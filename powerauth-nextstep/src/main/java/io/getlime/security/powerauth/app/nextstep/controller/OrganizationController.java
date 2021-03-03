/*
 * Copyright 2019 Wultra s.r.o.
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
import io.getlime.security.powerauth.app.nextstep.exception.ObjectRequestValidator;
import io.getlime.security.powerauth.app.nextstep.service.OrganizationService;
import io.getlime.security.powerauth.lib.nextstep.model.exception.DeleteNotAllowedException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.OrganizationAlreadyExistsException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.OrganizationNotFoundException;
import io.getlime.security.powerauth.lib.nextstep.model.request.CreateOrganizationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.DeleteOrganizationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.GetOrganizationDetailRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.GetOrganizationListRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CreateOrganizationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.DeleteOrganizationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOrganizationDetailResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetOrganizationListResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller class related to Next Step organizations.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@RestController
@RequestMapping(value = "organization")
public class OrganizationController {

    private static final Logger logger = LoggerFactory.getLogger(OrganizationController.class);

    private final OrganizationService organizationService;
    private final ObjectRequestValidator requestValidator;

    /**
     * REST controller constructor.
     * @param organizationService Organization service.
     * @param requestValidator Object request validator.
     */
    @Autowired
    public OrganizationController(OrganizationService organizationService, ObjectRequestValidator requestValidator) {
        this.organizationService = organizationService;
        this.requestValidator = requestValidator;
    }

    /**
     * Initialize the request validator.
     * @param binder Data binder.
     */
    @InitBinder
    private void initBinder(WebDataBinder binder) {
        binder.setValidator(requestValidator);
    }

    /**
     * Create an organization.
     * @param request Create organization request.
     * @return Create organization response.
     * @throws OrganizationAlreadyExistsException Thrown when organization already exists.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<CreateOrganizationResponse> createOrganization(@Valid @RequestBody ObjectRequest<CreateOrganizationRequest> request) throws OrganizationAlreadyExistsException {
        CreateOrganizationResponse response = organizationService.createOrganization(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

    /**
     * Get organization detail.
     *
     * @param request Get organization detail request.
     * @return Get organization detail response.
     * @throws OrganizationNotFoundException Thrown in case organization does not exist.
     */
    @RequestMapping(value = "detail", method = RequestMethod.POST)
    public ObjectResponse<GetOrganizationDetailResponse> getOrganizationDetail(@Valid @RequestBody ObjectRequest<GetOrganizationDetailRequest> request) throws OrganizationNotFoundException {
        logger.info("Received getOrganizationDetail request");
        if (request == null || request.getRequestObject() == null) {
            throw new OrganizationNotFoundException("Invalid request");
        }
        GetOrganizationDetailResponse response = organizationService.getOrganizationDetail(request.getRequestObject());
        logger.info("The getOrganizationDetail request succeeded");
        return new ObjectResponse<>(response);
    }

    /**
     * List organizations defined in Next Step service.
     *
     * @param request Get organizations request.
     * @return Get organizations response.
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ObjectResponse<GetOrganizationListResponse> getOrganizationList(@Valid @RequestBody ObjectRequest<GetOrganizationListRequest> request) {
        logger.info("Received getOrganizationList request");
        GetOrganizationListResponse response = organizationService.getOrganizationList(request.getRequestObject());
        logger.info("The getOrganizationList request succeeded, number of organizations: {}", response.getOrganizations().size());
        return new ObjectResponse<>(response);
    }

    /**
     * Delete an organization.
     * @param request Delete organization request.
     * @return Delete organization response.
     * @throws OrganizationNotFoundException Thrown when organization is not found.
     * @throws DeleteNotAllowedException Thrown when delete action is not allowed.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteOrganizationResponse> deleteOrganization(@Valid @RequestBody ObjectRequest<DeleteOrganizationRequest> request) throws OrganizationNotFoundException, DeleteNotAllowedException {
        DeleteOrganizationResponse response = organizationService.deleteOrganization(request.getRequestObject());
        return new ObjectResponse<>(response);
    }

}
