package spittr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * \* Created with Chen Zhe on 1/20/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

@ResponseStatus(value = HttpStatus.BAD_REQUEST,
                reason = "Duplicate Spittle in Spittles")
public class DuplicateException extends Exception{
}
