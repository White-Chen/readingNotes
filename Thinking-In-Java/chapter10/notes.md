###Chapter 10 : Inner Classes
####1. _内部类/inner class_
+ 定义: 将一个类的定义放置于另一个类的定义内部. 
+ 使用:
    + 通过 **OuterClassName.InnerClassName** 进行获取. 
    + 通过在 **OuterClassName** 内定义一个返回 **InnerClassName** 引用的可访问方法(更常用). 
+ 使用内部类的原因：
    + 因为内部类持有外围类对象的引用，所以内部类提供了一个访问外围类成员的窗口。
    + 内部类因为可以独立实现接口，且与外围类实现的接口不冲突，所以可以提供一种[基于内部类的多继承实现方案. 对于多接口，**implements** 关键字为我们直接提供了多继承方案，但是对于当我们需要继承多个类时，通过内部类可以实现.]()
+ 内部类有以下几个不同的特性
    + 1. 内部类可以有多个实例，且拥有独立于外围类的状态信息。
    + 2. 在一个外围类内可以以不同实现方式多次实现同一个接口。
    + 3. 内部类的创建时间不依赖于外围类的创建时间。(但是内部类的创建依赖外围类的创建，因为必须要有外围类的实例引用)
    + 4. 内部类是独立实体，因此与外围类没有迷惑性的 **is-a** 关系。
    
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
+ 匿名内部类的简化形式, 一般是指在返回时内部类对象时直接插入类的定义. 
+ [如下面示例, 在匿名内部类中使用类定义以外的对象时, 这里需要用final修饰形参引用, 这是语法要求. 但是需要注意的是, SE8以后可以不用写final了!!]() 
+ [如下面示例, 匿名内部类, 因为没有类名, 所以不能有显式的构造方法. 如果显式写构造方法, 编译器会提示为方法定义返回值类型, 这显然说明不能显式写构造方法]()
    ```java
    public class Parcel9 {
        /**
         * Destination destination.
         *
         * @param dest the dest
         *             注意在匿名内部类中使用类定义以外的对象时, 这里需要用final修饰形参引用, 这是语法要求!!
         * @return the destination 返回一个匿名内部类, 因为没有类名, 所以不能有显式的构造方法. 
         */
        public Destination destination(final String dest){
            
            return new Destination() {
                private String label = dest;
                private int a = x;
                //Destination(){} 如果这么写, IDE会提示没有方法的返回值类型定义, 这说明IDE将其识别为方法名而不是构造方法定义!!. 
                @Override
                public String readLabel() {
                    return label;
                }
            }; // 注意这里使用分号结束, 因为这是一个表达式. 
        }
    }
    ```
    
+ 如下面示例, 匿名内部类是否可以有参构造, 这取决于其接口类是否定义了有参构造方法. 
+ [如下面示例, 当参数引用仅传递给构造方法时, 则不需要使用final进行修饰, 这与上面的final语法要求并不矛盾.]()
    ```java
    public class Parcel10 {
    
        /**
         * Wrapping wrapping.
         *
         * @param x the x               注意这里没有使用final, 这是因为x被传递给构造方法使用, 而不是其他方法
         * @return the wrapping         有参构造方法, 这取决于其实现的类是否定义了该构造方法. 
         */
        public Wrapping wrapping(int x){
            return new Wrapping(x){
                public int value(){
                    return super.value() * 47; // super等关键字可以正常使用
                }
            };
        }
    
        public static void main(String[] args) {
            Parcel10 p10 = new Parcel10();
            Wrapping wrapping = p10.wrapping(10);
        }
    }
    
    /**
     * The type Wrapping.
     */
    class Wrapping{
        private int i;
        public Wrapping(int x){i = x;}
        public int value(){return i;}
    }
    ```
    
