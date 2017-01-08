package net.mindview.test;

/**
 * \* Created with Chen Zhe on 1/8/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
class BaseballException extends Exception{}
class Foul extends BaseballException{}
class Strike extends BaseballException{}
class StormException extends Exception{}
class RainedOut extends StormException{}
class PopFoul extends Foul{}

abstract class Inning{
    public Inning() throws BaseballException{throw new BaseballException();}
    public void event() throws BaseballException{}
    public void walk(){}
    public abstract void asBat() throws Strike, Foul;
}

interface Storm{
    public void event() throws RainedOut;
    public void rainHard() throws RainedOut;
}

public class StormyInning extends Inning implements Storm{

    public StormyInning()
            throws RainedOut, BaseballException {
    }

    // 在包含父类构造方法异常的同时，可以自己加异常，这个在其他普通方法上是不允许的。
    public StormyInning(String s)
        throws Foul, BaseballException{
    }

    // 不能抛出接口异常，因为在基类中重新说明了异常
    //public void event() throws RainedOut{}

    // 同样不能这样声明，因为基类与接口说明冲突
    //public void event() throws BaseballException{}

    // 可以这样声明
    public void event(){}

    // 抛出异常可以是被覆盖方法的异常子类，并且可以省略部分父类异常
    @Override
    public void asBat() throws PopFoul {
        throw new PopFoul();
    }

    // 以接口声明为主
    @Override
    public void rainHard() throws RainedOut {}

    public static void main(String[] args) {
        try {
            StormyInning si = new StormyInning();
            si.asBat();
        }catch (Strike e) {
            System.out.println("Strike");
        }catch (PopFoul e){// 捕获PopFoul
            System.out.println("Pop foul");
        }catch (RainedOut e){
            System.out.println("Rained out");
        }catch (BaseballException e){
            System.out.println("Generic baseball exception");
        }

        try{
            Inning i = new StormyInning();
            i.asBat();
        }catch (Strike e){
            System.out.println("Strike");
        }catch (Foul e){ // 捕获覆盖方法的PopFoul异常，因为是子类所以可以匹配
            System.out.println("Foul");
        }catch (RainedOut e){
            System.out.println("Rained out");
        }catch (BaseballException e){
            System.out.println("Generic baseball exception");
        }
    }
}
