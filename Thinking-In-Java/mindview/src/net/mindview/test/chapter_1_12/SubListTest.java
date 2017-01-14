package net.mindview.test.chapter_1_12;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * \* Created with Chen Zhe on 1/6/2017.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class SubListTest {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<Integer>();
        list1.add(1);list1.add(2);list1.add(3);
        list1.add(4);list1.add(5);list1.add(6);
        list1.add(7);list1.add(8);list1.add(9);
        List<Integer> subList = list1.subList(3,6);

        System.out.println(list1);
        System.out.println(subList);

        Collections.reverse(list1);
        System.out.println(list1);
        System.out.println(subList);

        Collections.shuffle(list1);
        System.out.println(list1);
        System.out.println(subList);

        subList.remove(0);
        System.out.println(list1);
        System.out.println(subList);

        list1.remove(2);
        System.out.println(list1);
        System.out.println(subList);

    }
}
