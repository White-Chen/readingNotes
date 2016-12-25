###Chapter 9 : Interface
####1. _抽象类和抽象方法/abstract_
+ 用 **abstract** 修饰的方法, 表示只需要声明方法返回类型, 方法名称以及方法的参数列表, 而不需要定义方法体. 
    ```
    abstract void f();
    ```

+ 用 **abstract**  修饰包含**abstract** 方法的类. 
    + **abstract** 类无法创建实例. 
    + **abstract** 类可以被继承, 其中 **abstract** 修饰的方法在子类中需要进行实现, 也就是定义方法体, 否则其子类仍然需要用 **abstract** 修饰. 
    + **abstract** 类在特殊情况下可以不包含任何 **abstract** 方法, 这种情况定义者往往只是希望利用抽象类无法实例化的特性. :heavy_exclamation_mark:
+ **abstract** 类也是类, 只是多了一点特殊性, 所以他的根父类还是 **Object**. 

####2. _接口/interface_
+ **interface** 是一种完整彻底的 **abstract** 类, **interface** 更像是一种架设在类与类之前的协议, 用于规范类之间的通信, 其中没有实现任何方法, 仅包含方法的名称, 返回值和参数列表. 
+ **interface** 中任何方法都是隐式声明为 **public abstract**, 即时显式声明也必须声明为 **public**, 因为接口中的抽象方法就是定义为实现类实现的, 如果用其他权限符修饰显然与设计初衷相违背. 
+ **interface** 可以包含成员变量, 但是默认隐式声明 **static final**. [这条特性使得在SE5之前, 程序员可以通过 **interface** 实现 **枚举类/enum** 的功能](). :heavy_exclamation_mark:
+ 成员变量必须显式初始化, 但是可以使用非常量方式初始化, 比如复制随机数. 其值存储于静态存储区内. 
+ **interface** 也是一种特殊类型, 这允许继承或实现他的子类进行向上转型, [但是他并没有根父类 **Object**](). :bangbang:
+ **interface** 的用途: 
    + 更彻底的抽象. 
    + 多接口可以实现多种向上转型, 提高灵活性. 
    + 不允许实例化. 
+ 子类实现 **interface** 时, 使用关键字 **implements**. 
+ 在SE5中 **Scanner** 类可以接受任何实现了 **Readable** 接口的类, 因此使用 **Scanner** 类有一种方法就是自定义类并实现 **Readable** . 但是当自定义类没有实现 **Readable** 接口时且不允许被修改时, 我们可以通过适配器模式进行适配. 因此方法接受接口类型是一种让任何类都可以对该方法进行适配的方式.
+ 有一个有意思的特点是, JAVA中的内置接口名称大多数都是以 **able** 结尾的, 其含义是说明每一个接口对应一种能力, 这种命名方法更加容易让你理解. 

####3. [_设计模式: 策略模式_]() :bangbang:
+ 策略模式属于对象的行为模式. 策略模式是对算法的包装, 是把使用算法的责任和算法本身分割开来, 委派给不同的对象管理. 策略模式通常把一个系列的算法包装到一系列的策略类里面, 作为一个抽象策略类的子类. 用一句话来说, 就是: “准备一组算法, 并将每一个算法封装起来, 使得它们可以互换”. 策略模式使得算法可以在不影响到客户端的情况下发生变化. 
+ 该模式包含三种角色: 
    + 环境(Context)角色: 持有一个Strategy的引用. 
    + 抽象策略(Strategy)角色: 这是一个抽象角色, 通常由一个接口或抽象类实现. 此角色给出所有的具体策略类所需的接口. 
    + 具体策略(ConcreteStrategy)角色: 包装了相关的算法或行为. 
