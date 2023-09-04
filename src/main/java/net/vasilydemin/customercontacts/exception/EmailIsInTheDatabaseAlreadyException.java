package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will be generated when we are trying to create (save) new Email entity record in the database,
 * but email record with the same email is in the database already and owned by another customer
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error: email is in the database already")
public class EmailIsInTheDatabaseAlreadyException extends RuntimeException{

    public EmailIsInTheDatabaseAlreadyException(String msg) {
        super(msg);
    }

}
