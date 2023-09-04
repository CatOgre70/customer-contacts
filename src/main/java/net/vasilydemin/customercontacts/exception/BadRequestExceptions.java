package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class BadRequestExceptions extends RuntimeException {
    public BadRequestExceptions(){
        super();
    }

    public BadRequestExceptions(String msg){
        super(msg);
    }

    public BadRequestExceptions(Throwable cause){
        super(cause);
    }
    public BadRequestExceptions(String msg, Throwable cause){
        super(msg, cause);
    }

}
