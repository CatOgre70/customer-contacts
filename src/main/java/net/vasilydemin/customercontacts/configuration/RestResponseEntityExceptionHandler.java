package net.vasilydemin.customercontacts.configuration;

import net.vasilydemin.customercontacts.exception.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Custom exception handler for all exceptions in exception package
 * It allows to generate and transfer to frontend not only status codes, but bodies with detailed
 * information regarding exception
 * Well, it is nice, but needs fewer development (please see comment within text of the handler)
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value =
            {   ContactTypeIsWrongException.class,
                CustomerMustNotBeNullException.class,
                CustomerWithSuchIdNotFoundException.class,
                CustomerWithSuchNameNotFoundException.class,
                EmailIsInTheDatabaseAlreadyException.class,
                EmailWithSuchIdNotFoundException.class,
                PhoneIsInTheDatabaseAlreadyException.class,
                PhoneWithSuchIdNotFoundException.class
            })
    protected ResponseEntity<Object> handleConflict(
            RuntimeException ex, WebRequest request) {

        // Well, this is demo of exception handler. Should be extended with StackTrace
        // .toString printing and converting it to Json
        // Source: https://www.baeldung.com/exception-handling-for-rest-with-spring
        // May the Holy Baeldung be glorified forever and ever, amen! :)
        String bodyOfResponse = "{\"reason\":\"" + ex.getMessage() +"\", \"stackDepth\":"
                + ex.getStackTrace().length + "}";
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
