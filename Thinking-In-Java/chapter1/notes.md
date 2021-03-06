###Chapter 1 : Introduction to OOP
####1. _OOP 5个特征_
+ 一切事物都是对象.
+ 程序是对象的集合, 它的运作是通过对象之间的**sending messages发送消息**实现的.
+ 每个对象都有一部分独立存储是由已知其他对象构成的, 这样可以通过复杂性隐藏域简单对象之后.
+ 每一个对象都有明确的类, **对象 object**是**类 class**的**实例 instance**.
+ 所有同属同样类型的对象可以接受相同格式**信息 messages**.

####2. _对象包含3个部分_
+ **状态/State:** 指对象中的内部.
+ **行为/Behavior:** 指对象中的方法.
+ **身份/Identity:** 指每个对象在内存中有不同的内存地址, 可以保证每个对象独一无二. _这里需要注意对象可以存在于不同机器, 不同地址空间或者存储于硬盘中, 因此在这种情况下需要通过其他信息区分不同对象._

####3. _每个对象都有接口_
+ **接口/Interface:** 明确了每个对象可以接受哪些满足要求的请求信息.

####4. _一个对象提供多种服务_
+ 每个对象都可以认为是一个服务提供者.
+ 如果一个对象只提供一种服务, 那么这种设计既满足软件设计基础要求之一**高内聚**, 也提高了代码的重用性.

####5. _访问权限控制_
+ **类型创建者/class creator:** 仅仅暴露类型使用者所必须的部分, 其余部分全部隐藏. 有点像MVC中的M.
+ **类型使用者/client programmer:** 通过调用类型完成应用的快速开发. 有点像MVC中的C.
+ **类型创建者/class creator** 与 **类使用者/client programmer** 应该有明确的区分----**访问权限控制/access control**.
+ **访问权限控制/access control:** JAVA中提供 **_private_**, **_protected_**, **_public_**, **_default_** 四种权限控制.
    + **_private_:** 字面意义, 可修饰类, 成员变量, 方法.
    + **_protected_:** 仅子类可以访问该关键字修饰的成员,只可以用于修饰变量和方法, 不能用于修饰类(既然继承则应该是**_public_**).
    + **_public_:** 字面意义, 可修饰类, 成员变量, 方法.
    + **_default_:** 对包内 **_public_**, 对包外 **_private_**, 不用显式调用.

####6. _类重用_
+ 通过在类中调用其他类, 实现类的重用, 有两种不同方式: **组合/composition** 和 **聚合/aggregation**.
+ **组合/composition:** 强耦合, 必须包含的关系.
  ```Java
  public class Car{
      //some code
      private Engine engine = new Engine();
      public Car(){}
      //some code
  }
  ```
+ **聚合/aggregation:** 具有动态性, 具体存在关系是动态存在的
  ```java
  public class Car{
      //some code
      private Engine engine;
      public Car(Engine engine){
          this.engine = engine;
      }
      //some code
  }
  ```
  
####7. _继承/inheritance_
+ 被继承类也称为父类, 超类, 基类.
+ 继承类称为派生类, 继承类, 子类.
+ **JAVA**中的继承关系是单根继承, 一个类只能有一个上级父类, 所有类的最上级父类是**_Object_**.
    + 提高开发效率
    + 类型功能明确
    + 便于垃圾回收的实现
+ 子类区别于父类的两种方法, 虽然前者在OOP中更加推崇, 但是现实中往往不能完全满足
    + **重写/override**添加任何新的方法, 重写从父类继承的方法,is-a. JAVA中貌似没有overwrite这个说法？
    + 添加新方法, 并调整从父类继承方法的实现方法, is-like-a.

####8. _多态/polymorphism_
+ 编译器在编译代码时, 仅确认调用方法是否存在, 并执行类型声明检查, 最后返回值, 但并不知道也不需要知道具体执行哪段代码.
+ **JAVA** 通过迟绑定或者叫动态绑定, 实现在具体方法被实际调用时才确认执行代码的位置.
+ **向上转换/upcasting** 是安全的,因为符合逻辑.
+ **向下转换/downcasting** 是不安全的.

####9. _容器/containers_
+ 容器默认装箱类型是 **_Object_**, 这是一个 **upcasting** 过程.
+ 从容器中取出实例需要将**_Object_**转换为其他类型,这是一个 **downcasting** 过程, 需要进行类型检查.
+ 因此SE5以后, 提供了**泛型/generics**, 明确一个容器只能包装一种指定类型, 这样在编译阶段即可完成类型检查, 减少了向下转换和运行时检查带来的不必要的时间消耗.

####10. _类的生命周期_
+ 通过在成为 **堆/heap** 的内存中动态创建类, 并使用GC进行自动化的内存回收.

####11. _并发编程_
+ SE5之后提供Concurrent并发包.
+ 注意需要解决不同线程间的资源共享问题.
