###Chapter 7 : Reusing Classes
####1. _三种复用方式_
+ 组合: 在类中直接通过新建实例的方式获取其他类的功能
+ 继承: 直接隐式的获取父类的接口
+ 代理: 在JAVA中并没有直接支持这种模式

####2. _组合/composition_
+ 因为只是希望获取其他类的部分功能, 而不是需要其本身对外暴露, 所以建议尽可能多的使用 **private** 关键字. 
+ 类之间的关系为 **has-a**. 

####3. _继承/inheritance_
+ 通过 **extends** 隐式的继承了其父类的接口. 
+ 当前类与父类之间的关系是 **is-a** 或者 **is-like-a**.
+ 在书中, 作者虽然明确了继承时OOP的一大主要思想, 但是劝诫应该谨慎使用, 只有存在类需要向上转换类型的情况下, 才会考虑使用继承这一方法. 
+ 大多数情况下, 继承与组合的方式是并存的, 因为所有类继承于 **Object** 类, 当类中新建了其他类的实例时, 就是一种继承与组合并存的方式, 往往这种方式可能更加灵活. 
+ 使用 **protected** 或 **public** 关键字可以有效的保证在不同包内具有继承关系的子类能够有效使用并重写父类接口. 
+ 具有继承关系的子类可以能够自动转为父类, 只是丢失部分自有特性, 这是一种安全的类型转换. 
+ 使用注释 **@Override** 可以显示说明当前方法是覆盖父类方法的, 这种方法可以防止你在不想重载时意外地进行了重载, 因为编译器会进行检查. :heavy_exclamation_mark:
+ 类实例化顺序: 首先调用父类构造函数, 如果父类仍然后父类, 则继续向上递归, 直到找到顶层, 然后依次首先调用父类的构造函数. 如果你在子类的构造函数中不显示调用父类构造函数, 那么编译器会在编译时自动为你添加调用 **super()**. 
+ 如果父类自定义了具有参数列表的构造函数, 因为编译器只会自动添加 **super()**, 可能会因此报错显示无法找到对应构造函数. 两种解决办法, 一种是在父类中添加无参构造函数, 一种是在子类中显示调用 **super(参数列别)**, 参数列表需要与父类构造函数一致, 且必须保证在子类构造函数中的第一行进行显示调用, 否则会报错. 
+ 类内存清理顺序: 与实例化顺序相反, 首先释放当前类资源, 在最后调用父类内存清理方法进行清理. :heavy_exclamation_mark: JAVA中所有类都有 **void finalize()** 方法, 用于dispose, 书里写了一个 **void dispose()** 的方法有点迷惑性, 好像C#是有 **dispose()**. 
    ```java
    public class SubClassTest extends BaseClass{
        protected int weight;
        public SubClassTest(String name, int size, int length, int weight) {
            super(name, size, length);
            this.weight = weight;
            System.out.println("construct SubClassTest");
        }
        @Override
        public void finalize() throws Throwable{
            System.out.println("do something to dispose SubClassTest");
            super.finalize();
        }
        public static void main(String[] args) throws Throwable {
            SubClassTest test = new SubClassTest("test",1,2,3);
            test = null;
            System.gc();
        }
    }
    class BaseBaseBaseClass{
        protected String name;
        public BaseBaseBaseClass(String name){
            this.name = name;
            System.out.println("Construct BaseBaseBaseClass");
        }
    
        @Override
        protected void finalize() throws Throwable {
            System.out.println("do something to dispose BaseBaseBaseClass");
            super.finalize();
        }
    }
    class BaseBaseClass extends BaseBaseBaseClass{
        protected int size;
        public BaseBaseClass(String name, int size){
            super(name);
            this.size = size;
            System.out.println("construct BaseBaseClass");
        }
        @Override
        protected void finalize() throws Throwable {
            System.out.println("do something to dispose BaseBaseClass");
            super.finalize();
        }
    }
    class BaseClass extends BaseBaseClass{
        protected int length;
        public BaseClass(String name, int size, int length){
            super(name,length);
            this.length = length;
            System.out.println("construct BaseClass");
        }
        @Override
        protected void finalize() throws Throwable {
            System.out.println("do something to dispose BaseClass");
            super.finalize();
        }
    }
    ```
    ```java
    /* OutPut
    Construct BaseBaseBaseClass
    construct BaseBaseClass
    construct BaseClass
    construct SubClassTest
    do something to dispose SubClassTest
    do something to dispose BaseClass
    do something to dispose BaseBaseClass
    do something to dispose BaseBaseBaseClass
    */
    ```

