###Chapter 10 : Inner Classes
####1. _内部类/inner class_
+ 定义: 将一个类的定义放置于另一个类的定义内部. 
+ 使用:
    + 通过 **OuterClassName.InnerClassName** 进行获取. 
    + 通过在 **OuterClassName** 内定义一个返回 **InnerClassName** 引用的可访问方法(更常用). 
    
####2. _内部类访问外围类成员_
+ 内部类对象持有一个指向外围类对象的引用, 因此可以**无权限约束的访问**外围类的成员, 包括 **private** 修饰的成员. 
+ 内部类对象(非静态类)只能在与其外围类对象相关联的情况下才能被创建, 反过来说这也意味着创建内部类对象的前提是外围类对象已经被创建. :heavy_exclamation_mark:
+ 示例: 
    ```java
    package net.mindview.test;
    
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
    
        //注意这里是private, 但是在内部类中仍然可以直接操作
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
    
    /*Output:
    0 1 2 3 4 5 6 7 8 9 
    */
    ```
    
####3. [_设计模式_: 迭代器模式]() :bangbang:
+ 上面的实例中展示了一种叫做迭代器模式的简单实现, 书上一带而过, 下面做一些补充. 
+ 定义: 提供一种方法顺序访问一个聚合对象中的各种元素, 而又不暴露该对象的内部表示. 
+ 构成角色: 
    + 迭代器角色(Iterator):定义遍历元素所需要的方法, 一般来说会有这么三个方法: 取得下一个元素的方法next(), 判断是否遍历结束的方法hasNext()), 移出当前对象的方法remove(),
    + 具体迭代器角色(Concrete Iterator): 实现迭代器接口中定义的方法, 完成集合的迭代. 
    + 容器角色(Aggregate):  一般是一个接口, 提供一个iterator()方法, 例如java中的Collection接口, List接口, Set接口等.
    + 具体容器角色(ConcreteAggregate): 就是抽象容器的具体实现类, 比如List接口的有序列表实现ArrayList, List接口的链表实现LinkList, Set接口的哈希列表的实现HashSet等. 
    
+ 应用场景: 
    + 访问一个集合对象的内容而无需暴露它的内部表示
    + 支持对集合对象的多种遍历
    + 为遍历不同的集合结构提供一个统一的接口
    
+ 示例: 
    + 迭代器接口: 
    ```java
    public interface Iterator {
        public boolean hasNext();
        public Object next();
    }
    ```
    
   + 迭代器实现类: 
   ```java
    public class ConcreteIterator implements Iterator {
        //这里自定义一个list接口, 不要用系统自带的, 不然可能需要实现更多的方法
        private List list = null;
        private int index;
    
        public ConcreteIterator(List list) {
            super();
            this.list = list;
        }
    
        @Override
        public boolean hasNext() {
            if (index >= list.getSize()) {
                return false;
            } else {
                return true;
            }
        }
    
        @Override
        public Object next() {
            Object object = list.get(index);
            index++;
            return object;
        }
    
    }
    ```
    
    + 容器角色接口: 
    ```java
    //定义集合可以进行的操作
    public interface List {
        public void add(Object obj);  
        public Object get(int index);
        public Iterator iterator();  
        public int getSize();
    }
    ```
    
    + 容器角色实现类: 
    ```java
    public class ConcreteAggregate implements List{
    
        private Object[] list;
        private int size=0;
        private int index=0;
        public ConcreteAggregate(){
            index=0;
            size=0;
            list=new Object[100];
        }
        @Override
        public void add(Object obj) {
            list[index++]=obj;
            size++;
        }
    
        @Override
        public Iterator iterator() {
            
            return new ConcreteIterator(this);
        }
        @Override
        public Object get(int index) {
            
            return list[index];
        }
        @Override
        public int getSize() {
            
            return size;
        }
      
        /**
        * 程序入口, 测试      
        */
        public static void main(String[] args){
            List list=new ConcreteAggregate();
                    list.add("a");
                    list.add("b");
                    list.add("c");
                    list.add("d");
                    Iterator it=list.iterator();
                    while(it.hasNext()){
                        System.out.print(it.next());
                    }
        }
    }
  
    /*Output:
    abcd
    */
    ```
    
+ 迭代器模式的优点:
    + 简化了遍历方式, 对于对象集合的遍历, 还是比较麻烦的, 对于数组或者有序列表, 我们尚可以通过游标来取得, 但用户需要在对集合了解很清楚的前提下, 自行遍历对象, 但是对于hash表来说, 用户遍历起来就比较麻烦了. 而引入了迭代器方法后, 用户用起来就简单的多了. 
    + 可以提供多种遍历方式, 比如说对有序列表, 我们可以根据需要提供正序遍历, 倒序遍历两种迭代器, 用户用起来只需要得到我们实现好的迭代器, 就可以方便的对集合进行遍历了. 
    + 封装性良好, 用户只需要得到迭代器就可以遍历, 而对于遍历算法则不用去关心. 
+ 迭代器模式的缺点: 
    + 对于比较简单的遍历(像数组或者有序列表), 使用迭代器方式遍历较为繁琐. 
   
