package net.mindview.test.chapter_1_12;

/**
 * \* Created with Chen Zhe on 1/2/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */

/**
 * 迭代器接口
 */
interface InnerClassSelector{
    /**
     * Has next boolean.
     *
     * @return the boolean
     */
    boolean hasNext();

    /**
     * Next object.
     *
     * @return the object
     */
    Object next();
}

/**
 * 外围类
 */
public class InnerClassSequence {

    //注意这里是private，但是在内部类中仍然可以直接操作
    private Object[] items;
    private int next = 0;

    /**
     * Instantiates a new Inner class sequence.
     *
     * @param size the size
     */
    public InnerClassSequence(int size){
        items = new Object[size];
    }

    /**
     * Add.
     *
     * @param x the x
     */
    public void add(Object x){
        if(next < items.length)
            items[next++] = x;
    }

    /**
     * 迭代器接口通过内部类实现
     */
    private class SequenceSelector implements InnerClassSelector{

        private int i = 0;

        @Override
        public boolean hasNext() {
            return i != items.length;
        }

        @Override
        public Object next() {
            return hasNext() ? items[i++] : null;
        }
    }

    /**
     * Selector inner class selector.
     *
     * @return the inner class selector
     */
    public InnerClassSelector selector(){
        return new SequenceSelector();
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        InnerClassSequence sequence = new InnerClassSequence(10);
        for (int i = 0; i < 10; i++) {
            sequence.add(Integer.toString(i));
        }
        InnerClassSelector selector = sequence.selector();
        while(selector.hasNext()){
            System.out.print(selector.next() + " ");
        }
    }
}