+ [对于匿名内部类, 因为不能显式定义构造方法所以无法实现在构造方法中进行成员变量初始化, 但是可以通过实例初始化方法进行成员变量初始化, 如下面示例:]()
    ```java
    public class Parcel11 {
        /**
         * Get base base.
         *
         * @param i the         传递给构造方法的对象引用, 不需要加final
         * @return the base     返回匿名内部类
         */
        public static Base getBase(int i){
            return new Base(i) {
                // 实例初始化, 调用优于构造器
                {
                    System.out.println("Inside instance initializer");
                }
                @Override
                public void f() {
                    System.out.println("In anonymous f()");
                }
            };
        }
    
        /**
         * The entry point of application.
         * 测试
         * @param args the input arguments
         */
        public static void main(String[] args) {
            Base base = Parcel11.getBase(47);
            base.f();
        }
    }
    /**
     * The type Base. 接口类
     */
    abstract class Base{
        /**
         * Instantiates a new Base.
         * 有参构造方法
         * @param i the
         */
        public Base(int i ){
            System.out.println("Base constructor, i = " + i);
        }
    
        /**
         * F.
         */
        public abstract void f();
    }
  
    /* Output: 
    Base constructor, i = 47
    Inside instance initializer
    In anonymous f()
    */
    ```
+ 实例初始化方法除上述用于匿名内部类的成员变量初始化意外, 可用于在多个构造方法中需要重复书写的初始化操作, 很适合用于日志记录等或者初始化验证等功能. 

####8. [_设计模式: 基于匿名内部类的工厂方法模式_]()
+ 上一章最后有介绍工厂方法模式, 但是不是基于匿名内部类的, 这里介绍一下基于匿名内部类的工厂方法模式, 当然基于匿名内部类的更好. 
+ 示例: 
    ```java
    /**
     * \* Created with Chen Zhe on 1/3/2017.
     * \* Description:
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    public class Factories_2 {
    
        public static void serviceConsumer(ServiceFactory factory){
            Service service = factory.getService();
            service.method1();
            service.method2();
        }
    
        public static void main(String[] args) {
            serviceConsumer(ServiceImplementation_1.factory);
            serviceConsumer(ServiceImplementation_2.factory);
        }
    }
    
    /**
     * The type Service impl 2.
     * 具体产品角色
     */
    class ServiceImplementation_1 implements Service{
    
        // 隐藏构造方法
        private ServiceImplementation_1(){}
    
        @Override
        public void method1() {
            System.out.println("ServiceImplementaion_1 method1");
        }
    
        @Override
        public void method2() {
            System.out.println("ServiceImplementaion_2 method1");
        }
    
        /**
         * The type Service factory impl 1.
         * 具体工厂角色1
         */
        public static ServiceFactory factory =
                new ServiceFactory() {
                    @Override
                    public Service getService() {
                        return new ServiceImplementation_1();
                    }
                };
    }
    
    /**
     * The type Service impl 2.
     * 具体产品角色
     */
    class ServiceImplementation_2 implements Service{
    
        // 隐藏构造方法
        private ServiceImplementation_2(){}
    
        @Override
        public void method1() {
            System.out.println("ServiceImplementaion_2 method1");
        }
    
        @Override
        public void method2() {
            System.out.println("ServiceImplementaion_2 method2");
        }
    
        /**
         * The type Service factory impl 1.
         * 具体工厂角色1
         */
        public static ServiceFactory factory =
                new ServiceFactory() {
                    @Override
                    public Service getService() {
                        return new ServiceImplementation_2();
                    }
                };
    }
  
    /* Output:
    ServiceImplementaion_1 method1
    ServiceImplementaion_2 method1
    ServiceImplementaion_2 method1
    ServiceImplementaion_2 method2
    */
    ```

+ [使用匿名内部类实现工厂方法模式的优点: ]() :bangbang:
    + 产品的构造方法可以通过 **private** 隐藏. 
    + 不需要额外创建与产品相对应的有名称的工厂类. 
    + 因为工厂类本身往往只需要一个实例即可, 这里因为可以使用 **static** 从而减少了不必要的对象创建, 提高了程序的效率. 
    
