package net.mindview.test;

/**
 * \* Created with Chen Zhe on 1/3/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class Parcel10 {

    /**
     * Wrapping wrapping.
     *
     * @param x the x               注意这里没有使用final，这是因为x被传递给构造方法使用，而不是其他方法
     * @return the wrapping         有参构造方法，这取决于其实现的类是否定义了该构造方法。
     */
    public Wrapping wrapping(int x){
        return new Wrapping(x){
            public int value(){
                return super.value() * 47;
            }
        };
    }

    public static void main(String[] args) {
        Parcel10 p10 = new Parcel10();
        Wrapping wrapping = p10.wrapping(10);
    }
}

/**
 * The type Wrapping.
 */
class Wrapping{
    private int i;
    public Wrapping(int x){i = x;}
    public int value(){return i;}
}