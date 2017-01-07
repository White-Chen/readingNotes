package net.mindview.test;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.Logger;

/**
 * Created by ChenZhePC on 2017/1/7.
 */
class MyException2 extends Exception {
    private int x;
    public MyException2(){}
    public MyException2(String mes){super(mes);}
    public MyException2(String mes, int x){
        super(mes);
        this.x = x;
    }
    public int val(){return x;}

    @Override
    public String getMessage() {
        return "Detail message: " + x + " " + super.getMessage();
    }
}

public class LoggingExceptions{

    private static Logger logger =
            Logger.getLogger("LoggingExceptions");

    static void logException(Exception e){
        StringWriter writer = new StringWriter();
        //使用重载方法
        e.printStackTrace(new PrintWriter(writer));
        logger.severe(writer.toString());
    }

    public static void main(String[] args) {

        try{
            throw new NullPointerException();
        }catch (NullPointerException exception){
            logException(exception);
        }
        try {
            throw new MyException2();
        }catch (MyException2 e){
            logException(e);
        }
        try{
            throw new MyException2("3rd try block");
        }catch (MyException2 e){
            logException(e);
        }
        try{
            throw new MyException2("4th try block",4);
        }catch (MyException2 e){
            System.out.println("e.val = " + e.val());
            logException(e);
        }
    }
}
