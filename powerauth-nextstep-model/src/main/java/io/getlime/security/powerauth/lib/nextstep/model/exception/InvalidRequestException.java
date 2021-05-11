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
package io.getlime.security.powerauth.lib.nextstep.model.exception;

/**
 * Exception for case when request is invalid.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class InvalidRequestException extends NextStepServiceException {

    /**
     * Request is invalid.
     */
    public static final String CODE = "INVALID_REQUEST";

    /**
     * Constructor with error message.
     * @param message Error message.
     */
    public InvalidRequestException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param cause Original exception.
     */
    public InvalidRequestException(Throwable cause) {
        super(cause);
    }

}