+ 示例代码如下
    ```java
    /**
     * \* Created with Chen Zhe on 12/25/2016.
     * \* Description:
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     */
    
    /**
     * 抽象策略 Strategy.
     */
    interface Processor{
        /**
         * @return the string
         */
        String name();
    
        /**
         * Process object.
         *
         * @param input the input
         * @return the object
         */
        Object process(Object input);
    }
    
    /**
     * The type Upcase.
     * 具体策略角色 ConcreteStrategy
     */
    class Upcase implements Processor{
    
        public String name() {
            return "Using Upcase Processor";
        }
    
        public String process(Object input) {
            return ((String) input).toUpperCase();
        }
    }
    
    /**
     * The type Downcase.
     * 具体策略角色 ConcreteStrategy
     */
    class Downcase implements Processor{
    
        public String name() {
            return "Using Downcase Processor";
        }
    
        public String process(Object input) {
            return ((String)input).toLowerCase();
        }
    }
    
    /**
     * The type Spliter.
     * 具体策略角色 ConcreteStrategy
     */
    class Spliter implements Processor{
    
        public String name() {
            return "Using Spliter Processor";
        }
    
        public String process(Object input) {
            return Arrays.toString(((String) input).split(" "));
        }
    }
    
    /**
     * The type Apply.
     * 环境角色 Context
     */
    public class Apply {
        /**
         * The constant s.
         */
        public static String s = "This is an example of strategy design pattern.";
    
        /**
         * Process.
         *
         * @param p the p 持有策略引用
         * @param o the o 待处理对象
         */
        public static void process(Processor p, Object o){
            System.out.println(p.name());
            System.out.println(p.process((o)));
        }
    
        /**
         * The entry point of application.
         * 用于测试
         * @param args the input arguments
         */
        public static void main(String[] args) {
            process(new Upcase(),s);
            process(new Downcase(),s);
            process(new Spliter(),s);
        }
    }
  
    /* Output:
    Using Upcase Processor
    THIS IS AN EXAMPLE OF STRATEGY DESIGN PATTERN.
    Using Downcase Processor
    this is an example of strategy design pattern.
    Using Spliter Processor
    [This, is, an, example, of, strategy, design, pattern.]
    */
    ```

+ 策略模式的优点: 
    + 避免重复的 **if-else**. 
    + 提供了一种管理算法族的办法. 
+ 策略模式的缺点: 
    + 客户端也就是上面的测试类, 必须要清楚知道所有的策略类, 以及其对应的算法和行为, 因为策略的选择由客户端决定. (看上面的main函数)
    + 如果算法族规模很大的话, 需要维护的对象数目也会很可观. 
+ 可以通过对比第八章, 发现 **状态模式** 与 **策略模式** 很像, 其区别的在于: [**状态模式** 中状态由其内部决定, 仍然会存在 **if-else**. **策略模式** 中策略的选择则是由外部客户端所决定的](). 

####4. [_设计模式: 适配器模式_]() :bangbang:
+ 如下面代码如果有一个 **Filter** 类用于处理 **Waveform** 类,  可以发现 **Filter** 接口类似 **Processor** 接口, 但是他们显然没有上下关系, 此时我们需要使用适配器模式, 从而使得 **Apply** 类像处理 **Processor** 接口一样处理 **Filter**. 
+ 适配器模式把一个类的接口变换成客户端所期待的另一种接口, 从而使原本因接口不匹配而无法在一起工作的两个类能够在一起工作. 适配器模式有类的适配器模式和对象的适配器模式两种不同的形式(下面例子是类适配器模式). 
+ 该模式包含三个角色: 
    + 目标(Target)角色: 这就是所期待得到的接口. 注意: 由于这里讨论的是类适配器模式, 因此目标不可以是类. 
    + 源(Adapee)角色: 现在需要适配的接口. 
    + 适配器(Adaper)角色: 适配器类是本模式的核心. 适配器把源接口转换成目标接口. 显然, 这一角色不可以是接口, 而必须是具体类. 
