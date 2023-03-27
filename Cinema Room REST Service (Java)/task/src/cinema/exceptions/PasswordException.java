package cinema.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class PasswordException extends RuntimeException {
    public PasswordException(String s) {
        super(s);
    }
}
