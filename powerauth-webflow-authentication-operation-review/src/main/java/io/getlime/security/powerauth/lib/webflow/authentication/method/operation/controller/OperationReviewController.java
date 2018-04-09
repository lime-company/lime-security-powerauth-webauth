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

package io.getlime.security.powerauth.lib.webflow.authentication.method.operation.controller;

import io.getlime.core.rest.model.base.response.ObjectResponse;
import io.getlime.security.powerauth.lib.dataadapter.client.DataAdapterClient;
import io.getlime.security.powerauth.lib.dataadapter.client.DataAdapterClientErrorException;
import io.getlime.security.powerauth.lib.dataadapter.model.entity.BankAccountChoice;
import io.getlime.security.powerauth.lib.dataadapter.model.entity.FormData;
import io.getlime.security.powerauth.lib.dataadapter.model.entity.OperationContext;
import io.getlime.security.powerauth.lib.dataadapter.model.response.DecorateOperationFormDataResponse;
import io.getlime.security.powerauth.lib.nextstep.client.NextStepClient;
import io.getlime.security.powerauth.lib.nextstep.model.entity.AuthStep;
import io.getlime.security.powerauth.lib.nextstep.model.entity.OperationFormData;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthMethod;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.AuthStepResult;
import io.getlime.security.powerauth.lib.nextstep.model.enumeration.OperationCancelReason;
import io.getlime.security.powerauth.lib.nextstep.model.exception.NextStepServiceException;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOperationDetailResponse;
import io.getlime.security.powerauth.lib.webflow.authentication.controller.AuthMethodController;
import io.getlime.security.powerauth.lib.webflow.authentication.exception.AuthStepException;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.request.OperationDetailRequest;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.request.OperationReviewRequest;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.request.UpdateOperationChosenAuthMethodRequest;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.request.UpdateOperationFormDataRequest;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.response.OperationReviewDetailResponse;
import io.getlime.security.powerauth.lib.webflow.authentication.method.operation.model.response.OperationReviewResponse;
import io.getlime.security.powerauth.lib.webflow.authentication.model.converter.FormDataConverter;
import io.getlime.security.powerauth.lib.webflow.authentication.service.MessageTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Operation review controller which shows operation details to the user and handles operation form data updates.
 *
 * @author Petr Dvorak, petr@lime-company.eu
 */
@Controller
@RequestMapping(value = "/api/auth/operation")
public class OperationReviewController extends AuthMethodController<OperationReviewRequest, OperationReviewResponse, AuthStepException> {

    private final String FIELD_BANK_ACCOUNT_CHOICE = "operation.bankAccountChoice";
    private final String FIELD_BANK_ACCOUNT_CHOICE_DISABLED = "operation.bankAccountChoice.disabled";

    private final DataAdapterClient dataAdapterClient;
    private final NextStepClient nextStepClient;
    private final MessageTranslationService messageTranslationService;

    /**
     * Controller constructor.
     *
     * @param dataAdapterClient Data adapter client.
     * @param nextStepClient Next step client.
     * @param messageTranslationService Message translation service.
     */
    @Autowired
    public OperationReviewController(DataAdapterClient dataAdapterClient, NextStepClient nextStepClient, MessageTranslationService messageTranslationService) {
        this.dataAdapterClient = dataAdapterClient;
        this.nextStepClient = nextStepClient;
        this.messageTranslationService = messageTranslationService;
    }

    /**
     * Authentication step - step is automatically authenticated if operation is valid.
     * @param request Authentication request.
     * @return User ID.
     * @throws AuthStepException Thrown when authentication fails.
     */
    @Override
    protected String authenticate(OperationReviewRequest request) throws AuthStepException {
        final GetOperationDetailResponse operation = getOperation();
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step authentication started, operation ID: {0}, authentication method: {1}", new String[] {operation.getOperationId(), getAuthMethodName().toString()});
        //TODO: Check pre-authenticated user here
        Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step authentication succeeded, operation ID: {0}, authentication method: {1}", new String[] {operation.getOperationId(), getAuthMethodName().toString()});
        return operation.getUserId();
    }

