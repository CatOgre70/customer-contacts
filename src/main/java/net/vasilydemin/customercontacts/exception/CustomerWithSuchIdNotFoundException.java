package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * This exception will be generated when we are trying to find customer record with specified id in the database,
 * but without success
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer with such id was not found in the database")
public class CustomerWithSuchIdNotFoundException extends RuntimeException {

    public CustomerWithSuchIdNotFoundException(String msg) {
        super(msg);
    }

}
