###Chapter 4 : Controlling Execution
####1. _**增强循环/foreach**_
+ 对实现 **Iterable** 接口的任何类都可以使用 **foreach**.  
+ 实现方式是为迭代对象新建一个迭代器对象Iterator, 然后进行常规化的for循环, 因为需要一个新的对象, 该过程需要花费一定时间, 相对来说数组的速度最快.  
+ 优点:  
    + 下标不可控, 因此不会出现越界问题.  
    + 书写简明, 且在实际环境中使用更平凡
    + SE8中, 结合Lambda表达式可以实现迭代Map
+ 缺点:  
    + 迭代过程中, 不可对引用做出增删改, 但是引用指向对象的内容还是可以修改的.  
    + 下标不可控, 因此只能做到顺序迭代, 而for循环则可以灵活控制. 
+ 简单的效率对比，不同方法都有差别，总体ArrayList最快，foreach读取方法速度也不如for来的快，倒读比正读慢。
    ```java
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
    ```
    ```java
    /* console out:
        "C:\Program Files\Java\jdk1.8.0_91\bin\java" -......
        In Array For each loop :: 18 ms
        In List For each loop :: 13 ms
        In HashSet For each loop :: 26 ms
        In Array Using array.length :: 14 ms
        In List Using collection.size() :: 6 ms
        In set Using collection.size() :: 15 ms
        In List Using [int size = array.length; int j = 0; j < size ; j++] :: 9 ms
        In List Using [int size = list.size(); int j = 0; j < size ; j++] :: 1 ms
        In List Using [int size = set.size(); int j = 0; j < size ; j++] :: 9 ms
        In Array Using [int j = list.size(); j > size ; j--] :: 2 ms
        In List Using [int j = list.size(); j > size ; j--] :: 11 ms
        In HashSet Using [int j = list.size(); j > size ; j--] :: 1 ms
    */
    ```
    
####2. _标签控制_
+ 标签起作用的唯一地方是迭代语句之前.
    ```java
    lebal1:
    outer_loop{
        lebal2:
        inner_loop{
            break;              //break inner_loop
            break lebal1;       //end cuurent loop and goto lebal1 and skip outer_loop
            break lebal2;       //end current loop and goto lebal2 and skip inner_loop
            continue;           //goto next inner_loop
            continue lebal1;    //end current loop and start new outer_loop
            continue lebal2;    //end current loop and start new inner_loop
        }
    }
    ```
+ **continue tagName** 结束当前循环跳转到标签位置, 会重新执行标签后的循环语句.  
+ **break tagName** 结束当前循环跳转到标签位置, 但是不会重新执行标签后的循环语句.  
+ 标签的主要用途是解决 **_continue_**, **_break_** 只能中断一层循环, 当遇到嵌套循环时控制力会下降.  

####3. _选择语句_
+ 支持 **short**, **char**, **byte**, **int** 四种基本类型以及他们的包装类, 前三种原理是将其转化为int类型.
+ SE5开始支持枚举类
    ```
    switch(short/char/byte/int/enum) {
        case value1 : statement; break;
        ......;
        default : statement;
    }
    ```
+ SE7开始支持String类型的选择判断, 其原理是是将字符串的哈希值作为其特征码, 然后进行选择判断, 最终仍然是通过判断int数值进行操作.  
