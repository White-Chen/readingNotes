package net.mindview.test.chapter_1_12;

/**
 * Created by ChenZhePC on 2016/12/23.
 */
public class Sandwich extends PortableLunch{
    private static int i;
    private Bread b = new Bread("Sandwich");
    private Cheese c = new Cheese("Sandwich");
    private Lettuce l = new Lettuce("Sandwich");
    public Sandwich(String callMethodName) {
        super(callMethodName);
        System.out.println("Sandwich " + callMethodName + " " + i++);
    }
    public static void main(String[] args) {
        new Sandwich("Sandwich");
    }
}
class Meal{
    private static int i;
    Meal(String callMethodName){
        System.out.println("Meal " + callMethodName + " " + i++);
    }
}
class Bread{
    private static int i;
    Bread(String callMethodName){
        System.out.println("Bread " + callMethodName + " " + i++);
    }
}
class Cheese{
    private static int i;
    Cheese(String callMethodName){
        System.out.println("Cheese " + callMethodName + " " + i++);
    }
}
class Lettuce{
    private static int i;
    Lettuce(String callMethodName){
        System.out.println("Lettuce " + callMethodName + " " + i++);
    }
}
class Lunch extends Meal{
    private static int i;
    private Bread b = new Bread("Lunch");
    private Cheese c = new Cheese("Lunch");
    private Lettuce l = new Lettuce("Lunch");
    Lunch(String callMethodName){
        super(callMethodName);
        System.out.println("Lunch " + callMethodName + " " + i++);
    }
}
class PortableLunch extends Lunch{
    private static int i;
    private Bread b = new Bread("PortableLunch");
    private Cheese c = new Cheese("PortableLunch");
    private Lettuce l = new Lettuce("PortableLunch");
    PortableLunch(String callMethodName){
        super("PortableLunch");
        System.out.println("PortableLunch " + callMethodName + " " + i++);
    }
}
