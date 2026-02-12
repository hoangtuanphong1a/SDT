package  sdt.project.common.exception;

import org.springframework.http.HttpStatus;
// 404
public class NotFoundException extends ApiException {
    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
