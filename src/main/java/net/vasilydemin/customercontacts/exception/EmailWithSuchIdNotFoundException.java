package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Email with such id was not found in the database")
public class EmailWithSuchIdNotFoundException extends RuntimeException {

    public EmailWithSuchIdNotFoundException(String msg) {
        super(msg);
    }

}
