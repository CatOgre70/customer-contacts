package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Customer with such id was not found in the database")
public class CustomerWithSuchIdNotFoundException extends RuntimeException {

    public CustomerWithSuchIdNotFoundException(String msg) {
        super(msg);
    }

}
