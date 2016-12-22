###Chapter 8 : Polymorphism
####1. _动态绑定_ :heavy_exclamation_mark:
+ 多态的实现方式就是动态绑定。
+ 方法绑定的实现方法: 
    + **前期绑定**：在编译时，程序运行前，编译器与链接器将对应的方法进行链接。这种方法在C语言这种面向过程的语言中比较常见。
    + **后期绑定/动态绑定**：绑定过程发生在运行时，而不再是编译时。这种绑定方法需要实现一种机制去确定对象的类型，以此避免在编译时确定类型信息，而是在运行时确定类型信息。(这个机制应该是值RTTI？ 之前提到过，后面的章节应该会详细说)。
+ JAVA中除了 **static** 和 **final** 修饰的方法之外，其余所有的方法都是进行后期绑定的。需要注意的是 **private** 方法也属于 **final** 方法，可能原因是对于这两个关键字修饰的方法，其意义都是不可被子类复写，因此也就不存在多态。:bangbang:
+ 当声明了一个 **final** 方法，在编译时相当于告诉编译器关闭动态绑定功能，这是编译器可能会对代码进行些许的优化。
+ 通过动态绑定，可以实现父类指针指向子类目标，这也就意味着可以通过直接操作父类指针调用子类复写的。
+ “动态是一种可以将改变事物与未改变事物分离开来的技术”，也就是如果仅仅只指针指向的对象增加了复写方法或者一些别的操作，只要操作父类的指针没有发生变化，即使不编译操作指针相关的类一样可以正常实现多态特性(表述可能不清楚，意思就是多态是发生在运行时的)。

####2. _缺陷_
+ "覆盖"私有方法: 用 **private** 修饰的方法同样也不能被覆盖，因此无法实现动态绑定，但是在这种情况因为方法名称，返回值类型，参数列表的相同会导致一种迷惑性。
    + 这个问题并不是技术上的问题，只是会影响代码的可读性，因此应该尽可能避免使用和父类私有方法相同的方法名。
    ```java
    public class PrivateOverride{
      private void f(){System.out.println("private f()");}
      public static void main(String[] args){
          PrivateOverride po = new Derived();
          po.f();
      }
    }
  
    class Derived extends PrivateOverride{
      //@Override 如果用这个注解，编译时会报错，因为根本就没有方法可供覆盖
      public void f(){System.out.println("public f()");}
    }
  
    /* Output
      private f()  
    */
    ```
    
+ 成员变量和静态方法：动态绑定只发生在那些普通的方法调用。如果直接访问某个成员变量，这个访问在编译时就已经完成。
    + 看下面输出第一行，直接获取的i是父类的i值，而不是子类覆盖方法后应该返回的i值，这是因为这一切在编译时已经完成，并不支持运行时的动态绑定。但是通过来访问则没有问题。
    + 这个问题主要是因为编写习惯的问题，因为良好的编程习惯要求我们私有化成员变量，同时请不是要使用相同名称的公共变量，以避免歧义。
    ```java
    class Super {
      public int i = 0;
      public int getI(){return i;} 
    }
  
    class Sub extends Super{
      public int i = 1;
      @Override
      public int getI(){return i;}
      public int getSuperI(){return super.i;}
    }
  
    public class FieldAccess{
      public static void main(String[] args){
          Super sup = new Sub();
          System.out.println("sup.i = " + sup.i +
              ", sup.getI() = " + sup.getI());
          Sub sub = new Sub();
          System.out.println("sub.i = " + sub.i +
              ", sub.getI() = " + sub.getI() + 
              ", sub.getSuperI() = " + sub.getSuperI());
      }
    }
    /* Output
    sup.i = 0, sup.getI() = 1
    sub.i = 1, sub.getI() = 1, sub.getSuperI() = 0
    */
    ```
    
####3. __