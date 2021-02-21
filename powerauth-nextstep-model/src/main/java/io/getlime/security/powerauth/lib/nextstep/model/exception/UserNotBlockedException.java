package io.getlime.security.powerauth.lib.nextstep.model.exception;

import io.getlime.core.rest.model.base.entity.Error;

/**
 * Exception for case when user identity is not blocked.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class UserNotBlockedException extends NextStepServiceException {

    public static final String CODE = "USER_IDENTITY_NOT_BLOCKED";

    private Error error;

    /**
     * Constructor with error message.
     * @param message Error message.
     */
    public UserNotBlockedException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param cause Original exception.
     */
    public UserNotBlockedException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with cause and error details.
     * @param cause Original exception.
     * @param error Object with error information.
     */
    public UserNotBlockedException(Throwable cause, Error error) {
        super(cause);
        this.error = error;
    }

    /**
     * Get error detail information.
     * @return Error detail information.
     */
    public Error getError() {
        return error;
    }
}