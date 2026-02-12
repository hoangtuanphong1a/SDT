package  sdt.project.common.exception;

import org.springframework.http.HttpStatus;
// 409
public class ConflictException extends ApiException {
    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }
}
