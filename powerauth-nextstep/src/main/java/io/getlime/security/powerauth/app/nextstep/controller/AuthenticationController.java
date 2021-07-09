/*
 * Copyright 2021 Wultra s.r.o.
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

package io.getlime.security.powerauth.app.nextstep.controller;

import io.getlime.core.rest.model.base.request.ObjectRequest;
import io.getlime.core.rest.model.base.response.ObjectResponse;
import io.getlime.security.powerauth.app.nextstep.service.AuthenticationService;
import io.getlime.security.powerauth.lib.nextstep.model.exception.*;
import io.getlime.security.powerauth.lib.nextstep.model.request.CombinedAuthenticationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.CredentialAuthenticationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.OtpAuthenticationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CombinedAuthenticationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.CredentialAuthenticationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.OtpAuthenticationResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for user authentication.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@RestController
@RequestMapping("auth")
@Validated
public class AuthenticationController {

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    private final AuthenticationService authenticationService;

    /**
     * REST controller constructor.
     * @param authenticationService Authentication service.
     */
    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    /**
     * Authenticate with a credential.
     * @param request Credential authentication request.
     * @return Credential authentication response.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws OperationNotFoundException Thrown when operation is not found.
     * @throws CredentialNotFoundException Thrown when credential is not found.
     * @throws CredentialDefinitionNotFoundException Thrown when credential definition is not found.
     * @throws InvalidConfigurationException Thrown when configuration is not found.
     * @throws OperationAlreadyFinishedException Thrown when operation is already finished.
     * @throws OperationAlreadyCanceledException Thrown when operation is already canceled.
     * @throws OperationAlreadyFailedException Thrown when operation is already failed.
     * @throws OperationNotValidException Thrown when operation is not valid.
     * @throws AuthMethodNotFoundException Thrown when authentication method is not found.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @Operation(summary = "Authenticate using a credential")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication result sent in response"),
            @ApiResponse(responseCode = "400", description = "Invalid request, error codes: REQUEST_VALIDATION_FAILED, INVALID_REQUEST, USER_IDENTITY_NOT_FOUND, OPERATION_NOT_FOUND, CREDENTIAL_NOT_FOUND, CREDENTIAL_DEFINITION_NOT_FOUND, INVALID_CONFIGURATION, OPERATION_ALREADY_FINISHED, OPERATION_ALREADY_CANCELED, OPERATION_ALREADY_FAILED, OPERATION_NOT_VALID, AUTH_METHOD_NOT_FOUND, ENCRYPTION_FAILED"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @RequestMapping(value = "credential", method = RequestMethod.POST)
    public ObjectResponse<CredentialAuthenticationResponse> authenticateWithCredential(@Valid @RequestBody ObjectRequest<CredentialAuthenticationRequest> request) throws InvalidRequestException, UserNotFoundException, OperationNotFoundException, CredentialNotFoundException, CredentialDefinitionNotFoundException, InvalidConfigurationException, OperationAlreadyFinishedException, OperationAlreadyCanceledException, OperationAlreadyFailedException, OperationNotValidException, AuthMethodNotFoundException, EncryptionException {
        logger.info("Received authenticateWithCredential request, user ID: {}, operation ID: {}", request.getRequestObject().getUserId(), request.getRequestObject().getOperationId());
        final CredentialAuthenticationResponse response = authenticationService.authenticateWithCredential(request.getRequestObject());
        logger.info("The authenticateWithCredential request succeeded, user ID: {}, operation ID: {}, result: {}", request.getRequestObject().getUserId(), request.getRequestObject().getOperationId(), response.getAuthenticationResult());
        return new ObjectResponse<>(response);
    }

    /**
     * Authenticate with an OTP.
     * @param request OTP authentication request.
     * @return OTP authentication response.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws AuthMethodNotFoundException Thrown when authentication method is not found.
     * @throws OperationAlreadyFailedException Thrown when operation is already failed.
     * @throws OperationAlreadyFinishedException Thrown when operation is already finished.
     * @throws OperationAlreadyCanceledException Thrown when operation is already canceled.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     * @throws CredentialNotFoundException Thrown when credential is not found.
     * @throws OperationNotFoundException Throw when operation is not found.
     * @throws OtpNotFoundException Thrown when OTP is not found.
     * @throws OperationNotValidException Thrown when operation is not valid.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @Operation(summary = "Authenticate using an OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication result sent in response"),
            @ApiResponse(responseCode = "400", description = "Invalid request, error codes: REQUEST_VALIDATION_FAILED, INVALID_REQUEST, AUTH_METHOD_NOT_FOUND, OPERATION_ALREADY_FAILED, OPERATION_ALREADY_FINISHED, OPERATION_ALREADY_CANCELED, INVALID_CONFIGURATION, CREDENTIAL_NOT_FOUND, OPERATION_NOT_FOUND, OTP_NOT_FOUND, OPERATION_NOT_VALID, ENCRYPTION_FAILED"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @RequestMapping(value = "otp", method = RequestMethod.POST)
    public ObjectResponse<OtpAuthenticationResponse> authenticateWithOtp(@Valid @RequestBody ObjectRequest<OtpAuthenticationRequest> request) throws InvalidRequestException, AuthMethodNotFoundException, OperationAlreadyFailedException, OperationAlreadyFinishedException, OperationAlreadyCanceledException, InvalidConfigurationException, CredentialNotFoundException, OperationNotFoundException, OtpNotFoundException, OperationNotValidException, EncryptionException {
        logger.info("Received authenticateWithOtp request, OTP ID: {}, operation ID: {}", request.getRequestObject().getOtpId(), request.getRequestObject().getOperationId());
        final OtpAuthenticationResponse response = authenticationService.authenticateWithOtp(request.getRequestObject());
        logger.info("The authenticateWithOtp succeeded, OTP ID: {}, operation ID: {}, result: {}", request.getRequestObject().getOtpId(), request.getRequestObject().getOperationId(), response.getAuthenticationResult());
        return new ObjectResponse<>(response);
    }

    /**
     * Authenticate with credential and OTP.
     * @param request Combined authentication request.
     * @return Combined authentication response.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws AuthMethodNotFoundException Thrown when authentication method is not found.
     * @throws InvalidConfigurationException Thrown when configuration is invalid.
     * @throws UserNotFoundException Thrown when user identity is not found.
     * @throws OperationAlreadyFinishedException Thrown when operation is already finished.
     * @throws OperationAlreadyCanceledException Thrown when operation is already canceled.
     * @throws OperationAlreadyFailedException Thrown when operation is already failed.
     * @throws CredentialNotFoundException Thrown when credential is not found.
     * @throws OperationNotFoundException Thrown when operation is not found.
     * @throws OtpNotFoundException Thrown when OTP is not found.
     * @throws OperationNotValidException Thrown when operation is not valid.
     * @throws EncryptionException Thrown when decryption fails.
     */
    @Operation(summary = "Authenticate using a credential and OTP")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Authentication result sent in response"),
            @ApiResponse(responseCode = "400", description = "Invalid request, error codes: REQUEST_VALIDATION_FAILED, INVALID_REQUEST, AUTH_METHOD_NOT_FOUND, INVALID_CONFIGURATION, USER_IDENTITY_NOT_FOUND, OPERATION_ALREADY_FINISHED, OPERATION_ALREADY_CANCELED, OPERATION_ALREADY_FAILED, CREDENTIAL_NOT_FOUND, OPERATION_NOT_FOUND, OTP_NOT_FOUND, OPERATION_NOT_VALID, ENCRYPTION_FAILED"),
            @ApiResponse(responseCode = "500", description = "Unexpected error")
    })
    @RequestMapping(value = "combined", method = RequestMethod.POST)
    public ObjectResponse<CombinedAuthenticationResponse> authenticateCombined(@Valid @RequestBody ObjectRequest<CombinedAuthenticationRequest> request) throws InvalidRequestException, AuthMethodNotFoundException, InvalidConfigurationException, UserNotFoundException, OperationAlreadyFinishedException, OperationAlreadyCanceledException, OperationAlreadyFailedException, CredentialNotFoundException, OperationNotFoundException, OtpNotFoundException, OperationNotValidException, EncryptionException {
        logger.info("Received authenticateCombined request, user ID: {}, OTP ID: {}, operation ID: {}", request.getRequestObject().getUserId(), request.getRequestObject().getOperationId(), request.getRequestObject().getOtpId());
        final CombinedAuthenticationResponse response = authenticationService.authenticateCombined(request.getRequestObject());
        logger.info("The authenticateCombined request succeeded, user ID: {}, OTP ID: {}, operation ID: {}, result: {}", request.getRequestObject().getUserId(), request.getRequestObject().getOperationId(), request.getRequestObject().getOtpId(), response.getAuthenticationResult());
        return new ObjectResponse<>(response);
    }

}
