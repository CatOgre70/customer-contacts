package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception generating when frontend send to backend wrong contact type
 * ContactType - enum class, where types of contacts are defined
 * If ContactType.getContactTypeByName() method returns null, then service generates this exception
 */
@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Contact type is wrong")
public class ContactTypeIsWrongException extends NotFoundExceptions{

    public ContactTypeIsWrongException(String msg) {
        super(msg);
    }

}
