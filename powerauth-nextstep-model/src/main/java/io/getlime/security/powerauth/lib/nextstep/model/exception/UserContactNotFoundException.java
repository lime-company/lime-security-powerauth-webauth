package io.getlime.security.powerauth.lib.nextstep.model.exception;

import io.getlime.core.rest.model.base.entity.Error;

/**
 * Exception for case when user contact is not found.
 *
 * @author Roman Strobl, roman.strobl@wultra.com
 */
public class UserContactNotFoundException extends NextStepServiceException {

    public static final String CODE = "USER_CONTACT_NOT_FOUND";

    private Error error;

    /**
     * Constructor with error message.
     * @param message Error message.
     */
    public UserContactNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructor with cause.
     * @param cause Original exception.
     */
    public UserContactNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Constructor with cause and error details.
     * @param cause Original exception.
     * @param error Object with error information.
     */
    public UserContactNotFoundException(Throwable cause, Error error) {
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