+ 示例代码如下: 
    ```java
    /**
     * The type Waveform.
     * 辅助类, 不用关心
     */
    class Waveform{
        private static long counter;
        private final long id = counter ++;
        @Override
        public String toString(){return "Waveform " + id;}
    }
    
    /**
     * 抽象策略 Strategy.
     * 目标角色 Target
     */
    interface Processor{
        /**
         * Name string.
         *
         * @return the string
         */
        public String name();
    
        /**
         * Process object.
         *
         * @param input the input
         * @return the object
         */
        Object process(Object input);
    }
    
    /**
     * The type Filter.
     * 源角色 Adapee
     */
    class Filter{
    
        /**
         * Name string.
         *
         * @return the string
         */
        public String name(){
            return getClass().getSimpleName();
        }
    
        /**
         * Process waveform.
         *
         * @param input the input
         * @return the waveform
         */
        public Waveform process(Waveform input){
            return input;
        }
    }
    
    /**
     * The type Low pass.
     * 源角色的具体实现 Adapee
     */
    class LowPass extends Filter{
        /**
         * The Cutoff.
         */
        double cutoff;
    
        /**
         * Instantiates a new Low pass.
         *
         * @param cutoff the cutoff
         */
        public LowPass(double cutoff){this.cutoff  = cutoff;}
    
        @Override
        public Waveform process(Waveform input) {
            return super.process(input);
        }
    }
    
    /**
     * The type High pass.
     * 源角色的具体实现 Adapee
     */
    class HighPass extends Filter{
        /**
         * The Cutoff.
         */
        double cutoff;
    
        /**
         * Instantiates a new High pass.
         *
         * @param cutoff the cutoff
         */
        public HighPass(double cutoff){this.cutoff = cutoff;}
    
        @Override
        public Waveform process(Waveform input) {
            return super.process(input);
        }
    }
    /**
     * The type Band pass.
     * 源角色的具体实现 Adapee
     */
    class BandPass extends Filter{
        /**
         * The Lowoff.
         */
        double lowoff, /**
         * The Highoff.
         */
        highoff;
    
        /**
         * Instantiates a new Band pass.
         *
         * @param lowoff  the lowoff
         * @param highoff the highoff
         */
        public BandPass(double lowoff, double highoff){
            this.lowoff = lowoff;
            this.highoff = highoff;
        }
    
        @Override
        public Waveform process(Waveform input) {
            return super.process(input);
        }
    }
    
    /**
     * The type Filter adpter.
     * 适配器角色 Adaper 将 {@link Filter}接口转换为{@link Processor}接口. 
     */
    class FilterAdpter extends Filter implements Processor{
    
        private Filter filter;
    
        /**
         * Instantiates a new Filter adpter.
         *
         * @param filter the filter
         */
        public FilterAdpter(Filter filter){this.filter = filter;}
    
        public Waveform process(Object input) {
            return filter.process((Waveform) input);
        }
    }
    
    /**
     * The type Apply.
     * 环境角色 Context
     */
    class Apply {
        /**
         * The constant s.
         *
         * @param p the p
         * @param o the o
         */
        public static void process(Processor p, Object o){
            System.out.println(p.name());
            System.out.println(p.process((o)));
        }
    }
    
    /**
     * The type Filter processor.
     * 测试类
     */
    public class FilterProcessor{
        /**
         * The entry point of application.
         *
         * @param args the input arguments
         */
        public static void main(String[] args) {
            Waveform w = new Waveform();
            Apply.process(new FilterAdpter(new LowPass(1.0)),w);
            Apply.process(new FilterAdpter(new HighPass(2.0)),w);
            Apply.process(new FilterAdpter(new BandPass(3.0,4.0)),w);
        }
    }
  
    /* Output:
    FilterAdpter
    Waveform 0
    FilterAdpter
    Waveform 0
    FilterAdpter
    Waveform 0
    */
    ```
    
+ 适配器模式在实际工程中用的应该是比较多的一种模式, 一般发生在类库无法修改的情况下, 希望接口不同的两个类能够一起工作. 
+ 适配器模式的优点: 
    + 更好的复用性. 
    + 更好的扩展性. 
+ 适配器模式的缺点: 
    + 过多的使用适配器, 会让系统非常零乱, 不易整体进行把握. 比如, 明明看到调用的是A接口, 其实内部被适配成了B接口的实现, 一个系统如果太多出现这种情况, 无异于一场灾难. 因此如果不是很有必要, 可以不使用适配器, 而是直接对系统进行重构. 
    