####4. _代理/delegation_
+ 这里应该是一种简化版的代理, 思想应该还是代理模式的思想. 这里只是希望通过一个中间类来解决具有相同接口功能但是明显不具备 **is-a** 或者 **like-a** 关系的两个类. 
    ```java
    class SpaceShipController{
      void up(int velocity){}
      void down(int velocity){}
      void left(int velocity){}
      void right(int velocity){}
    }
    class Delegation{
      private String name;
      private SpaceShipController controller= new SpaceShipController();
      public Delegation(String name){this.name = name;}
      public void up(int velocity){controller.up(velocity);}
      public void down(int velocity){controller.down(velocity);}
      public void left(int velocity){controller.left(velocity);}
      public void right(int velocity){controller.right(velocity);}   
    }
    ```
    
+ 上面并不是代理模式, 这是一种类的复用方式, 代理模式可能在书后会有介绍, 到时候再记录. 

####5. _final关键字_
+ 三种用途: 修饰变量, 修饰方法, 修饰类
+ 修饰变量: 
    + 修饰基础变量时, 表示为常数值, 在编译时处理, 而不是运行时. ---编译时常量
    + 修饰对象时, 表示指向对象的引用不可变, 并不代表指向对象的内容不能发生更改. ---初始化数组
    + 修饰参数列表时, 表示形参引用不可变, 如果是基础变量则值不能变, 一般用于匿名内部类的读取. 
    ```
    void add(final int a, final Object o)
    {
      a++; /*this is wrong*/  
      o = new Object(); /*this is wrong*/ 
    }
    ```
    + 修饰空白变量时, 表示在构造方法中强制初始化. 
    ```java
    public class BlankFinal{
      private final int i;
      private final Object o;
      BlankFinal(){
          i = 1;
          o = new Object();
      }
      BlankFinal(int i, Object o){
          this.i = 1;
          this.o = new Object();
      }
    }
    ```
    + 配合 **static** 使用唯一且初始化后不可变. 
    + 书写规范是大小字母, 用下划线连接. 
+ 修饰方法: 
    + 早期通过 **final** 修饰的方法在调用时可以直接内联提高效率, 但是这有问题就是如果一个方法大量内联或者内容过大, 会导致效率不高, 现在已经不提倡这种用法了. :heavy_exclamation_mark:
    + 表示方法不可被 **@Override**, 不代表方法不可见, 与 **private** 不可混为一谈. 
+ 修饰类: 
    + 表示类不可以被继承, String类就是不可集成的. 
    ```java
    public final class String implements Serializable, Comparable<String>, CharSequence {...}
    ```

+ 谨慎使用 **final**, 除非你能肯定被修饰内容的将不需要被变更. 
    
####6. _详细的初始化和类加载过程_
+ 首先试图获取类的main函数. 
+ 之后类加载器就会试图加载当前类的class文件. 
+ 当发现当前类存在父类时, 则向上递归查询, 直到发现跟父类. 
+ 逐层向下, 对其静态成员进行初始化. 
+ 此时必要类已经加载完成, 此时对象中的基本类型以及对象都会被初始化
+ 然后依然是逐层向下, 调用构造方法. 
+ 按顺序初始化实例变量. 