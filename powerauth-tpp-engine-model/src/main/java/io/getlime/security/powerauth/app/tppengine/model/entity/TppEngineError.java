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

package io.getlime.security.powerauth.app.tppengine.model.entity;

import io.getlime.core.rest.model.base.entity.Error;

/**
 *
 * Error model, used to represent error responses.
 *
 * @author Petr Dvorak, petr@wultra.com
 */
public class TppEngineError extends Error {

    private String[] causes;

    /**
     * Response codes for different authentication failures.
     */
    public class Code extends Error.Code {
        public static final String REMOTE_ERROR = "REMOTE_ERROR";
        public static final String TPP_ENGINE_CLIENT_ERROR = "TPP_ENGINE_CLIENT_ERROR";
        public static final String COMMUNICATION_ERROR = "COMMUNICATION_ERROR";
    }

    /**
     * Default constructor.
     */
    public TppEngineError() {
        super();
    }

    /**
     * Constructor accepting code and message.
     *
     * @param code    Error code.
     * @param message Error message.
     */
    public TppEngineError(String code, String message) {
        super(code, message);
    }

    /**
     * Set the causes of this error.
     * @param causes Array of causes for the error.
     */
    public void setCauses(String[] causes) {
        this.causes = causes;
    }

    /**
     * Get the array of causes for this error.
     * @return Array of causes for the error.
     */
    public String[] getCauses() {
        return causes;
    }
}
