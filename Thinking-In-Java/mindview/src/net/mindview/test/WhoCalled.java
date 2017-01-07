package net.mindview.test;

/**
 * \* Created with Chen Zhe on 1/7/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class WhoCalled {
    static void f() throws Exception {throw new Exception();}
    static void g() throws Exception {f();}
    static void h() throws Exception {
        try {
            f();
        }finally {
            return;
        }
    }

    public static void main(String[] args) {
        try {
            WhoCalled.h();
            System.out.println("can not catch exception from f()");
        } catch (Exception e) {
            System.out.println("catch exception from f()");
        }
    }
}
