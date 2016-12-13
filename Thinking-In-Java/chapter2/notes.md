###Chapter 2 : Everything Is an Object
####1. _引用和对象之间的关系_
+ 书中给了一个比喻, 我觉得还不错. 如果 **对象/object** 是一台电视机, **引用/reference** 就是遥控器, 想要改变电视显示的内容需要通过遥控器实现操作. **JAVA** 中想要操纵目标都是通过引用来实现的(?).

####2. _**JAVA** 存储管理_
+ **寄存器/Register:** 内置于CPU中, 速度最快, 应该值得是L1, L2, L3这些缓存, 在JAVA中无法直接或间接操作.
+ **栈/Stack:** 一般在RAM中, 但是CPU可以直接操作通过栈指针, 指针下移创建新内存, 指针上移释放内存, 速度仅次于寄存器. 因为必须在创建程序时, 知道栈中所有对象的确切生命周期, 因此栈中只存放引用, 而不存放引用实际指向的对象. 
+ **堆/Heap:** 一般在RAM中, 因为没有栈中明确的生命周期限制, 因此其存储管理更加弹性, 其间存储了所有通过**new**创建的对象. 当然, 这样的代价就是需要花费更多的时间去安排和清理存储. 
+ **常量存储/Constant storage:** 常量值因为不会变化, 所以一般在程序代码中存储. 
+ **非RAM存储/Non-RAM storage:** 与程序运行相互不依赖的数据. 

####3. _基本类型/primitive types_
+ 基本类型表格, 基本类型值直接存储于栈中, 不存在引用与对象的关系, 且不能使用new关键字创建. 

    |   基本类型   | 占用空间  | 最小值           | 最大值                   | 默认值              | 包装类        |
    |:-----------:|:--------:|:---------------:|:------------------------:|:------------------:|:-------------:|
    | **boolean** | -        | -               | -                        | **false**          | **Boolean**   |
    | **char**    | 16bit    | Unicode 0       | Unicode 2<sup>16</sup>-1 | **'\u0000'(null)** | **Character** |
    | **byte**    | 8bit     | -128            | +127                     | (byte)0            | **Byte**      |
    | **short**   | 16bit    | -2<sup>15</sup> | +2<sup>15</sup>-1        | (short)0           | **Short**     |
    | **int**     | 32bit    | -2<sup>31</sup> | +2<sup>31</sup>-1        | 0                  | **Long**      |
    | **long**    | 64bit    | -2<sup>63</sup> | +2<sup>63</sup>-1        | 0L                 | **Long**      |
    | **float**   | 32bit    | IEEE754         | IEEE754                  | 0.0f               | **Float**     |
    | **double**  | 64bit    | IEEE754         | IEEE754                  | 0.0d               | **Double**    |
    | **void**    | -        | -               | -                        | void               | **void**      |
+ 其中**void** 在JDK文档中并不属于基本类型, 本书中将其划分为基本来信, 我猜想是从基本类型存储于栈中而不是堆的角度来思考的, 因为**void** 并不能通过new关键字创建. 
+ JAVA中, 为了兼容Unicode编码, 所以**char** 占用的是2个**byte**. 
+ SE5中提供了基本类型与包装类的之间的自动装箱和拆箱操作. 
+ **BigInteger** 和 **BigDecimal** 提供了相比基本类型更精准的高精度计算, 提供的操作方法与基本数值类型操作类似. 
+ JAVA中**Array** 是引用集合, 因此其必须初始化所有引用的指向对象, 否则默认为**null**, 也因此所有的集合泛型约束必须是引用类型, 对于基本类型必须使用其包装类. 
+ 当基本类型作为类的成员变量时, 即使不进行初始化, 也会拥有默认值, 但是如果基本类型作为方法中的临时变量未初始化时, 编译器会在编译时报错. 
+ 基本类型在方法的输入参数列表中, 属于值传递, 引用类型属于引用传递. 

####4. _域_
+ 域是可以限定引用的生命周期, 但是无法限定类的生命周期, 当栈中的引用被回收后, 引用指向的对象仍然存在与堆中, 其回收时间有GC决定. 

####5. _**static** 关键字_
+ 直接通过类名调用静态方法和静态成员, 相比通过新建对象调用静态方法和静态成员, 能够给编译器更多机会进行优化, 所以推荐使用前者. 

####6. _编译_
+ 书里说使用 **_Ant_** 进行编译比直接用javac编译更快, 因为有更多的优化(?).

####7\*. _注释/Comment 和[API文档/Javadoc]()_
+ 两种注释方法
    ```java
    
    /*
    * fisrt way
    */

    // second way
    ```
+ API文档的注释方法
    ```java
    /** A class comment */
    public class Ducomemtation1 {
      /** A field comment */
      public int i;
      /** A method comment */
      public void method(){}
    }
    ```

+ Javadoc支持HTML标签或者@标签
    + @see和@link均为超链接标签, 区别是一个外联一个内联. 
    + @docRoot设置doc根目录. 
    + @inheritDoc设置从最近基类继承API文档. 
    + @version版本信息. 
    + @author作者信息. 
    + @since该特性的初始开发时间. 
    + @param参数描述. 
    + @return返回值描述. 
    + @throws异常说明. 
    + @deprecated该方法或特性将会在未来被移除, 不推荐使用. 
    ```java
    /** The document example
     *  @author Chen Zhe
     *  @author q953387601@163.com
     *  @version 1.0
    */
    public class Documentation2{
      /**
       * @param args array of string arguments
       * @throws Exception No exceptions thrown
      */ 
      public static void main(String args[]){}  
    }
    ```