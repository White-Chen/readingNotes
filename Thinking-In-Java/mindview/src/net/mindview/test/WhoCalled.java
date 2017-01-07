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
    static void f() throws Exception {
        try{
            throw new Exception();
        } catch (Exception e){
            throw (Exception) new Exception("f() exception").initCause(e);
        }
    }
    static void g() throws Exception {
        try {
            f();
        } catch (Exception e) {
            throw (Exception) new Exception("g() exception").initCause(e);
        }
    }
    static void h() throws Exception {
        try {
            g();
        } catch (Exception e) {
            throw (Exception) new Exception("h() exception").initCause(e);
        }
    }

    public static void main(String[] args) {
        try {
            WhoCalled.h();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
