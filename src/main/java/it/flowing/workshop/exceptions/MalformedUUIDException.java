package it.flowing.workshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class MalformedUUIDException extends IllegalArgumentException {
    public MalformedUUIDException(Throwable cause) {
        super(cause);
    }
}
