package exercise23;

/**
 * Created by ChenZhePC on 2017/1/4.
 */
public class A {
    private static long counter = 0;
    private final long id = counter ++;

    public U getU(){
        return new U() {
            @Override
            public void method_1() {
                System.out.println("Anonymous U in class A(id=" + id + ") -> method_1()");
            }

            @Override
            public void method_2() {
                System.out.println("Anonymous U in class A(id=" + id + ") -> method_2()");
            }

            @Override
            public void method_3() {
                System.out.println("Anonymous U in class A(id=" + id + ") -> method_3()");
            }
        };
    }
}
