package spittr.web;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import spittr.exception.DuplicateException;
import spittr.exception.SpittleNotFoundException;

/**
 * \* Created with Chen Zhe on 1/20/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@ControllerAdvice
public class AppGlobalExceptionHandler {

    @ExceptionHandler(DuplicateException.class)
    public String duplicateSpittleHandler(){
        return "error/duplicate";
    }

    @ExceptionHandler(SpittleNotFoundException.class)
    public String spittleNotFoundException(){
        return "error/notFound";
    }
}