    /**
     * Get current authentication method.
     * @return Current authentication method.
     */
    @Override
    protected AuthMethod getAuthMethodName() {
        return AuthMethod.SHOW_OPERATION_DETAIL;
    }

    /**
     * Get operation detail.
     * @param request Operation detail request.
     * @return Operation detail response.
     * @throws AuthStepException Thrown when operation is invalid or not available.
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public @ResponseBody OperationReviewDetailResponse getOperationDetails(@RequestBody OperationDetailRequest request) throws AuthStepException {
        final GetOperationDetailResponse operation = getOperation();
        OperationReviewDetailResponse response = new OperationReviewDetailResponse();
        response.setData(operation.getOperationData());
        response.setFormData(decorateFormData(operation));
        response.setChosenAuthMethod(operation.getChosenAuthMethod());
        return response;
    }

    /**
     * Perform step authentication and return response.
     * @param request Operation review request.
     * @return Operation review response.
     */
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public @ResponseBody OperationReviewResponse getOperationDetails(@RequestBody OperationReviewRequest request) {
        try {
            return buildAuthorizationResponse(request, new AuthResponseProvider() {

                @Override
                public OperationReviewResponse doneAuthentication(String userId) {
                    authenticateCurrentBrowserSession();
                    final OperationReviewResponse response = new OperationReviewResponse();
                    response.setResult(AuthStepResult.CONFIRMED);
                    response.setMessage("authentication.success");
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: CONFIRMED, authentication method: {0}", getAuthMethodName().toString());
                    return response;
                }

                @Override
                public OperationReviewResponse failedAuthentication(String userId, String failedReason) {
                    clearCurrentBrowserSession();
                    final OperationReviewResponse response = new OperationReviewResponse();
                    response.setResult(AuthStepResult.AUTH_FAILED);
                    response.setMessage(failedReason);
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: AUTH_FAILED, authentication method: {0}", getAuthMethodName().toString());
                    return response;
                }

                @Override
                public OperationReviewResponse continueAuthentication(String operationId, String userId, List<AuthStep> steps) {
                    final OperationReviewResponse response = new OperationReviewResponse();
                    response.setResult(AuthStepResult.CONFIRMED);
                    response.setMessage("authentication.success");
                    response.getNext().addAll(steps);
                    Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: CONFIRMED, operation ID: {0}, authentication method: {1}", new String[]{operationId, getAuthMethodName().toString()});
                    return response;
                }
            });
        } catch (AuthStepException e) {
            Logger.getLogger(this.getClass().getName()).log(Level.WARNING, "Error occurred while reviewing operation: {0}", e.getMessage());
            final OperationReviewResponse response = new OperationReviewResponse();
            response.setResult(AuthStepResult.AUTH_FAILED);
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: AUTH_FAILED, authentication method: {0}", getAuthMethodName().toString());
            if (e.getMessageId() != null) {
                // prefer localized message over regular message string
                response.setMessage(e.getMessageId());
            } else {
                response.setMessage(e.getMessage());
            }
            return response;
        }
    }

    /**
     * Cancel operation.
     * @return Object response.
     * @throws AuthStepException Thrown when operation could not be canceled.
     */
    @RequestMapping(value = "/cancel", method = RequestMethod.POST)
    public @ResponseBody OperationReviewResponse cancelAuthentication() throws AuthStepException {
        try {
            GetOperationDetailResponse operation = getOperation();
            cancelAuthorization(operation.getOperationId(), null, OperationCancelReason.UNKNOWN, null);
            final OperationReviewResponse response = new OperationReviewResponse();
            response.setResult(AuthStepResult.CANCELED);
            response.setMessage("operation.canceled");
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: CANCELED, operation ID: {0}, authentication method: {1}", new String[]{operation.getOperationId(), getAuthMethodName().toString()});
            return response;
        } catch (NextStepServiceException e) {
            final OperationReviewResponse response = new OperationReviewResponse();
            response.setResult(AuthStepResult.AUTH_FAILED);
            response.setMessage(e.getMessage());
            Logger.getLogger(this.getClass().getName()).log(Level.INFO, "Step result: AUTH_FAILED, authentication method: {0}", getAuthMethodName().toString());
            return response;
        }
    }

