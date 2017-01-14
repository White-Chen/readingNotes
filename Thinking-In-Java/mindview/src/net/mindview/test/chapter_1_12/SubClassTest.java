package net.mindview.test.chapter_1_12;

/**
 * Created by ChenZhePC on 2016/12/22.
 */
public class SubClassTest extends BaseClass{

    protected int weight;
    public SubClassTest(String name, int size, int length, int weight) {
        super(name, size, length);
        this.weight = weight;
        System.out.println("construct SubClassTest");
    }
    @Override
    public void finalize() throws Throwable{
        System.out.println("do something to dispose SubClassTest");
        super.finalize();
    }

    public static void main(String[] args) throws Throwable {
        SubClassTest test = new SubClassTest("test",1,2,3);
        test = null;
        System.gc();
    }
}

class BaseBaseBaseClass{
    protected String name;
    public BaseBaseBaseClass(String name){
        this.name = name;
        System.out.println("Construct BaseBaseBaseClass");
    }

    @Override
    protected void finalize() throws Throwable {
        System.out.println("do something to dispose BaseBaseBaseClass");
        super.finalize();
    }
}

class BaseBaseClass extends BaseBaseBaseClass{
    protected int size;
    public BaseBaseClass(String name, int size){
        super(name);
        this.size = size;
        System.out.println("construct BaseBaseClass");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("do something to dispose BaseBaseClass");
        super.finalize();
    }
}

class BaseClass extends BaseBaseClass{
    protected int length;
    public BaseClass(String name, int size, int length){
        super(name,length);
        this.length = length;
        System.out.println("construct BaseClass");
    }
    @Override
    protected void finalize() throws Throwable {
        System.out.println("do something to dispose BaseClass");
        super.finalize();
    }
}