package net.vasilydemin.customercontacts.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class NotFoundExceptions extends RuntimeException {

    public NotFoundExceptions(){
        super();
    }

    public NotFoundExceptions(String msg){
        super(msg);
    }

    public NotFoundExceptions(Throwable cause){
        super(cause);
    }
    public NotFoundExceptions(String msg, Throwable cause){
        super(msg, cause);
    }

}
