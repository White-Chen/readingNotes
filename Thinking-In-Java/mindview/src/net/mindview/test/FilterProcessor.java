package net.mindview.test;

import java.util.Arrays;

/**
 * \* Created with Chen Zhe on 12/25/2016.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */


/**
 * The type Upcase.
 * 具体策略角色 ConcreteStrategy
 */
class Upcase implements Processor{

    public String name() {
        return "Using Upcase Processor";
    }

    public String process(Object input) {
        return ((String) input).toUpperCase();
    }
}

/**
 * The type Downcase.
 * 具体策略角色 ConcreteStrategy
 */
class Downcase implements Processor{

    public String name() {
        return "Using Downcase Processor";
    }

    public String process(Object input) {
        return ((String)input).toLowerCase();
    }
}

/**
 * The type Spliter.
 * 具体策略角色 ConcreteStrategy
 */
class Spliter implements Processor{

    public String name() {
        return "Using Spliter Processor";
    }

    public String process(Object input) {
        return Arrays.toString(((String) input).split(" "));
    }
}

///**
// * The type Apply.
// * 环境角色 Context
// */
//public class Apply {
//    /**
//     * The constant s.
//     */
//    public static String s = "This is an example of strategy design pattern.";
//
//    /**
//     * Process.
//     *
//     * @param p the p 持有策略引用
//     * @param o the o 待处理对象
//     */
//    public static void process(Processor p, Object o){
//        System.out.println(p.name());
//        System.out.println(p.process((o)));
//    }
//
//    /**
//     * The entry point of application.
//     * 用于测试
//     * @param args the input arguments
//     */
//    public static void main(String[] args) {
//        process(new Upcase(),s);
//        process(new Downcase(),s);
//        process(new Spliter(),s);
//    }
//}

/**
 * The type Waveform.
 */
class Waveform{
    private static long counter;
    private final long id = counter ++;
    @Override
    public String toString(){return "Waveform " + id;}
}

/**
 * 抽象策略 Strategy.
 * 目标角色 Target
 */
interface Processor{
    /**
     * Name string.
     *
     * @return the string
     */
    public String name();

    /**
     * Process object.
     *
     * @param input the input
     * @return the object
     */
    Object process(Object input);
}

/**
 * The type Filter.
 * 源角色 Adapee
 */
class Filter{

    /**
     * Name string.
     *
     * @return the string
     */
    public String name(){
        return getClass().getSimpleName();
    }

    /**
     * Process waveform.
     *
     * @param input the input
     * @return the waveform
     */
    public Waveform process(Waveform input){
        return input;
    }
}

/**
 * The type Low pass.
 * 源角色的具体实现 Adapee
 */
class LowPass extends Filter{
    /**
     * The Cutoff.
     */
    double cutoff;

    /**
     * Instantiates a new Low pass.
     *
     * @param cutoff the cutoff
     */
    public LowPass(double cutoff){this.cutoff  = cutoff;}

    @Override
    public Waveform process(Waveform input) {
        return super.process(input);
    }
}

/**
 * The type High pass.
 * 源角色的具体实现 Adapee
 */
class HighPass extends Filter{
    /**
     * The Cutoff.
     */
    double cutoff;

    /**
     * Instantiates a new High pass.
     *
     * @param cutoff the cutoff
     */
    public HighPass(double cutoff){this.cutoff = cutoff;}

    @Override
    public Waveform process(Waveform input) {
        return super.process(input);
    }
}

/**
 * The type Band pass.
 * 源角色的具体实现 Adapee
 */
class BandPass extends Filter{
    /**
     * The Lowoff.
     */
    double lowoff, /**
     * The Highoff.
     */
    highoff;

    /**
     * Instantiates a new Band pass.
     *
     * @param lowoff  the lowoff
     * @param highoff the highoff
     */
    public BandPass(double lowoff, double highoff){
        this.lowoff = lowoff;
        this.highoff = highoff;
    }

    @Override
    public Waveform process(Waveform input) {
        return super.process(input);
    }
}

/**
 * The type Filter adpter.
 * 适配器角色 Adaper 将 {@link Filter}接口转换为{@link Processor}接口。
 */
class FilterAdpter extends Filter implements Processor{

    private Filter filter;

    /**
     * Instantiates a new Filter adpter.
     *
     * @param filter the filter
     */
    public FilterAdpter(Filter filter){this.filter = filter;}

    public Waveform process(Object input) {
        return filter.process((Waveform) input);
    }
}

/**
 * The type Apply.
 * 环境角色 Context
 */
class Apply {
    /**
     * The constant s.
     *
     * @param p the p
     * @param o the o
     */
    public static void process(Processor p, Object o){
        System.out.println(p.name());
        System.out.println(p.process((o)));
    }
}

/**
 * The type Filter processor.
 * 测试类
 */
public class FilterProcessor{
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Waveform w = new Waveform();
        Apply.process(new FilterAdpter(new LowPass(1.0)),w);
        Apply.process(new FilterAdpter(new HighPass(2.0)),w);
        Apply.process(new FilterAdpter(new BandPass(3.0,4.0)),w);
    }
}