package net.mindview.test;

/**
 * \* Created with Chen Zhe on 12/22/2016.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
class Super {
    public int i = 0;
    public int getI(){return i;}
}

class Sub extends Super{
    public int i = 1;
    @Override
    public int getI(){return i;}
    public int getSuperI(){return super.i;}
}

public class FieldAccess{
    public static void main(String[] args){
        Super sup = new Sub();
        System.out.println("sup.i = " + sup.i +
                ", sup.getI() = " + sup.getI());
        Sub sub = new Sub();
        System.out.println("sub.i = " + sub.i +
                ", sub.getI() = " + sub.getI() +
                ", sub.getSuperI() = " + sub.getSuperI());
    }
}