####5. _多继承_
+ JAVA通过接口实现了多继承特性, 也就是说对于一个类(**class**)只能 **extends** 一个类或接口, 但是可以 **implements** 多个接口. **extends** 在前, **implements**在后. 
    ```java
    class ModifiedClass extends Class_1 implements interface_1, interface_2,interface_n{}
    ```

+ 可以向上转型为接口, 这样就意味着一个实现多个接口的类可以向上转型为多个不同的接口, 且这样的转型时安全的. 
+ 接口可以继承接口以此实现扩展接口, 使用 **extends** 关键字, [但是这里却可以继承多个接口, 这条规范显然和类单继承不一样](). :bangbang:
    ```java
    interface ModifiedInterface extends interface_1, interface_2,interface_3{}
    ```

+ 多接口中的名称冲突: 
    + 仍然遵循子类覆盖父类方法的书写规范. 
    + 同时实现多个接口, 接口中有名称相同且参数列表相同的方法声明时, 如果返回值相同, 编译器正常通过；如果返回值不兼容, 编译器会报错. 
    + [同时继承一个类并实现多个接口时, 在满足上面一条的要求是, 还要满足父类方法与接口声明不冲突. 如果在父类和接口中有名称相同且参数列表相同的方法声明时, 如果返回值相同, 编译器正常通过, 父类方法直接隐式实现接口对应方法；如果返回值不兼容, 编译器会报错](). :heavy_exclamation_mark:
    + 尽可能避免名称冲突这种情况的发生. 

####6. _嵌套接口的特性_
+ 类中嵌套的 **private** 修饰的接口, 只能在类内部被实现, 且可以实现为 **public** 类, 这种情况下的实现类只能被外部类其自身使用, 无法通过任何方式直接使用, 即使它是 **public** 修饰的. :bangbang:
    ```java
    class A{
      public interface A{void f();}  
      interface B{void f();}          //包访问
      private interface C{void f();}
      public class CImp implements C{
          public void f(){}  
      }
      public C getC(){return new CImp();}
      private C cRef;
      public void receive(C c){
          cRef = c;
          cRef.f();
      }
    }
  
    public class Test{
      public static void main(String[] args){
          A a = new A();
          //不能通过A.C获取
          //A.C ac = a.getC();
          //不能获取
          //A.CImp ac = a.getC();
          //不能操作f()
          //a.getC().f();
          A a2 = new A();
          a2.receive(a.getC());
      }
    }
    ```
    
+ 在接口中嵌套接口, 必须是 **public** 修饰. :heavy_exclamation_mark:
+ 在实现一个接口时, 并不需要实现接口内嵌套的接口. 

####7. [_设计模式: 工厂方法模式_]()
+ 工厂方法模式是类的创建模式, 又叫做虚拟构造子(Virtual Constructor)模式或者多态性工厂(Polymorphic Factory)模式. 
+ 工厂模式包含以下角色: 
    + 抽象工厂角色: 担任这个角色的是工厂方法模式的核心, 任何在模式中创建对象的工厂类必须实现这个接口. 在实际的系统中, 这个角色也常常使用抽象类实现. 
    + 具体工厂角色: 担任这个角色的是实现了抽象工厂接口的具体JAVA类. 具体工厂角色含有与业务密切相关的逻辑, 并且受到使用者的调用以创建导出类. 
    + 抽象角色: 工厂方法模式所创建的对象的超类, 也就是所有导出类的共同父类或共同拥有的接口. 在实际的系统中, 这个角色也常常使用抽象类实现. 
    + 具体角色: 这个角色实现了抽象导出角色所声明的接口, 工厂方法模式所创建的每一个对象都是某个具体导出角色的实例. 

