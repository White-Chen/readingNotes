package net.mindview.test.chapter_1_12;

/**
 * \* Created with Chen Zhe on 1/3/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Parcel11 {
    /**
     * Get base base.
     *
     * @param i the         传递给构造方法的对象引用，不需要加final
     * @return the base     返回匿名内部类
     */
    public static Base getBase(int i){
        return new Base(i) {
            // 实例初始化，调用优于构造器
            {
                System.out.println("Inside instance initializer");
            }
            @Override
            public void f() {
                System.out.println("In anonymous f()");
            }
        };
    }

    /**
     * The entry point of application.
     * 测试
     * @param args the input arguments
     */
    public static void main(String[] args) {
        Base base = Parcel11.getBase(47);
        base.f();
    }
}

/**
 * The type Base. 接口类
 */
abstract class Base{
    /**
     * Instantiates a new Base.
     * 有参构造方法
     * @param i the
     */
    public Base(int i ){
        System.out.println("Base constructor, i = " + i);
    }

    /**
     * F.
     */
    public abstract void f();
}
