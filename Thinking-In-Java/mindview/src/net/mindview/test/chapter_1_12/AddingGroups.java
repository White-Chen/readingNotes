package net.mindview.test.chapter_1_12;

import java.util.*;

/**
 * Created by ChenZhePC on 2017/1/6.
 */

class Snow{}
class Powder extends Snow{}
class Light extends Powder{}
class Heavy extends Powder{}
class Crusty extends Snow{}
class Slush extends Snow{}
public class AddingGroups {
    public static void main(String[] args) {

        // 注意这里新建一个ArrayList副本，而不是直接使用原Array
        //Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
        //Integer[] moreInts = {6, 7, 8, 9, 10};

        // 方法1
        //collection.addAll(Arrays.asList(moreInts));
        // 方法2
        //Collections.addAll(collection, 11, 12, 13, 14, 15);
        // 方法3
        //Collections.addAll(collection, moreInts);

        // 这里直接使用原Array
        //List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
        //list.set(1, 21);
        //list.add(21);
        /*Exception in thread "main" java.lang.UnsupportedOperationException
        at java.util.AbstractList.add(AbstractList.java:148)
        at java.util.AbstractList.add(AbstractList.java:108)
        at AddingGroups.main(AddingGroups.java:25)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)*/

        List<Snow> snow1 = Arrays.asList(
                new Crusty(),
                new Slush(),
                new Powder()
        );

        //如果移除下面注释。编译器会报错：Error:(50, 41) java: incompatible types: java.util.List<Powder> cannot be converted to java.util.List<Snow>
        //List<Snow> snows = Arrays.asList(
        //        new Light(),
        //        new Heavy()
        //);

        List<Snow> snows = new ArrayList<Snow>();
        Collections.addAll(snows, new Light(), new Heavy());

        List<Snow> snow4 = Arrays.<Snow>asList(
                new Light(), new Heavy()
        );
    }
}
