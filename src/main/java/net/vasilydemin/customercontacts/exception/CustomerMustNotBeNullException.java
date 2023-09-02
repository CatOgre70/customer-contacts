package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Customer id mustn't be null")
public class CustomerMustNotBeNullException extends RuntimeException {

    public CustomerMustNotBeNullException(String msg) {
        super(msg);
    }

}
