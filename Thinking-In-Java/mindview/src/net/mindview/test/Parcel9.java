package net.mindview.test;

/**
 * \* Created with Chen Zhe on 1/3/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Parcel9 {
    /**
     * Destination destination.
     *
     * @param dest the dest
     *             注意在匿名内部类中使用类定义以外的对象时，这里需要用final修饰形参引用，这是语法要求！！
     * @return the destination 返回一个匿名内部类，因为没有类名，所以不能有显式的构造方法。
     */
    public Destination destination(final String dest){
        return new Destination() {
            private String label = dest;

            @Override
            public String readLabel() {
                return label;
            }
        }; // 注意这里使用分号结束，因为这是一个表达式。
    }
}
