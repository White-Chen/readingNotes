package net.mindview.test;

/**
 * Created by ChenZhePC on 2016/12/23.
 */
public class ReferenceCounting {
    public static void main(String[] args) {
        Shared shared = new Shared();
        Composing[] s = {new Composing(shared),new Composing(shared),new Composing(shared),new Composing(shared),new Composing(shared)};
        for (int i = 0; i < s.length; i++) {
            s[i].dispose();
        }
    }
}

class Shared{
    private int refcount = 0;
    private static long counter = 0;
    private final long id = counter ++;
    public Shared(){
        System.out.println("Creating " + this);
        refcount ++;
    }
    public void addRef(){

    }
    protected void dispose(){
        if(--refcount == 0)
            System.out.println("Dispose " + this);
    }
    @Override
    public String toString() {
        return "Shared "+ id;
    }
}

class Composing{
    private Shared shared;
    private static long counter = 0;
    private final long id = counter ++;
    public Composing(Shared shared){
        System.out.println("Creating " + this);
        this.shared = shared;
        /*this.shared.addRef();*/
    }
    protected void dispose(){
        System.out.println("disposing " + this);
        shared.dispose();
    }
    @Override
    public String toString() {
        return "Composing " + id;
    }
}
