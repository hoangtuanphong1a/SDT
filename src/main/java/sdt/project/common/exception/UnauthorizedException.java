package  sdt.project.common.exception;

import org.springframework.http.HttpStatus;

// 401
public class UnauthorizedException extends ApiException {
    public UnauthorizedException(String message) {
        super(HttpStatus.UNAUTHORIZED, message);
    }
}
