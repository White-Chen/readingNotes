package net.mindview.test.chapter_1_12;

import java.util.*;

/**
 * \* Created with Chen Zhe on 12/12/2016.
 * \* Description:
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class ForeachTest {
    public static void main(String[] args) {
        List<Integer> list = new ArrayList<Integer>();
        Integer[] array = new Integer[1000000];
        Set<Integer> set = new HashSet<Integer>();
        long startTime;
        long endTime;
        for(int n=0; n < 1000000; n++)
        {
            list.add(n);
            array[n] = n;
            set.add(n);
        }

        //Type 1
        startTime = Calendar.getInstance().getTimeInMillis();
        for(Integer i : array){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In Array For each loop :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(Integer i : list){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List For each loop :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(Integer i : set){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In HashSet For each loop :: " + (endTime - startTime) + " ms");

        //Type 2
        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = 0; j < array.length ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In Array Using array.length :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = 0; j < list.size() ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List Using collection.size() :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = 0; j < set.size() ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In set Using collection.size() :: " + (endTime - startTime) + " ms");

        //Type 3
        startTime = Calendar.getInstance().getTimeInMillis();
        int size = array.length;
        for(int j = 0; j < size ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List Using [int size = array.length; int j = 0; j < size ; j++] :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        size = list.size();
        for(int j = 0; j < size ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List Using [int size = list.size(); int j = 0; j < size ; j++] :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        size = set.size();
        for(int j = 0; j < size ; j++){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List Using [int size = set.size(); int j = 0; j < size ; j++] :: " + (endTime - startTime) + " ms");

        //Type 4
        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = array.length; j > 0 ; j--){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In Array Using [int j = list.size(); j > size ; j--] :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = list.size(); j > 0 ; j--){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In List Using [int j = list.size(); j > size ; j--] :: " + (endTime - startTime) + " ms");

        startTime = Calendar.getInstance().getTimeInMillis();
        for(int j = set.size(); j > 0 ; j--){}
        endTime = Calendar.getInstance().getTimeInMillis();
        System.out.println("In HashSet Using [int j = list.size(); j > size ; j--] :: " + (endTime - startTime) + " ms");
    }
}
