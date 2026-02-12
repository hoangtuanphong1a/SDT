package  sdt.project.common.exception;

import org.springframework.http.HttpStatus;

// 422
public class ValidationException extends ApiException {

    public ValidationException(String message) {
        super(HttpStatus.UNPROCESSABLE_ENTITY, message);
    }
}
