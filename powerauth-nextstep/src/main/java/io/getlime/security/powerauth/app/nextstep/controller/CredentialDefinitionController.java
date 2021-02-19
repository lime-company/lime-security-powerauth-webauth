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
import io.getlime.security.powerauth.app.nextstep.service.CredentialDefinitionService;
import io.getlime.security.powerauth.lib.nextstep.model.exception.*;
import io.getlime.security.powerauth.lib.nextstep.model.request.CreateCredentialDefinitionRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.DeleteCredentialDefinitionRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.GetCredentialDefinitionListRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.UpdateCredentialDefinitionRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CreateCredentialDefinitionResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.DeleteCredentialDefinitionResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetCredentialDefinitionListResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.UpdateCredentialDefinitionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for credential definitions.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@RestController
@RequestMapping("credential/definition")
public class CredentialDefinitionController {

    private static final Logger logger = LoggerFactory.getLogger(CredentialDefinitionController.class);

    private final CredentialDefinitionService credentialDefinitionService;

    @Autowired
    public CredentialDefinitionController(CredentialDefinitionService credentialDefinitionService) {
        this.credentialDefinitionService = credentialDefinitionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<CreateCredentialDefinitionResponse> createCredentialDefinition(@RequestBody ObjectRequest<CreateCredentialDefinitionRequest> request) throws CredentialDefinitionAlreadyExistsException, ApplicationNotFoundException, HashConfigNotFoundException, CredentialPolicyNotFoundException {
        // TODO - request validation
        CreateCredentialDefinitionResponse response = credentialDefinitionService.createCredentialDefinition(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ObjectResponse<UpdateCredentialDefinitionResponse> updateCredentialDefinition(@RequestBody ObjectRequest<UpdateCredentialDefinitionRequest> request) throws CredentialDefinitionNotFoundException, ApplicationNotFoundException, HashConfigNotFoundException, CredentialPolicyNotFoundException {
        // TODO - request validation
        UpdateCredentialDefinitionResponse response = credentialDefinitionService.updateCredentialDefinition(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ObjectResponse<UpdateCredentialDefinitionResponse> updateCredentialDefinitionPost(@RequestBody ObjectRequest<UpdateCredentialDefinitionRequest> request) throws CredentialDefinitionNotFoundException, ApplicationNotFoundException, HashConfigNotFoundException, CredentialPolicyNotFoundException {
        // TODO - request validation
        UpdateCredentialDefinitionResponse response = credentialDefinitionService.updateCredentialDefinition(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ObjectResponse<GetCredentialDefinitionListResponse> listCredentialPolicies(@RequestBody ObjectRequest<GetCredentialDefinitionListRequest> request) {
        GetCredentialDefinitionListResponse response = credentialDefinitionService.getCredentialDefinitionList(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteCredentialDefinitionResponse> deleteCredentialDefinition(@RequestBody ObjectRequest<DeleteCredentialDefinitionRequest> request) throws CredentialDefinitionNotFoundException {
        // TODO - request validation
        DeleteCredentialDefinitionResponse response = credentialDefinitionService.deleteCredentialDefinition(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

}
