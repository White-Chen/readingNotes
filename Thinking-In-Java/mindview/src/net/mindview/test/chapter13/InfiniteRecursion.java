package net.mindview.test.chapter13;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZhePC on 2017/1/23.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */
public class InfiniteRecursion{

    @Override
    public String toString() {
        return "InfiniteRecursion address : " + this + "\n";
    }

    public static void main(String[] args) {
        List<InfiniteRecursion> list =
                new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new InfiniteRecursion());
        }
        System.out.println(list);
    }
}
