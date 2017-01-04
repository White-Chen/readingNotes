package exercise23;

/**
 * Created by ChenZhePC on 2017/1/4.
 * location Page:371
 */
public class B {

    private U[] as;
    private int next;

    public B(int arrayLength){
        as = new U[arrayLength];
        next = 0;
    }

    public void add(U u){
        if(next < as.length)
            as[next++] = u;
        else
            System.out.println("to the end");
    }

    public void remove(int index){
        if(index < as.length)
            as[index] = null;
    }

    public void iterator(){
        for (int i = 0; i < as.length; i++) {
            if (as[i] == null)
                continue;
            as[i].method_1();
            as[i].method_2();
            as[i].method_3();
            System.out.println();
        }
    }


    public static void main(String[] args) {
        int length = 10;
        B b = new B(length);
        for (int i = 0; i < length; i++) {
            A tempA = new A();
            b.add(tempA.getU());
        }
        b.iterator();

        b.remove(0);
        b.remove(4);
        b.remove(9);
        b.iterator();
    }
}
