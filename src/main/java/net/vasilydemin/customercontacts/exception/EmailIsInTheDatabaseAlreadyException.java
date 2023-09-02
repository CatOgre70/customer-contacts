package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error: email is in the database already")
public class EmailIsInTheDatabaseAlreadyException extends RuntimeException{

    public EmailIsInTheDatabaseAlreadyException(String msg) {
        super(msg);
    }

}
