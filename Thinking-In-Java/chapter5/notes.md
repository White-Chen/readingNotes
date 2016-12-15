###Chapter 5 : Initialization & Cleanup
####1. _构造器_
+ 代码规范: 构造器与类名同名, 方法名小写. 
+ 构造方法也是一种无返回值的特殊方法. 
+ 如果没有显式定义构造方法, 那么编译器会在编译时默认加入无参的构造方法, 以防止发生编译错误. 
+ 如果显式定义定义构造方法, 那么编译器则认为定义人明确知道自己在做什么, 所以并不会加入默认的无参构造方法, 此时需要主要如果想要通过无参构造方法新建实例, 必须手动显式在类中定义无参构造方法. 

####2. _方法重载_
+ 不同参数列表的构造方法的实现原理也是方法重载. 
+ 重载方法的唯一判别条件是: 参数列表不同, 这里的不同不是指形参名称不同, 而是参数类型不同或者参数列表长度不同. 
+ 返回值的不同并能作为重载方法的判别条件, 原因很简单, 对于无返回值的或者仅仅只调用不赋值的方法调用操作, 相同参数列表不同返回值的方法在JAVA中无法得到有效判别. 
+ 包含基础类型的方法重载, 如果找不到完全对应的方法, 如果出入参数类型小于方法的参数类型, 那么会自动进行格式转换, 如果参数类型大于方法的参数类型, 则需要显式强转. 

####3. _this关键字_
+ this指代当前实例对象, 因此只能在非**static**方法中使用. 
+ this一般用途: 
    + 当形参名称与实例对象的成员参数同名时, 用this进行区别, 见下面的注释 **1** . 
    + 在return中返回当前对象, 这个很容用于实现函数式编程风格, 减少不必要的冗余代码, 且代码更易理解, 见下面的注释 **2**. 
    + 在其他类方法中传递当前对象, 见下面注释 **3**. 
    + 构造器中调用其他构造器, 注意方法必须要显式的在第一行调用, 允许输入参数, 且只能调用一次, 见下面注释 **4**. :heavy_exclamation_mark:
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
    
+ 当一个实例调用其实例对象时, JAVA会隐式地传入当前实例的指针, 效果如下所示当然这个内置的. :heavy_exclamation_mark:
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
+ 项目中不应该大量存在 **static** 方法, 因为不符合OOP的设计理念, 因为 **static** 属于类而不属于对象, 有些类似别的方法中的全局方法. 

####5. _[finalization 对象释放]()_ :heavy_exclamation_mark:
+ _**garbage collector (GC)**_ 并不是析构函数, 因此只能释放对象占用的内存空间, 而当对象被标记时将被释放前, 你无法定义一些被内存被释放前必须执行的行为. 
+ 所有对象都从 **Object** 类中继承了 _**finalize()**_ 方法, 当对象被垃圾回收机释放时, 会被调用, 因此可用于自定义对象释放时的一些行为. 
+ _**garbage collector (GC)**_ 对对象的释放并不受人为控制, 所以 _**finalize()**_ 并不能保证被执行, 所以指望通过_**garbage collector (GC)**_ 或者通过 _**finalize()**_ 人为控制对象的内存释放时不可能也不推荐的. 
+ _**finalize()**_ 正确的用途是用于确认终止状态, 虽然不能人为控制 _**finalize()**_ 被调用的时间, 但是可以保证 _**finalize()**_ 被调用那么对象就意味着将被释放, 这就意味对象进入终止状态, 此时好的变成习惯是进行一些状态确认, 确保对象被释放前进行了正确的操作, 如果操作不正确应该通过日志等方式进行记录以便后续粉分析. :bangbang:

