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
import io.getlime.security.powerauth.app.nextstep.service.HashConfigService;
import io.getlime.security.powerauth.lib.nextstep.model.exception.HashConfigAlreadyExistsException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.HashConfigNotFoundException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.InvalidConfigurationException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.InvalidRequestException;
import io.getlime.security.powerauth.lib.nextstep.model.request.CreateHashConfigRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.DeleteHashConfigRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.GetHashConfigListRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.UpdateHashConfigRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CreateHashConfigResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.DeleteHashConfigResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetHashConfigListResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.UpdateHashConfigResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * REST controller for hashing configurations.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@RestController
@RequestMapping("hashconfig")
public class HashConfigController {

    private static final Logger logger = LoggerFactory.getLogger(HashConfigController.class);

    private final HashConfigService hashConfigService;

    /**
     * REST controller constructor.
     * @param hashConfigService Hashing configuration service.
     */
    @Autowired
    public HashConfigController(HashConfigService hashConfigService) {
        this.hashConfigService = hashConfigService;
    }

    /**
     * Create a hashing configuration.
     * @param request Create hashing configuration request.
     * @return Create hashing configuration response.
     * @throws InvalidRequestException Thrown when request is invalid.
     * @throws HashConfigAlreadyExistsException Thrown when hashing configuration already exists.
     */
    @RequestMapping(method = RequestMethod.POST)
    public ObjectResponse<CreateHashConfigResponse> createHashConfig(@Valid @RequestBody ObjectRequest<CreateHashConfigRequest> request) throws InvalidRequestException, HashConfigAlreadyExistsException {
        logger.info("Received createHashConfig request, hash config name: {}", request.getRequestObject().getHashConfigName());
        CreateHashConfigResponse response = hashConfigService.createHashConfig(request.getRequestObject());
        logger.info("The createHashConfig request succeeded, hash config name: {}", request.getRequestObject().getHashConfigName());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a hashing configuration via PUT method.
     * @param request Update hashing configuration request.
     * @return Update hashing configuration response.
     * @throws HashConfigNotFoundException Thrown when hashing configuration is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(method = RequestMethod.PUT)
    public ObjectResponse<UpdateHashConfigResponse> updateHashConfig(@Valid @RequestBody ObjectRequest<UpdateHashConfigRequest> request) throws HashConfigNotFoundException, InvalidRequestException {
        logger.info("Received updateHashConfig request, hash config name: {}", request.getRequestObject().getHashConfigName());
        UpdateHashConfigResponse response = hashConfigService.updateHashConfig(request.getRequestObject());
        logger.info("The updateHashConfig request succeeded, hash config name: {}", request.getRequestObject().getHashConfigName());
        return new ObjectResponse<>(response);
    }

    /**
     * Update a hashing configuration via POST method.
     * @param request Update hashing configuration request.
     * @return Update hashing configuration response.
     * @throws HashConfigNotFoundException Thrown when hashing configuration is not found.
     * @throws InvalidRequestException Thrown when request is invalid.
     */
    @RequestMapping(value = "update", method = RequestMethod.POST)
    public ObjectResponse<UpdateHashConfigResponse> updateHashConfigPost(@Valid @RequestBody ObjectRequest<UpdateHashConfigRequest> request) throws HashConfigNotFoundException, InvalidRequestException {
        logger.info("Received updateHashConfigPost request, hash config name: {}", request.getRequestObject().getHashConfigName());
        UpdateHashConfigResponse response = hashConfigService.updateHashConfig(request.getRequestObject());
        logger.info("The updateHashConfigPost request succeeded, hash config name: {}", request.getRequestObject().getHashConfigName());
        return new ObjectResponse<>(response);
    }

    /**
     * Get hashing configuration list.
     * @param request Get hashing configuration list request.
     * @return Get hashing configuration list response.
     * @throws InvalidConfigurationException Thrown when Next Step configuration is invalid.
     */
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ObjectResponse<GetHashConfigListResponse> getHashConfigList(@Valid @RequestBody ObjectRequest<GetHashConfigListRequest> request) throws InvalidConfigurationException {
        logger.info("Received getHashConfigList request");
        GetHashConfigListResponse response = hashConfigService.getHashConfigList(request.getRequestObject());
        logger.info("The getHashConfigList request succeeded");
        return new ObjectResponse<>(response);
    }

    /**
     * Delete a hashing configuration.
     * @param request Delete hashing configuration request.
     * @return Delete hashing configuration response.
     * @throws HashConfigNotFoundException Thrown when hashing configuration is not found.
     */
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ObjectResponse<DeleteHashConfigResponse> deleteHashConfig(@Valid @RequestBody ObjectRequest<DeleteHashConfigRequest> request) throws HashConfigNotFoundException {
        logger.info("Received deleteHashConfig request, hash config name: {}", request.getRequestObject().getHashConfigName());
        DeleteHashConfigResponse response = hashConfigService.deleteHashConfig(request.getRequestObject());
        logger.info("The deleteHashConfig request succeeded, hash config name: {}", request.getRequestObject().getHashConfigName());
        return new ObjectResponse<>(response);
    }

}
