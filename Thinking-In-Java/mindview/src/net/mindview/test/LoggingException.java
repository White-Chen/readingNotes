package net.mindview.test;

import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Created by ChenZhePC on 2017/1/7.
 */
public class LoggingException extends Exception {
    private static Logger logger =
            Logger.getLogger("LoggingException");

    public LoggingException(){
        StringWriter trace = new StringWriter();
        printStackTrace();
    }
}