####6. _[garbage collector (GC) 垃圾回收器 ]()_
+ _**garbage collector (GC)**_ 主要是管理堆内存, 栈内存是由系统进行维护的. 
+ _**System.gc()**_ 可以被用于显式调用垃圾回收器. 
+ JAVA中的堆内存的分配速度很快, 不同于C++等语言, 在申请内存时需要确认一块安全的被释放内存, 而JAVA申请堆空间的操作更像栈操作, 仅仅将指针后移就形成了新的空间, 而其他什么时候都不用做, 因此效率媲美C++的栈分配. 
+ 但是当内存被用完时, 垃圾回收_**garbage collector (GC)**_ 就成了问题. 
+ _**garbage collector (GC)**_ 算法发展经历以下几个阶段(至少书中是这样描述的) :heavy_exclamation_mark:
    + _**reference counting:**_ 堆中每个对象, 被引用一次其计数器+1, 每次引用释放就-1. 缺点: 对象之间循环引用, 此时大量实际需要被回收的内容仍然无法被回收. JVM中没有实现过这一策略. 
    + _**reachability analysis:**_ 对象存活以否不在依赖于被引用次数, 而是通过从栈和静态区进行递归式的trace, 没有被trace到的对象显然是需要被回收的, 被trace到的就是需要被保存的. 
    + _**stop-and-copy:**_ 顾名思义, 当内存不够用或者因为别的原因_**garbage collector (GC)**_ 触发了之后, 需要停止当前程序, 注意这里不是在后台运行, 而是停止当前程序运行!!! 基于 _**reachability analysis**_ 方法, 将存活对象复制到新的区域, 然后重新定义栈和静态区的引用, 这里可以理解成有一张表, 其中定义了每个对象新旧引用之间的映射关系. 这个方法有两个问题: 1是需要控制两倍的空间, 解决这个问题就是进行大块内存(chunk)操作; 2是当程序绝大多数对象都是不需要被回收时, 此时反复复制操作并不能有效释放内存. 
    + _**mark-and-sweep:**_ 类似 _**reachability analysis:**_, 如它的名字一样, 算法分为“标记”和“清除”两个阶段: 首先标记出所有需要回收的对象, 在标记完成后统一回收掉所有被标记的对象. 之所以说它是最基础的收集算法, 是因为后续的收集算法都是基于这种思路并对其缺点进行改进而得到的. 它的主要**缺点**有两个: 一个是效率问题, 标记和清除过程的效率都不高; 另外一个是空间问题, 标记清除之后会产生大量不连续的内存碎片, 空间碎片太多可能会导致, 当程序在以后的运行过程中需要分配较大对象时无法找到足够的连续内存而不得不提前触发另一次垃圾收集动作. 
    + _**generation count:**_ JVM目前使用的回收机制, 把Java堆分为新生代和老年代, 这样就可以根据各个年代的特点采用最适当的收集算法. 在新生代中, 每次垃圾收集时都发现有大批对象死去, 只有少量存活, 那就选用 _**stop-and-copy**_ 算法, 只需要付出少量存活对象的复制成本就可以完成收集. 而老年代中因为对象存活率高、没有额外空间对它进行分配担保, 就必须使用 _**mark-and-sweep**_ 或 _**mark-and-compact:**_ 算法来进行回收. 