####8. _嵌套类_
+ 内部类声明为 **static**, 通常称为嵌套类, 它与普通内部类不同之处在于不需要持有外围类的对象引用, 这意味着:
    + 要创建嵌套类的对象, 并不需要外围类的对象.
    + 不能从嵌套类的对象中访问非静态的外围类对象.
    + 嵌套类可以拥有静态成员, 而普通内部类不可以. 
+ 接口内部类: 因为接口中成员默认是 **public static** 修饰, 因此在接口内定义嵌套类并不与接口的语法规则矛盾, [事实上接口中定义的嵌套类只是和接口共用同一个命名空间.]() 这种方法可以用于在接口中定义一些所有实现类都共用的代码. 
+ 在JAVA中通过以下操作可以直接运行嵌套类.
    ```
    java 外围类名称$嵌套类名称
    ```
+ 内部类无论嵌套多少层, 都可以访问它嵌入的外围类所有成员(注意这里说的是内部类, 不是嵌套类).

####9. [_闭包和回调/Closures & Callbacks_]()  :bangbang:
+ 闭包是一个可调用的对象，他记录了一些信息，这些信息来自于创建它的作用域。这一概念与内部类相似，因此内部类可以看做是面向对象的闭包。
+ 回调：通过回调，对象能够携带一些信息，这些信息允许它在稍后的某个时刻调用初始的对象。Java同样可以通过内部类实现回调，并且这样相比指针回调更加安全。
+ [个人理解：把接口对应的实现类的一个实例当成一个参数传递给一个函数调用，那个函数处理过程中会调用你的这个接口中的方法。就比实现了Callable接口的线程，会返回一个Future对象，这个应该是一个实现的内部类，它在程序员与线程之间架设了一道桥梁，程序员可以有限安全地使用一些线程对象中的方法。]()

####10. [_控制框架 & 设计模式：模板方法_]() :bangbang:
+ 

####11. _继承内部类_
+ [因为内部类持续持有外围类的对象引用，而如果继承内部类则显然无法明确继承类与外围类对象之间的关系，所以需要通过必须通过如下语法实现，否则无法编译通过,]()
    > enclosingClassReference.super();

+ 示例如下：
    ```java
    public class WithInner {
        class Inner{
            Inner(String outString){
                System.out.println(outString);
            }
        }
    }
  
    public class InheritInner extends WithInner.Inner{
        //InheritInner(){} IDE会提示没有默认的构造方法
        //如下可以实现，但是感觉不是很明白这种语法。为什么with的super会指向它的内部类？
        InheritInner(WithInner with, String outStr){
            with.super(outStr);
        }
    
        public static void main(String[] args) {
            WithInner wi = new WithInner();
            InheritInner ii = new InheritInner(wi,"take easy");
        }
    }
    
    /* Output:
    take easy
    */
    ```

####12. _覆盖内部类_
+ 因为内部类与外围类是在同一个命名空间下的完全分离的两个个体，而不是方法，自然不支持在子类中覆盖的功能，但是可以通过在子类中实现一个继承自父类内部类的新内部类来实现类似的覆盖效果。

####13. _局部内部类 vs. 匿名内部类_
+ 大多数情况下[局部内部类(上面有介绍，在方法中定义)]()与[匿名内部类]()可以实现相同的功能。
+ 区别：局部内部类可以定义构造方法，内部类只能实例初始化。
+ 以下两种情况用局部内部类：
    + 重复创建内部类的多个实例。
    + 需要自定义构造方法或重载构造方法。
    
####14. _内部类表示符$_
+ 内部类在编译时会独立编译为.class文件，其名称符合以下规则
    + 匿名内部类：
    > OuterCLassName$number.class
    
    + 普通内部类：
    > OuterClassName$InnerClassName.class
    

