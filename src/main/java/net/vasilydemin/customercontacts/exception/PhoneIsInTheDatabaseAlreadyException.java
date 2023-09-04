package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will be generated when we are trying to create (save) new Phone entity record in the database,
 * but phone record with the same phone is in the database already and owned by another customer
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Error: phone is in the database already")
public class PhoneIsInTheDatabaseAlreadyException extends RuntimeException{

    public PhoneIsInTheDatabaseAlreadyException(String msg) {
        super(msg);
    }

}
