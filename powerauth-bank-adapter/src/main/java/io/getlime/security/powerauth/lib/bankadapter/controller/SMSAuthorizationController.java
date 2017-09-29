/*
 * Copyright 2017 Lime - HighTech Solutions s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.getlime.security.powerauth.lib.bankadapter.controller;

import io.getlime.core.rest.model.base.request.ObjectRequest;
import io.getlime.core.rest.model.base.response.ObjectResponse;
import io.getlime.security.powerauth.crypto.lib.generator.KeyGenerator;
import io.getlime.security.powerauth.crypto.lib.util.HMACHashUtilities;
import io.getlime.security.powerauth.lib.bankadapter.configuration.BankAdapterConfiguration;
import io.getlime.security.powerauth.lib.bankadapter.exception.SMSAuthorizationFailedException;
import io.getlime.security.powerauth.lib.bankadapter.exception.SMSAuthorizationMessageInvalidException;
import io.getlime.security.powerauth.lib.bankadapter.model.request.CreateSMSAuthorizationRequest;
import io.getlime.security.powerauth.lib.bankadapter.model.request.VerifySMSAuthorizationRequest;
import io.getlime.security.powerauth.lib.bankadapter.model.response.CreateSMSAuthorizationResponse;
import io.getlime.security.powerauth.lib.bankadapter.repository.SMSAuthorizationRepository;
import io.getlime.security.powerauth.lib.bankadapter.repository.model.entity.SMSAuthorizationEntity;
import io.getlime.security.powerauth.lib.bankadapter.validation.CreateSMSAuthorizationRequestValidator;
import org.joda.time.DateTime;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

/**
 * Controller class which handles SMS OTP authorization.
 *
 * @author Roman Strobl, roman.strobl@lime-company.eu
 */
@Controller
@RequestMapping("/api/auth/sms")
public class SMSAuthorizationController {

    // the authorization code length - number of digits
    private static final int AUTHORIZATION_CODE_LENGTH = 8;

    private SMSAuthorizationRepository smsAuthorizationRepository;
    private BankAdapterConfiguration bankAdapterConfiguration;

    public SMSAuthorizationController(SMSAuthorizationRepository smsAuthorizationRepository, BankAdapterConfiguration bankAdapterConfiguration) {
        this.smsAuthorizationRepository = smsAuthorizationRepository;
        this.bankAdapterConfiguration = bankAdapterConfiguration;
    }

