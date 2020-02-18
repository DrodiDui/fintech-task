package by.kapitonov.fintech.task.exception;

import javax.validation.ValidationException;

public class CustomValidationException extends ValidationException {

    public CustomValidationException(String message) {
        super(message);
    }

    public CustomValidationException() {
        super();
    }

    public CustomValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    public CustomValidationException(Throwable cause) {
        super(cause);
    }
}
