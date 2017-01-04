package exercise26;

/**
 * \* Created with Chen Zhe on 1/4/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class InheritInner extends WithInner.Inner{
    //InheritInner(){} IDE会提示没有默认的构造方法
    //如下可以实现，但是感觉不是很明白这种语法。为什么with的super会指向它的内部类？
    InheritInner(WithInner with, String outStr){
        with.super(outStr);
    }

    public static void main(String[] args) {
        WithInner wi = new WithInner();
        InheritInner ii = new InheritInner(wi,"take easy");
    }
}