    /**
     * Create a new SMS OTP authorization message.
     *
     * @param request Request data.
     * @return Response with message ID.
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public @ResponseBody ObjectResponse<CreateSMSAuthorizationResponse> create(@RequestBody ObjectRequest<CreateSMSAuthorizationRequest> request) throws MethodArgumentNotValidException {
        CreateSMSAuthorizationRequest createSMSAuthorizationRequest = request.getRequestObject();

        // input validation is handled by CreateSMSAuthorizationRequestValidator
        // validation is invoked manually because of the generified Request object
        CreateSMSAuthorizationRequestValidator validator = new CreateSMSAuthorizationRequestValidator();
        BeanPropertyBindingResult result = new BeanPropertyBindingResult(createSMSAuthorizationRequest, "createSMSAuthorizationRequest");
        ValidationUtils.invokeValidator(validator, createSMSAuthorizationRequest, result);
        if (result.hasErrors()) {
            // getEnclosingMethod() on new object returns a reference to current method
            MethodParameter methodParam = new MethodParameter(new Object() {
            }.getClass().getEnclosingMethod(), 0);
            throw new MethodArgumentNotValidException(methodParam, result);
        }

        // messageId is generated as random UUID, it can be overriden to provide a real message identification
        String messageId = UUID.randomUUID().toString();

        // update names of operationData JSON fields if necessary
        BigDecimal amount = createSMSAuthorizationRequest.getOperationData().get("amount").decimalValue();
        String currency = createSMSAuthorizationRequest.getOperationData().get("currency").textValue();
        String account = createSMSAuthorizationRequest.getOperationData().get("account").textValue();

        // update localized SMS message text in resources
        String authorizationCode = generateAuthorizationCode(amount, currency, account);
        String[] messageArgs = {amount.toPlainString(), currency, account, authorizationCode};
        String messageText = messageSource().getMessage("sms-otp.text", messageArgs, new Locale(createSMSAuthorizationRequest.getLang()));

        SMSAuthorizationEntity smsEntity = new SMSAuthorizationEntity();
        smsEntity.setMessageId(messageId);
        smsEntity.setUserId(createSMSAuthorizationRequest.getUserId());
        smsEntity.setOperationName(createSMSAuthorizationRequest.getOperationName());
        smsEntity.setOperationData(createSMSAuthorizationRequest.getOperationData().toString());
        smsEntity.setAuthorizationCode(authorizationCode);
        smsEntity.setMessageText(messageText);
        smsEntity.setVerifyRequestCount(0);
        smsEntity.setTimestampCreated(new Date());
        smsEntity.setTimestampExpires(new DateTime().plusSeconds(bankAdapterConfiguration.getSmsOtpExpirationTime()).toDate());
        smsEntity.setTimestampVerified(null);
        smsEntity.setVerified(false);

        // store entity in database
        smsAuthorizationRepository.save(smsEntity);

        // Add here code to send the SMS OTP message to user identified by userId with messageText.

        CreateSMSAuthorizationResponse createSMSResponse = new CreateSMSAuthorizationResponse(messageId);
        return new ObjectResponse<>(createSMSResponse);
    }

    /**
     * Verify a SMS OTP authorization code.
     *
     * @param request Request data.
     * @return Authorization response.
     */
    @RequestMapping(value = "verify", method = RequestMethod.POST)
    public @ResponseBody ObjectResponse verify(@RequestBody ObjectRequest<VerifySMSAuthorizationRequest> request) throws SMSAuthorizationMessageInvalidException, SMSAuthorizationFailedException {
        VerifySMSAuthorizationRequest verifyRequest = request.getRequestObject();
        String messageId = verifyRequest.getMessageId();
        SMSAuthorizationEntity smsEntity = smsAuthorizationRepository.findOne(messageId);
        if (smsEntity == null) {
            throw new SMSAuthorizationMessageInvalidException("smsAuthorization.invalidMessage");
        }
        // increase number of verification tries and save entity
        smsEntity.setVerifyRequestCount(smsEntity.getVerifyRequestCount() + 1);
        smsAuthorizationRepository.save(smsEntity);

        if (smsEntity.getAuthorizationCode() == null || smsEntity.getAuthorizationCode().isEmpty()) {
            throw new SMSAuthorizationMessageInvalidException("smsAuthorization.invalidCode");
        }
        if (smsEntity.isExpired()) {
            throw new SMSAuthorizationFailedException("smsAuthorization.expired");
        }
        if (smsEntity.isVerified()) {
            throw new SMSAuthorizationFailedException("smsAuthorization.alreadyVerified");
        }
        if (smsEntity.getVerifyRequestCount() > bankAdapterConfiguration.getSmsOtpMaxVerifyTriesPerMessage()) {
            throw new SMSAuthorizationFailedException("smsAuthorization.maxAttemptsExceeded");
        }
        String authorizationCodeExpected = smsEntity.getAuthorizationCode();
        String authorizationCodeActual = verifyRequest.getAuthorizationCode();
        if (!authorizationCodeActual.equals(authorizationCodeExpected)) {
            throw new SMSAuthorizationFailedException("smsAuthorization.failed");
        }

        // SMS OTP authorization succeeded when this line is reached, update entity verification status
        smsEntity.setVerified(true);
        smsEntity.setTimestampVerified(new Date());
        smsAuthorizationRepository.save(smsEntity);

        // no actual data sent - ObjectResponse is empty
        return new ObjectResponse();
    }

    /**
     * Authorization code generation - to be updated based on application requirements.
     *
     * @return Generated authorization code.
     */
    private String generateAuthorizationCode(BigDecimal amount, String currency, String account) {
        try {
            // use random key for hash
            byte[] randomKey = new KeyGenerator().generateRandomBytes(16);
            // include amount, currency and account in the operation data
            String operationData = amount.toPlainString() + "&" + currency + "&" + account;
            HMACHashUtilities hmac = new HMACHashUtilities();
            // generate hash of operation data to achieve the dynamic linking property:
            // "any change to the amount or payee shall result in a change of the authentication code"
            byte[] otpHash = hmac.hash(randomKey, operationData.getBytes("UTF-8"));
            // use modulo on generated hash to get the right length of authorization code
            BigInteger otp = new BigInteger(otpHash).mod(BigInteger.TEN.pow(AUTHORIZATION_CODE_LENGTH));
            // prepare digit format - add leading zeros in case otp starts with zeros
            String digitFormat = "%" + String.format("%02d", AUTHORIZATION_CODE_LENGTH) + "d";
            // apply the digit format
            return String.format(digitFormat, otp);
        } catch (UnsupportedEncodingException ex) {
            // UTF-8 is always available, null is never thrown
            return null;
        }
    }

    /**
     * Get MessageSource with i18n data for authorizations SMS messages.
     *
     * @return MessageSource.
     */
    @Bean
    private MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/static/resources/messages");
        return messageSource;
    }
}