+ 补充: :bangbang:
    + _**mark-and-compact:**_ 复制收集算法在对象存活率较高时就要执行较多的复制操作, 效率将会变低. 更关键的是, 如果不想浪费50%的空间, 就需要有额外的空间进行分配担保, 以应对被使用的内存中所有对象都100%存活的极端情况, 所以在老年代一般不能直接选用这种算法. 根据老年代的特点, 有人提出了另外一种“标记-整理”（Mark-Compact）算法, 标记过程仍然与“标记-清除”算法一样, 但后续步骤不是直接对可回收对象进行清理, 而是让所有存活的对象都向一端移动, 然后直接清理掉端边界以外的内存. 
    + _**Serial GC:**_ 串行收集器是最古老, 最稳定以及效率高的收集器, 可能会产生较长的停顿, 只使用一个线程去回收. 新生代、老年代使用串行回收; 新生代复制算法、老年代标记-压缩; 垃圾收集的过程中会Stop The World (服务暂停). 参数控制: 
        + -XX:+UseSerialGC
    + _**ParNew GC:**_ ParNew收集器其实就是Serial收集器的多线程版本. 新生代并行, 老年代串行; 新生代复制算法、老年代标记-压缩. 参数控制: 
        + -XX:+UseParNewGC
        + -XX:ParallelGCThreads 限制线程数量
    + _**Parallel GC:**_ Parallel Scavenge收集器类似ParNew收集器, Parallel收集器更关注系统的吞吐量. 可以通过参数来打开自适应调节策略, 虚拟机会根据当前系统的运行情况收集性能监控信息, 动态调整这些参数以提供最合适的停顿时间或最大的吞吐量; 也可以通过参数控制GC的时间不大于多少毫秒或者比例; 新生代复制算法、老年代标记-压缩. 参数控制: 
        + -XX:+UseParallelGC
    + _**Parallel Old GC:**_ Parallel Old是Parallel Scavenge收集器的老年代版本, 使用多线程和“标记－整理”算法. 这个收集器是在SE6中才开始提供. 参数控制: 
        + -XX:+UseParallelOldGC
    + _**CMS GC:**_ CMS（Concurrent Mark Sweep）收集器是一种以获取最短回收停顿时间为目标的收集器. 目前很大一部分的Java应用都集中在互联网站或B/S系统的服务端上, 这类应用尤其重视服务的响应速度, 希望系统停顿时间最短, 以给用户带来较好的体验. 从名字（包含“Mark Sweep”）上就可以看出CMS收集器是基于“标记-清除”算法实现的, 它的运作过程相对于前面几种收集器来说要更复杂一些, 整个过程分为4个步骤, 包括: 
        + **初始标记** (CMS initial mark)
        + **并发标记** (CMS concurrent mark)
        + **重新标记** (CMS remark)
        + **并发清除** (CMS concurrent sweep)
        + 其中初始标记、重新标记这两个步骤仍然需要“Stop The World”. 初始标记仅仅只是标记一下GC Roots能直接关联到的对象, 速度很快, 并发标记阶段就是进行GC Roots Tracing的过程, 而重新标记阶段则是为了修正并发标记期间, 因用户程序继续运作而导致标记产生变动的那一部分对象的标记记录, 这个阶段的停顿时间一般会比初始标记阶段稍长一些, 但远比并发标记的时间短. 由于整个过程中耗时最长的并发标记和并发清除过程中, 收集器线程都可以与用户线程一起工作, 所以总体上来说, CMS收集器的内存回收过程是与用户线程一起并发地执行. 老年代收集器（新生代使用ParNew）. 优点:并发收集、低停顿. 缺点: 产生大量空间碎片、并发阶段会降低吞吐量. 参数控制: 
        + -XX:+UseConcMarkSweepGC  使用CMS收集器
        + -XX:+UseCMSCompactAtFullCollection Full GC后, 进行一次碎片整理; 整理过程是独占的, 会引起停顿时间变长
        + -XX:+CMSFullGCsBeforeCompaction  设置进行几次Full GC后, 进行一次碎片整理
        + -XX:ParallelCMSThreads  设定CMS的线程数量(一般情况约等于可用CPU数量);
    + _**G1 GC:**_ SE7中引入, 使用G1收集器时, Java堆的内存布局与其他收集器有很大差别, 它将整个Java堆划分为多个大小相等的独立区域（Region）, 虽然还保留有新生代和老年代的概念, 但新生代和老年代不再是物理隔阂了, 它们都是一部分（可以不连续）Region的集合. 与CMS收集器相比G1收集器有以下特点: 
        + **空间整合**, G1收集器采用标记整理算法, 不会产生内存空间碎片. 分配大对象时不会因为无法找到连续空间而提前触发下一次GC. 
        + **可预测停顿**, 这是G1的另一大优势, 降低停顿时间是G1和CMS的共同关注点, 但G1除了追求低停顿外, 还能建立可预测的停顿时间模型, 能让使用者明确指定在一个长度为N毫秒的时间片段内, 消耗在垃圾收集上的时间不得超过N毫秒, 这几乎已经是实时Java（RTSJ）的垃圾收集器的特征了. 
        + 收集步骤如下: 
        + **标记阶段**, 首先初始标记, 这个阶段是停顿的, 并且会触发一次普通Mintor GC. 对应GC log:GC pause (young) (inital-mark). 
        + **Root Region Scanning**, 程序运行过程中会回收survivor区(存活到老年代), 这一过程必须在young GC之前完成. 
        + **Concurrent Marking**, 在整个堆中进行并发标记(和应用程序并发执行), 此过程可能被young GC中断. 在并发标记阶段, 若发现区域对象中的所有对象都是垃圾, 那个这个区域会被立即回收. 同时, 并发标记过程中, 会计算每个区域的对象活性(区域中存活对象的比例). 
        + **Remark*, 再标记, 会有短暂停顿(STW). 再标记阶段是用来收集 并发标记阶段 产生新的垃圾(并发阶段和应用程序一同运行); G1中采用了比CMS更快的初始快照算法:snapshot-at-the-beginning (SATB). 
        + **Copy/Clean up**, 多线程清除失活对象, 会有STW. G1将回收区域的存活对象拷贝到新区域, 清除Remember Sets, 并发清空回收区域并把它返回到空闲区域链表中. 

