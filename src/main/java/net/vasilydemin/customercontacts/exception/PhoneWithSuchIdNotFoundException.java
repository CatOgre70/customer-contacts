package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will be generated when we are trying to find phone record with specified id in the database,
 * but without success
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Phone with such id was not found in the database")
public class PhoneWithSuchIdNotFoundException extends RuntimeException {

    public PhoneWithSuchIdNotFoundException(String msg) {
        super(msg);
    }

}
