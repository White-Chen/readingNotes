package net.mindview.test.chapter_1_12;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * \* Created with Chen Zhe on 1/8/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
class WrapCheckedException{

    // 可以发现抛出RuntimeException时不需要在方法签名中说明抛出异常
    void throwRunctimeException(int type){
        try{
            switch (type){
                case 0: throw new FileNotFoundException();
                case 1: throw new IOException();
                case 2: throw new RuntimeException("Where am I?");
            }
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}

class SomeOtherException extends Exception{}

public class TurnOffChecking {
    public static void main(String[] args) {
        WrapCheckedException wrapCheckedException = new WrapCheckedException();
        // 看输出结果可以发现，如果不catch，那么这里虽然抛了异常但是你不需要做任何额外操作，就好像这里不会报错一样。
        wrapCheckedException.throwRunctimeException(3);
        // 也可以手动捕获异常
        for (int i = 0; i < 4; i++) {
            try{
                if(i < 3)
                    wrapCheckedException.throwRunctimeException(i);
                else
                    throw new SomeOtherException();
            }catch (SomeOtherException e){
                System.out.println("SomeOtherException : " + e);
            }catch (RuntimeException re){
                try{
                    throw re.getCause();
                } catch (FileNotFoundException e){
                    System.out.println("FileNotFondException " + e);
                } catch (IOException e){
                    System.out.println("IOException " + e);
                } catch (Throwable throwable) {
                    System.out.println("Throwable " + throwable);
                }
            }
        }
    }
}
