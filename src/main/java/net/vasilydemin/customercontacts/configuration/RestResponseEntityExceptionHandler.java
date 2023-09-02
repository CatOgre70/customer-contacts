package net.vasilydemin.customercontacts.configuration;

import net.vasilydemin.customercontacts.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value
            = { ContactTypeIsWrongException.class, CustomerMustNotBeNullException.class,
                CustomerWithSuchIdNotFoundException.class, CustomerWithSuchNameNotFoundException.class,
                EmailIsInTheDatabaseAlreadyException.class, EmailWithSuchIdNotFoundException.class,
            PhoneIsInTheDatabaseAlreadyException.class, PhoneWithSuchIdNotFoundException.class})
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "{\"reason\":\"" + ex.getMessage() +"\", \"stackDepth\":" + ex.getStackTrace().length + "}";
        HttpStatus httpStatus;
        if(ex.getClass().equals(ContactTypeIsWrongException.class)
                || ex.getClass().equals(CustomerMustNotBeNullException.class)
                || ex.getClass().equals(EmailIsInTheDatabaseAlreadyException.class)
                || ex.getClass().equals(PhoneIsInTheDatabaseAlreadyException.class)){
            httpStatus = HttpStatus.BAD_REQUEST;
        } else if(ex.getClass().equals(CustomerWithSuchIdNotFoundException.class)
                || ex.getClass().equals(CustomerWithSuchNameNotFoundException.class)
                || ex.getClass().equals(EmailWithSuchIdNotFoundException.class)
                || ex.getClass().equals(PhoneWithSuchIdNotFoundException.class)) {
            httpStatus = HttpStatus.NOT_FOUND;
        } else {
            httpStatus = HttpStatus.CONFLICT;
        }
        return handleExceptionInternal(ex, bodyOfResponse,
                new HttpHeaders(), httpStatus, request);
    }
}
