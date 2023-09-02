package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error: phone is in the database already")
public class PhoneIsInTheDatabaseAlreadyException extends RuntimeException{

    public PhoneIsInTheDatabaseAlreadyException(String msg) {
        super(msg);
    }

}