####4. _[.this 和 .new]()_ :bangbang:
+ **OuterClassName.this**: 当需要在内部类中传递或者使用外围类的对象引用时, 可以通过这种方法获得实例对象的引用. 
+ [**OuterClassObjectName.new**: 创建内部类的实例.]()
    ```java
    public class DotNew{
      //空的内部类
      public class Inner{}
      public static void main(String[] args){
          DotNew dn = new DotNew();
          // 用法正确, 内部类必须持有一个外围类的对象引用, 除静态内部类以外
          DotNew.Inner inner = dn.new Inner();
          // 错误用法
          DotNew.Inner inner1 = dn.new DotNew.Inner();
      }
    }
    ```

####5. _内部类的向上转型_
+ 示例: 
    ```java
    // 公共接口, 这里省略的包
    public interface Destination{
      String readLabel();
    }
    
    // 公共接口, 省略包
    public interface Contents{
      int value();
    }
  
    class Parcel4{
      
      private class PContents implements Contents{
          private int i = 11;
          public int value(){return i;}
      }
    
      protected class PDestination implements Destination{
          private String label;
          private PDestination(String whereTo){
              label = whereTo;  
          }
          public String readLabel(){return label;}
      }
    
      public Destination destination(String s){
          return new PDestination(s);  
      }
    
      public Contents contents(){
          return new PContents();  
      }
    }   
  
    public class Test{
      /**
      * 程序测试入口
      */
      public static void main(String[] args){
          Parcel4 p = new Parcel4();
          //完全隐藏实现, 连内部类的名称都不知道
          Contents c = p.contents();
          Destination d = p.destination();
          // 错误用法, 无法获取
          Parcel4.PContents pc = p.new PContents();
      }
    }
    ```

####6. _在方法或域中定义内部类_
+ 内部类支持在方法域或其他任意域中定义, 之所以实现这种复杂语法有两点原因
    + 返回某类型接口的实现类, 并隐藏其具体实现. 
    + 隐藏某些不想被使用的类. 
+ 1. 定义在方法体中: 
    + 也称为局部内部类(local inner class). 
    + [这种定义方法中内部实现类的可获取性依据其权限修饰符, 而并不是说在方法域以外不可获取](). 
    ```java
    public class Parcel5{
    
      public Destination destination(String s){
        
          /**
          * 内部类实现Destination接口
          * 权限修饰符: friendly, 表示包内其他类可以获取. 
          */
          class ModifiedDestination implements Destination{  
              private Sting label;
              private ModifiedDestination(String whereTo){
                  label = whereTo;  
              }
          @Override
          public String readLabel(){return label;}
          }
          return new ModifiedDestination(s);
      }
    
      public static void main(String[] args){
          Parcel5 p = new Parcel5();
          Destination destination = p.destination("Nan Jing");
      }
    }
    ```

+ 2. 在任意域中定义: 
    + [不同于在方法中定义, 在其他任意域中定义的内部类只能在域内获取, 域外无法直接使用.]()
    + 如下图, 定义在if作用域中的内部类, 但是需要注意的是: [域内定义的类, 虽然无法在域外获取, 但并不是意味着程序只有执行到该代码域时才会创建类, 类在编译时其实已经创建完毕.]() :bangbang:
    ```java
    public class Parcel6{
    
      private void internalTracking(boolean b){
          //定义在if的作用域内
          if(b){  
              class TrackingSlip{
                  private String id;
                  TrackingSlip(String s){id = s;}
                  String getSlip(){return id;}
              }
              TrackingSlip ts = new TrackingSlip("slip");
              String s = ts.getSlip();
          }
          //这里无法直接使用TrackingSlip类
          //如下直接会提示错误, 如果用IDE的自动补全是不会不全的
          //TrackingSlip ts = new TrackingSlip("x");
      }
    }
    ```

####7. [_匿名内部类_]():heavy_exclamation_mark:
+ 匿名内部类的简化形式，一般是指在返回时内部类对象时直接插入类的定义。
+ [如下面实例在匿名内部类中使用类定义以外的对象时，这里需要用final修饰形参引用，这是语法要求！！]() 
+ [如下面示例，+匿名内部类，因为没有类名，所以不能有显式的构造方法。如果显式写构造方法，编译器会提示为方法定义返回值类型，这显然说明不能显式写构造方法]()
    ```java
    public class Parcel9 {
        /**
         * Destination destination.
         *
         * @param dest the dest
         *             注意在匿名内部类中使用类定义以外的对象时，这里需要用final修饰形参引用，这是语法要求！！
         * @return the destination 返回一个匿名内部类，因为没有类名，所以不能有显式的构造方法。
         */
        public Destination destination(final String dest){
            return new Destination() {
                private String label = dest;
                //Destination(){} 如果这么写，IDE会提示没有方法的返回值类型定义，这说明IDE将其识别为方法名而不是构造方法定义！！。
                @Override
                public String readLabel() {
                    return label;
                }
            }; // 注意这里使用分号结束，因为这是一个表达式。
        }
    }
    ```
+ 