####7. _[just-in-time (JIT) Compiler / JIT 编译器]()_ :bangbang:
+ 即时编译技术.  在Java中提到“编译”, 自然很容易想到Javac编译器将*.java文件编译成为*.class文件的过程, 这里的Javac编译器称为前端编译器. 相对应的还有后端编译器, 它在程序运行期间将字节码转变成机器码(现在的Java程序在运行时基本都是解释执行加编译执行). 
+ Java程序最初是仅仅通过解释器解释执行的, 即对字节码逐条解释执行, 这种方式的执行速度相对会比较慢, 尤其当某个方法或代码块运行的特别频繁时, 这种方式的执行效率就显得很低. 于是后来在虚拟机中引入了JIT编译器（即时编译器）, 当虚拟机发现某个方法或代码块运行特别频繁时, 就会把这些代码认定为“Hot Spot Code”（热点代码）, 为了提高热点代码的执行效率, 在运行时, 虚拟机将会把这些代码编译成与本地平台相关的机器码, 并进行各层次的优化, 完成这项任务的正是JIT编译器. 
+ 代表性的JIT技术有Sun的HotSpot. [后续还需要继续了解, 书上没有具体说明]().
+ JAVA通过JIT加速程序运行, 从而减少由GC时间消耗带来的消极影响. 

####8. _对象初始化_
+ 类成员变量会默认初始化, 即时不进行赋值操作. 基础类型初始化为0, 其他引用类型初始化为null.
+ 类的静态成员和静态方法(静态方法可以想象成一个单独的类), 初始化只发生在第一次创建或者调用时, 并不是发生在程序开始时, 只发生一次. 
+ 方法中的临时变量并不会默认初始化, 需要显式初始化. 
+ 静态代码块是类加载进JVM时类实例化前进行, 以防类实例化时的加载影响性能; 非静态代码块是在类实例化时加载. 
+ 类实例化过程(书中描述): 
    + 当创建实例时, 或者调用类静态方法或者类静态成员变量时, java解释器在classpath中搜索YourClass.class (JIT技术？). 
    + 加载YourClass, 此时一个Class对象被创建(非实例对象). 此时进行静态初始化. 
    + 当创建实例时, 在堆中为类分配足够的实例空间. 
    + 实例空间被擦除, 所有非静态成员变量自动转为默认值. 
    + 执行非静态成员变量的赋值操作. (也就是非静态成员赋值了两次, 一次是默认值, 一次是自定义值). 
    + 构造方法被调用. 

####9. _数组初始化_
+ 三种方法: 
    ```java
    int[] a1 = new int[10];
    int[] a2 = {1,2,3,4,5,6};
    int[] a3 = new int[5]{1,2,3,4,5,6};
    ```

+ 必须要初始化长度. 

####10. _[可变参数]()_ :heavy_exclamation_mark:
+ 早期实现方式
    ```java
    void method (Object[] args){
      //....
    }
    ```

+ SE5之后实现方式, 用三点实现可变参数, 如果输入参数不是数组则会自动包装成数组, 否则则不包装. 
    ```java
    void method1(Object... args){

    }

    void method2(int a, Object... args){

    }
    ```

+ 可变参数列表不依赖自动包装机制, 也就是说可以用基本类型定义. 如果参数列表中由其他输入参数, 那么可变参数类型需要方法在参数列表最后一个. 
    ```java
    void method3(int... args){

    }
    ```

+ 可变参数列表可以实现方法的可选参数输入, 或者默认参数输入等效果. 
+ 需要注意是可变参数列表的时候容易引起方法重载问题, 有时会使得两个重载方法之间没有区别, 从而引起错误. 所以因该保证在一个方法的所有重载版本中, 只是用一次可变参数类型, 或者尽量不用. 


####8. _enum 枚举类_
+ SE5添加的新特性, 事实上就是一种类, 有自己的方法. 
+ 与 **switch** 搭配很好, 因为这样使得语义明确. 
+ 书写规范是, 内部成员用大写字母, 多个字母中间用'_'连接. 
+ 内置方法: 
    + _**toString():**_ 打印名称. 
    + _**ordinal():**_ 申明顺序. 
    + _**static values():**_ 返回对应int值, switch的判断机制也是讲enum转为对应的int值, 再进行判断, 总是在switch底层, 仍然只支持int类型的判断. 
+ [enum可以实现安全的单例模式, 好处是 **简单, 线程安全, 抗序列化, 防反射攻击**.]()  :bangbang:
    ```java
    public enum Singleton {
        INSTANCE {

            @Override
            protected void read() {
                System.out.println("read");
            }

            @Override
            protected void write() {
                System.out.println("write");
            }

        };
        protected abstract void read();
        protected abstract void write();
    }
    ```
+ 补充: 普通的单例模式, 即使通过二次检查也仍然不能防反射攻击, 且如果用 **valotile** 修饰, 还会有内存问题. 

