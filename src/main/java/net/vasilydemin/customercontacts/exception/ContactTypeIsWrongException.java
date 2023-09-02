package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Contact type is wrong")
public class ContactTypeIsWrongException extends RuntimeException{

    public ContactTypeIsWrongException(String msg) {
        super(msg);
    }

}
