###Chapter 5 : Initialization & Cleanup :heavy_exclamation_mark:
####1. _构造器_
+ 代码规范：构造器与类名同名，方法名小写。
+ 构造方法也是一种无返回值的特殊方法。
+ 如果没有显式定义构造方法，那么编译器会在编译时默认加入无参的构造方法，以防止发生编译错误。
+ 如果显式定义定义构造方法，那么编译器则认为定义人明确知道自己在做什么，所以并不会加入默认的无参构造方法，此时需要主要如果想要通过无参构造方法新建实例，必须手动显式在类中定义无参构造方法。

####2. _方法重载_
+ 不同参数列表的构造方法的实现原理也是方法重载。
+ 重载方法的唯一判别条件是：参数列表不同，这里的不同不是指形参名称不同，而是参数类型不同或者参数列表长度不同。
+ 返回值的不同并能作为重载方法的判别条件，原因很简单，对于无返回值的或者仅仅只调用不赋值的方法调用操作，相同参数列表不同返回值的方法在JAVA中无法得到有效判别。
+ 包含基础类型的方法重载，如果找不到完全对应的方法，如果出入参数类型小于方法的参数类型，那么会自动进行格式转换，如果参数类型大于方法的参数类型，则需要显式强转。

####3. _this关键字_
+ this指代当前实例对象，因此只能在非**static**方法中使用。
+ this一般用途：
    + 当形参名称与实例对象的成员参数同名时，用this进行区别，见下面的注释 **1** 。
    + 在return中返回当前对象，这个很容用于实现函数式编程风格，减少不必要的冗余代码，且代码更易理解，见下面的注释 **2**。
    + 在其他类方法中传递当前对象，见下面注释 **3**。
    + 构造器中调用其他构造器，注意方法必须要显式的在第一行调用，允许输入参数，且只能调用一次, 见下面注释 **4**。:heavy_exclamation_mark:
    ```java
    class Person{
      private String name;
      private int age;
      private double weight;
      private double height;
      
      Person(){}
      Person(String name){
          this.name = name; // 1
      }
      Person(int age){
          this.age = age; // 1
      }
    
      Person(String name, int age){
          this(name); // 4
          //this(age); error, because can be only called one time, and must be called at the first line
          this.age = age; // 1
      }
    
    
      Person setString(String name){
          this.name = name; // 1 
          return this;  //2
      }
    
      Person setAge(int age){
          this.age = age; // 1
          return this; //2
      }
    
      Person setWeight(double weight){
          this.weight = weight; //1
          return this; //2
      }
      
      Person setHeight(double height){
          this.height = height; //1
          return this; //2
      }
    
      void eat(Apple apple){
          Apple peeled = apple.getPeeled();  
      }
    }
    
    class Peeler{
      static Apple peel(Apple apple){return apple;}
    }
    class Apple{
      Apple getPeeled(){
          return Peeler.peel(this); // 3  
      }
    }
  
    public class Test{
      public static void main(String[] args){
          // 2 functional programming style
          Person person = new Person().setString("ChenZhe")
                                      .setAge(26)
                                      .setHeight(170)
                                      .setWeight(120);
            
      }   
    }

    ```
    
+ 当一个实例调用其实例对象时，JAVA会隐式地传入当前实例的指针，效果如下所示当然这个内置的。:heavy_exclamation_mark:
    ```java
    class Person{
      void run(int meters){}
    }
  
    public class Test{
      public static void main(String[] args){
      //
          Person p1 = new Person(),
                 p2 = new Person();
          p1.run(1); // p1.run(1) => p1.run(p1,1);
          p2.run(2); // p2.run(2) => p2.run(p2,2);
      }
    }
    ```  

####4. _static关键字_
+ 项目中不应该大量存在 **static** 方法，因为不符合OOP的设计理念，因为 **static** 属于类而不属于对象，有些类似别的方法中的全局方法。

####5. _[finalization 对象释放]()_ :heavy_exclamation_mark:
+ GC并不是析构函数，因此只能释放对象占用的内存空间，而当对象被标记时将被释放前，你无法定义一些被内存被释放前必须执行的行为。
+ 所有对象都从 **Object** 类中继承了 _**finalize()**_ 方法，当对象被垃圾回收机释放时，会被调用，因此可用于自定义对象释放时的一些行为。
+ GC对对象的释放并不受人为控制，所以 _**finalize()**_ 并不能保证被执行，所以指望通过GC或者通过 _**finalize()**_ 人为控制对象的内存释放时不可能也不推荐的。
+ _**finalize()**_ 正确的用途是用于确认终止状态，虽然不能认为控制 _**finalize()**_ 被调用的时间，但是可以保证 _**finalize()**_ 被调用那么对象就意味着将被释放，这就意味对象进入终止状态，此时好的变成习惯是进行一些状态确认，确保对象被释放前进行了正确的操作，如果操作不正确应该通过日志等方式进行记录以便后续粉分析。:bangbang:

####6. _[GC 垃圾回收器 ]()_ :heavy_exclamation_mark:
+ GC主要是管理堆内存，栈内存是由系统进行维护的。
+ _**System.gc()**_ 可以被用于显式调用垃圾回收器。
+ JAVA中的堆内存的分配速度很快，不同于C++等语言，在申请内存时需要确认一块安全的被释放内存，而JAVA申请堆空间的操作更像栈操作，仅仅将指针后移就形成了新的空间，而其他什么时候都不用做，因此效率媲美C++的栈分配。
+ 但是当内存被用完时，垃圾回收GC就成了问题。
+ GC的发展经历以下几个阶段(至少书中是这样描述的)
    + _**reference counting:**_ 堆中每个对象，被引用一次其计数器+1，每次引用释放就-1。缺点：对象之间循环引用，此时大量实际需要被回收的内容仍然无法被回收。JVM中没有实现过这一策略。
    + _**trace:**_ 对象存活以否不在依赖于被引用次数，而是通过从栈和静态区进行递归式的trace, 没有被trace到的对象显然是需要被回收的，被trace到的就是需要被保存的。 
    + _**stop-and-copy:**_ 顾名思义，当内存不够用或者因为别的原因GC触发了之后，需要停止当前程序，注意这里不是在后台运行，而是停止当前程序运行!!! 基于trace方法，将存活对象复制到新的区域，然后重新定义栈和静态区的引用，这里可以理解成有一张表，其中定义了每个对象新旧引用之间的映射关系。这个方法有两个问题：1是需要控制两倍的空间，解决这个问题就是进行大块内存(chunk)操作; 2是当程序绝大多数对象都是不需要被回收时，此时反复复制操作并不能有效释放内存。
    + _**mark-and-sweep:**_ 类似trace，但是他在标记过程中是不回收内存的，只有当sweep被触发时，才会一次回收所有被mark的对象。这个方法的问题是内存空间不连续，因此压缩内存空间时更消耗时间。
    + _**generation count:**_ 综合上面两个优点，一个内存空间连续但是需要维护多个空间，一个维护空间少，但是内存不连续。方法是，将内存分块，每一个快进行代数计数，当一块内存被引用时则+1，对于大块内存则尽量不进行释放操作仅仅+1，而是对小块空间进行释放，当内存占用到达一定程度进行_**stop-and-copy**_。

####7. _enum 枚举类_
+ 