    /**
     * Update operation form data.
     * @param request Update operation form data request.
     * @return Object response.
     * @throws NextStepServiceException Thrown when communication with Next Step server fails.
     * @throws DataAdapterClientErrorException Thrown when data could not be retrieved from Data Adapter.
     * @throws AuthStepException Thrown when operation is invalid or not available.
     */
    @RequestMapping(value = "/formData", method = RequestMethod.PUT)
    public @ResponseBody ObjectResponse updateFormData(@RequestBody UpdateOperationFormDataRequest request) throws NextStepServiceException, DataAdapterClientErrorException, AuthStepException {
        final GetOperationDetailResponse operation = getOperation();
        // update formData in Next Step server
        nextStepClient.updateOperationFormData(operation.getOperationId(), request.getFormData());
        // Send notification to Data Adapter if the bank account has changed.
        // In case there is no bank account choice, the notification is not performed.
        Map<String, String> userInput = request.getFormData().getUserInput();
        if (userInput.containsKey(FIELD_BANK_ACCOUNT_CHOICE_DISABLED) && userInput.containsKey(FIELD_BANK_ACCOUNT_CHOICE)) {
            BankAccountChoice bankAccountChoice = new BankAccountChoice();
            bankAccountChoice.setBankAccountId(request.getFormData().getUserInput().get(FIELD_BANK_ACCOUNT_CHOICE));
            FormData formData = new FormDataConverter().fromOperationFormData(operation.getFormData());
            OperationContext operationContext = new OperationContext(operation.getOperationId(), operation.getOperationName(), operation.getOperationData(), formData);
            dataAdapterClient.formDataChangedNotification(bankAccountChoice, operation.getUserId(), operationContext);
        }
        return new ObjectResponse();
    }

    /**
     * Update chosen authentication method.
     * @param request Update chosen authentication method request.
     * @return Object response.
     * @throws NextStepServiceException Thrown when communication with Next Step server fails.
     * @throws AuthStepException Thrown when operation is invalid or not available.
     */
    @RequestMapping(value = "/chosenAuthMethod", method = RequestMethod.PUT)
    public @ResponseBody ObjectResponse updateChosenAuthenticationMethod(@RequestBody UpdateOperationChosenAuthMethodRequest request) throws NextStepServiceException, AuthStepException {
        final GetOperationDetailResponse operation = getOperation();
        // update chosenAuthMethod in Next Step server
        nextStepClient.updateChosenAuthMethod(operation.getOperationId(), request.getChosenAuthMethod());
        return new ObjectResponse();
    }

    /**
     * Decorate form data in Data Adapter.
     * @param operation Operation.
     * @return Decorated operation form data.
     */
    private OperationFormData decorateFormData(GetOperationDetailResponse operation) {
        OperationFormData formDataNS = operation.getFormData();
        if (formDataNS==null || operation.getUserId()==null) {
            return formDataNS;
        }
        if (!formDataNS.isDynamicDataLoaded()) {
            // Dynamic data has not been loaded yet. At this point the user is authenticated, so we can
            // load dynamic data based on user id. For now dynamic data contains the bank account list,
            // however it can be easily extended in the future.
            try {
                FormDataConverter converter = new FormDataConverter();
                FormData formDataDA = converter.fromOperationFormData(operation.getFormData());
                OperationContext operationContext = new OperationContext(operation.getOperationId(), operation.getOperationName(), operation.getOperationData(), formDataDA);
                ObjectResponse<DecorateOperationFormDataResponse> response = dataAdapterClient.decorateOperationFormData(operation.getUserId(), operationContext);
                DecorateOperationFormDataResponse responseObject = response.getResponseObject();
                formDataNS = converter.fromFormData(responseObject.getFormData());
                formDataNS.setDynamicDataLoaded(true);
                operation.setFormData(formDataNS);
            } catch (DataAdapterClientErrorException e) {
                // Failed to load dynamic data, log the error. The UI will handle missing dynamic data error separately.
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "Failed to load dynamic operation data", e);
            }
        }
        // translate new formData messages
        messageTranslationService.translateFormData(formDataNS);
        return formDataNS;
    }

}
