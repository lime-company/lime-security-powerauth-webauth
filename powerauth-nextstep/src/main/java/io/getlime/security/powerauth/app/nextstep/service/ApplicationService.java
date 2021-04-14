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
package io.getlime.security.powerauth.app.nextstep.service;

import io.getlime.security.powerauth.app.nextstep.converter.ApplicationConverter;
import io.getlime.security.powerauth.app.nextstep.repository.ApplicationRepository;
import io.getlime.security.powerauth.app.nextstep.repository.model.entity.ApplicationEntity;
import io.getlime.security.powerauth.lib.nextstep.model.entity.ApplicationDetail;
import io.getlime.security.powerauth.lib.nextstep.model.entity.enumeration.ApplicationStatus;
import io.getlime.security.powerauth.lib.nextstep.model.exception.ApplicationAlreadyExistsException;
import io.getlime.security.powerauth.lib.nextstep.model.exception.ApplicationNotFoundException;
import io.getlime.security.powerauth.lib.nextstep.model.request.CreateApplicationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.DeleteApplicationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.GetApplicationListRequest;
import io.getlime.security.powerauth.lib.nextstep.model.request.UpdateApplicationRequest;
import io.getlime.security.powerauth.lib.nextstep.model.response.CreateApplicationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.DeleteApplicationResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.GetApplicationListResponse;
import io.getlime.security.powerauth.lib.nextstep.model.response.UpdateApplicationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Optional;

/**
 * This service handles persistence of Next Step applications.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
@Service
public class ApplicationService {

    private final Logger logger = LoggerFactory.getLogger(ApplicationService.class);

    private final ApplicationRepository applicationRepository;

    private final ApplicationConverter applicationConverter = new ApplicationConverter();

    /**
     * Application service constructor.
     * @param applicationRepository Application repository.
     */
    @Autowired
    public ApplicationService(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    /**
     * Create an application.
     * @param request Create application request.
     * @return Create application response.
     * @throws ApplicationAlreadyExistsException Thrown when application already exists.
     */
    @Transactional
    public CreateApplicationResponse createApplication(CreateApplicationRequest request) throws ApplicationAlreadyExistsException {
        final Optional<ApplicationEntity> applicationOptional = applicationRepository.findByName(request.getApplicationName());
        if (applicationOptional.isPresent()) {
            throw new ApplicationAlreadyExistsException("Application already exists: " + request.getApplicationName());
        }
        final ApplicationEntity application = new ApplicationEntity();
        application.setName(request.getApplicationName());
        application.setDescription(request.getDescription());
        application.setStatus(ApplicationStatus.ACTIVE);
        application.setTimestampCreated(new Date());
        applicationRepository.save(application);
        final CreateApplicationResponse response = new CreateApplicationResponse();
        response.setApplicationName(application.getName());
        response.setDescription(application.getDescription());
        response.setApplicationStatus(application.getStatus());
        return response;
    }

    /**
     * Update an application.
     * @param request Update application request.
     * @return Update application response.
     * @throws ApplicationNotFoundException Thrown when application is not found.
     */
    @Transactional
    public UpdateApplicationResponse updateApplication(UpdateApplicationRequest request) throws ApplicationNotFoundException {
        final Optional<ApplicationEntity> applicationOptional = applicationRepository.findByName(request.getApplicationName());
        if (!applicationOptional.isPresent()) {
            throw new ApplicationNotFoundException("Application not found: " + request.getApplicationName());
        }
        final ApplicationEntity application = applicationOptional.get();
        if (application.getStatus() != ApplicationStatus.ACTIVE && request.getApplicationStatus() != ApplicationStatus.ACTIVE) {
            throw new ApplicationNotFoundException("Application is not ACTIVE: " + request.getApplicationName());
        }
        application.setDescription(request.getDescription());
        if (request.getApplicationStatus() != null) {
            application.setStatus(request.getApplicationStatus());
        }
        application.setTimestampLastUpdated(new Date());
        applicationRepository.save(application);
        final UpdateApplicationResponse response = new UpdateApplicationResponse();
        response.setApplicationName(application.getName());
        response.setDescription(application.getDescription());
        response.setApplicationStatus(application.getStatus());
        return response;
    }

    /**
     * Get application list.
     * @param request Get application list request.
     * @return Get application list response.
     */
    @Transactional
    public GetApplicationListResponse getApplicationList(GetApplicationListRequest request) {
        final Iterable<ApplicationEntity> applications;
        if (request.isIncludeRemoved()) {
            applications = applicationRepository.findAll();
        } else {
            applications = applicationRepository.findApplicationsByStatus(ApplicationStatus.ACTIVE);
        }
        final GetApplicationListResponse response = new GetApplicationListResponse();
        for (ApplicationEntity application: applications) {
            final ApplicationDetail applicationDetail = applicationConverter.fromEntity(application);
            response.getApplications().add(applicationDetail);
        }
        return response;
    }

    /**
     * Delete an application.
     * @param request Delete application request.
     * @return Delete application response.
     * @throws ApplicationNotFoundException Thrown when application is not found.
     */
    @Transactional
    public DeleteApplicationResponse deleteApplication(DeleteApplicationRequest request) throws ApplicationNotFoundException {
        final Optional<ApplicationEntity> applicationOptional = applicationRepository.findByName(request.getApplicationName());
        if (!applicationOptional.isPresent()) {
            throw new ApplicationNotFoundException("Application not found: " + request.getApplicationName());
        }
        final ApplicationEntity application = applicationOptional.get();
        application.setStatus(ApplicationStatus.REMOVED);
        application.setTimestampLastUpdated(new Date());
        applicationRepository.save(application);
        final DeleteApplicationResponse response = new DeleteApplicationResponse();
        response.setApplicationName(application.getName());
        response.setApplicationStatus(application.getStatus());
        return response;
    }

}