+ 在下一章节通过匿名内部类可以更好的实现工厂模式, 到时候会重新介绍基于匿名内部类的实现方法. 下面是本章直接通过单独的实现接口实现的工厂方法模式: 
    ```java
    /**
     * \* Created with Chen Zhe on 12/25/2016.
     * \* Description:
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    public class Factories_1 {
    
        /**
         * Service consumer.
         * 产品的消费者
         * @param factory the factory
         */
        public static void serviceConsumer(ServiceFactory factory){
            Service service = factory.getService();
            service.method1();
            service.method2();
        }
    
        /**
         * The entry point of application.
         *  测试类
         * @param args the input arguments
         */
        public static void main(String[] args) {
            serviceConsumer(new ServiceFactoryImpl_1());
            serviceConsumer(new ServiceFactoryImpl_2());
        }
    }
    
    /**
     * The interface Service.
     * 抽象产品接口
     */
    interface Service{
        /**
         * Method 1.
         */
        void method1();
    
        /**
         * Method 2.
         */
        void method2();
    }
    
    /**
     * The interface Service factory.
     * 抽象工厂接口
     */
    interface ServiceFactory{
        /**
         * Gets service.
         *
         * @return the service
         */
        Service getService();
    }
    
    /**
     * The type Service impl 1.
     * 具体产品角色
     */
    class ServiceImpl_1 implements Service{
    
        public void method1() {
            System.out.println("ServiceImpl_1 method1()");
        }
    
        public void method2() {
            System.out.println("ServiceImpl_1 method2()");
        }
    }
    
    /**
     * The type Service impl 2.
     * 具体产品角色
     */
    class ServiceImpl_2 implements Service{
    
        public void method1() {
            System.out.println("ServiceImpl_2 method1()");
        }
    
        public void method2() {
            System.out.println("ServiceImpl_2 method2()");
        }
    }
    
    /**
     * The type Service factory impl 1.
     * 具体工厂角色1
     */
    class ServiceFactoryImpl_1 implements ServiceFactory{
        public Service getService() {
            return new ServiceImpl_1();
        }
    }
    
    /**
     * The type Service factory impl 2.
     * 具体工厂角色2
     */
    class ServiceFactoryImpl_2 implements ServiceFactory{
    
        public Service getService() {
            return new ServiceImpl_2();
        }
    }
  
    /* Output:
    ServiceImpl_1 method1()
    ServiceImpl_1 method2()
    ServiceImpl_2 method1()
    ServiceImpl_2 method2()
    */
    ```

+ 抽象工厂模式的优点是不用关心类的具体创建逻辑, 适合用于框架创建. 
+ [工厂方法模式与简单工厂模式的区别: 简单工厂模式中工厂直接是具体类, 没有抽象工厂接口. 在工厂方法模式中, 所有工厂类都有一个共同的抽象工厂接口. 但是工厂方法模式相比抽象工厂模式, 工厂方法模式中每个工厂类对应同一个产品抽象接口；而抽象工厂模式则需要面对多个产品抽象接口. ]() :bangbang:

####8. _接口VS抽象类_
+ [抽象类仍然是 **Object** 子类, 而接口不是!!!]() :bangbang: 以下来源于官方文档说明, 也就是说每个interface都会在内置于 **Object** 相同的方法, 但是它不是 **Object**. 
    > If an interface has no direct superinterfaces, then the interface implicitly declares a public abstract member method m with signature s, return type r, and throws clause t corresponding to each public instance method m with signature s, return type r, and throws clause t declared in Object, unless a method with the same signature, same return type, and a compatible throws clause is explicitly declared by the interface.

+ abstract class通常含有一个或多个抽象方法, 抽象方法不提供实现；包含抽象方法的类必须声明为抽象类abstract class；abstract class的所有具体子类都必须为超类提供具体实现；子类如果没有实现超类的抽象方法, 则会产生编译错误, 除非子类也声明为abstract. 
+ abstract class声明了类层次结构中各个类的共同属性和行为；由于不能继承构造函数, 因此构造函数不能声明为抽象方法；尽管不能实例化抽象类的对象, 但是能够声明抽象类型的变量, 这种变量可用于引用子类的对象. 
+ 接口以interface开始, 并包含一组默认为是public的抽象方法, 接口可以包含变量, 默认为static final的, 且必须给其初值, 所以实现类中不能重新定义, 也不能改变其值；实现接口必须实现其中的所有方法, 接口中不能有实现方法, 所有的成员方法都是abstract的. 
+ 如果一个类没有实现任何接口方法, 则它是抽象类, 并且必须以关键字abstract声明该类；实现一个接口如同与编译器达成一个协议, “我将声明该接口制定的所有方法”. 