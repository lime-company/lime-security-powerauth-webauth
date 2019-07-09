/*
 * Copyright 2017 Wultra s.r.o.
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
package io.getlime.security.powerauth.lib.nextstep.model.request;

import io.getlime.security.powerauth.lib.nextstep.model.entity.ApplicationContext;

/**
 * Request object used for updating application context of an operation.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class UpdateApplicationContextRequest {

    private String operationId;
    private ApplicationContext applicationContext;

    /**
     * Default constructor.
     */
    public UpdateApplicationContextRequest() {
    }

    /**
     * Constructor with operation ID and application context.
     *
     * @param operationId        Operation ID.
     * @param applicationContext Application context.
     */
    public UpdateApplicationContextRequest(String operationId, ApplicationContext applicationContext) {
        this.operationId = operationId;
        this.applicationContext = applicationContext;
    }

    /**
     * Get operation ID.
     *
     * @return Operation ID.
     */
    public String getOperationId() {
        return operationId;
    }

    /**
     * Set operation ID.
     *
     * @param operationId Operation ID.
     */
    public void setOperationId(String operationId) {
        this.operationId = operationId;
    }

    /**
     * Get application context.
     *
     * @return Application context.
     */
    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    /**
     * Set application context.
     * @param applicationContext Application context.
     */
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }
}