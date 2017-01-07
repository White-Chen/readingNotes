###Chapter 12 : Error Handling with Exception
**Java的基本理念就是结构不佳的代码不能运行**

####1. _概念_
+ 背景: C以及早期语言常常具有多种错误处理模式. 这些模式中通常通过返回某个特殊值或者设置摸个标志, 并且假定接收者将对这个返回值或标志进行检查. 以判定是否发生错误. [这种方法有两个主要缺陷: 1,每次调用方法时都需要进行彻底错误检查, 这会产生大量冗余代码；另一点调用者完全可以不检查异常, 这将导致程序健壮性收到极大影响.]()
+ 解决: 用强制规定的形式消除错误处理过程中随心所欲的因素. . 
+ 使用异常带来两点好处: 1.总能有合适的地方处理或抛出异常, 这使得你不得不正视异常处理, 提高程序的健壮性；2.不需要在方法调用出进行异常检查, 因为异常机制能够保证捕获异常, 并只需要在一个合适的地方处理异常, 这极大地降低了错误处理代码的复杂度. 

####2. _基本异常_
+ 区别异常情形和普通问题情形: 
    + 普通问题情形是指在 **当前环境** 下总能处理的错误. 
    + 异常情形是指在 **当前环境** 下无法获得必要的信息来解决问题, 因此只能结束当前运行路径, 并抛给给上一级环境. 
+ 抛异常过程: 
    + 1. 系统会在堆上新建一个异常类对象；
    + 2. 结束当前执行路径；
    + 3. 从当前环境中返回异常对象引用；
    + 4. [异常处理机制]()接管程序, 并向上寻找一个异常处理程序；
    + 5. 异常处理程序将程序从错误状态中恢复, 从而是程序可以继续运行下去；
    + 6. 如果找不到异常处理程序, 那么应该是退出当前程序, 并输出错误异常. 

+ Java中的异常机制允许我们: 
    + 最坏状态: 没有处理手段, 则强制程序停止运行. 
    + 理想状态: 处理异常, 并返回稳定状态. 

+ 通过关键字 **throw** 抛出一个异常对象引用. 
    ```java
    throw new Exception();
    throw new Exception("Exception info");
    ```

+ 异常参数如上面所示, 在创建异常时, 所有标准异常类都有两个构造器: 
    + 默认无参构造方法. 
    + 字符串构造方法, 传入可以描述当前异常信息的字符串.  

####3. _捕获异常_
+ 监控区域/guarded region: 一段可能产生异常的代码, 并且后面跟着处理这些异常的代码. 
+ 语法: 
    + try块, 用于包围可能会抛出异常的方法块. 
    + catch块, 处理try块中抛出的异常. [异常处理机制将负责搜寻参数与异常类型相匹配的第一个处理城区, 然后进入catch字句执行, 并且只有匹配的catch才会执行.]()
    + finally块, 可选块, 表明try块中无论是否发生异常, 都会执行的逻辑操作. 
    ```java
    try{
    // Code that might generate exceptions
    } catch{Type id1}
    // Handle exception of Type1
    } catch{Type2 id2}
    // Handle excetion of Type2
    } catch{Type2 id2}
    // Handle excetion of Type2
    } finally{
    // do something Whether or not error
    }
    // etc..
    ```

+ [终止与恢复:]() :heavy_exclamation_mark:
    + 终止模型: 错误非常关键, 一旦异常被抛出, 就无法继续执行. [推荐使用.]()
    + 恢复模型: 异常被处理后能继续执行程序. 通过调用方法来修正错误, 或者把try块放在while循环里, 直到获得满意结果. [这种模型会导致耦合, 增加代码编写和维护点额困难.]() ::

####4. _自定义异常_
+ Java虽然提供了一些列标准异常类, 但是完全可以自己通过继承异常类实现自定义异常. (最好选择意义相近的类继承)
+ [对于一个异常类来说, 最重要的是它的类名, 名字应该尽量能够体现改异常的信息.]()
+ 所有异常类均实现了 **Throwable** 接口, 从字面意义可看出实现该接口的类可以被抛出. 
+ 所有异常类的根类是 **Exception** 类, 该类默认实现了两种构造方法. 
+ 打印异常信息时, [推荐使用System.err输出流, 因为它不会像System.out输出流一样会有可能被重定向输出.]() e.printStackTrace()默认调用的是System.err输出流. 
+ 示例: 
    ```java
    class MyException extends Exception{
      public MyException(){}
      public MyException(String exceptionInfo){super(exceptionInfo);}  
    }
  
    public class TestException_1{
      public static void f() throws MyException{
        System.out.println("Throw");
      }
      public static void main(String[] args){
          try{
              f();
          }catch (MyException e){
              e.printStackTrace();
          }
      }
    }
    ```

+ 异常功能一般情况下都是与日志功能相关联的, 通过在处理异常时记录日志, 从而保留下程序错误的信息以供分析.   
一般有两种写法, 一种是在异常类中内置日志对象, 从而在异常类被new时记录日志, 还有一种是在调用异常类的类中内置日志对象记录当前类处理异常的日志. 两种写法各有优势, 一般由后者的比较多. 下面示例显示的就是后者, 其中使用了 **java.util.logging** 中的日志记录功能.   
观察下面代码的输出, 可以发现一个问题, 就是明明是sout调用在前, 但是输出结果却是在后面. 这主要是因为一个调用的是out一个调用的是err, 两者之间输出顺序会受到系统调度的影响. 
    ```java
    class MyException2 extends Exception {
        private int x;
        public MyException2(){}
        public MyException2(String mes){super(mes);}
        public MyException2(String mes, int x){
            super(mes);
            this.x = x;
        }
        public int val(){return x;}
    
        @Override
        public String getMessage() {
            return "Detail message: " + x + " " + super.getMessage();
        }
    }
    
    public class LoggingExceptions{
    
        private static Logger logger =
                Logger.getLogger("LoggingExceptions");
    
        static void logException(Exception e){
            StringWriter writer = new StringWriter();
            //使用重载方法
            e.printStackTrace(new PrintWriter(writer));
            logger.severe(writer.toString());
        }
    
        public static void main(String[] args) {
    
            try{
                throw new NullPointerException();
            }catch (NullPointerException exception){
                logException(exception);
            }
            try {
                throw new MyException2();
            }catch (MyException2 e){
                logException(e);
            }
            try{
                throw new MyException2("3rd try block");
            }catch (MyException2 e){
                logException(e);
            }
            try{
                throw new MyException2("4th try block",4);
            }catch (MyException2 e){
                System.out.println("e.val = " + e.val());
                logException(e);
            }
        }
    }
    
    /*Output:
    Jan 07, 2017 7:57:16 PM net.mindview.test.LoggingExceptions logException
    SEVERE: java.lang.NullPointerException
    	at net.mindview.test.LoggingExceptions.main(LoggingExceptions.java:41)
    
    Jan 07, 2017 7:57:16 PM net.mindview.test.LoggingExceptions logException
    SEVERE: net.mindview.test.MyException2: Detail message: 0 null
    	at net.mindview.test.LoggingExceptions.main(LoggingExceptions.java:46)
    
    Jan 07, 2017 7:57:16 PM net.mindview.test.LoggingExceptions logException
    SEVERE: net.mindview.test.MyException2: Detail message: 0 3rd try block
    	at net.mindview.test.LoggingExceptions.main(LoggingExceptions.java:51)
    
    Jan 07, 2017 7:57:16 PM net.mindview.test.LoggingExceptions logException
    SEVERE: net.mindview.test.MyException2: Detail message: 4 4th try block
    	at net.mindview.test.LoggingExceptions.main(LoggingExceptions.java:56)
    
    e.val = 4
    */
    ```
    
+ 书中作者给出一个观点: 大多数程序员可能仅仅只查看一下抛出的异常类型, 其他就不管了, 因此对异常类所添加的其他功能也许根本用不上. 

####5. _异常说明_
+ 通过关键 **throws** 可以用于告示使用当前类/方法的程序员, 该类/方法抛出哪些异常. 
+ [这是一种强制性语法, 这意味如果当前类/方法能处理某些异常, 那就需要对这些异常进行说明, 以此来告知调用者需要处理. 也就是说对于异常, Java语法明了只有两种处理方式, 要么throws要么就catch.]()
+ [有一种接口设计思路: 就是明明方法不会抛出异常, 但是也throws了异常, 这就迫使使用者必须像真的抛出异常那样使用这个方法, 这样做的好处是为了异常先占位置, 以后方便抛出异常时不用修改代码. 在定义抽象基类或接口时这种设计很重要, 这样派生类或接口实现就能够抛出这些预先声明的异常.]()
+ 这种在编译时就能被检查的异常称为[被检查的异常](). 

####6. _捕获所有异常_
+ 只要catch所有异常的基类Exception类就可以捕获所有异常, 因此需要注意[最好把它放在catch列表的末尾, 以防它在其他catch语句之前先捕获了异常.]()
+ 所有实现 **Throwable** 接口的类都可以通过 _printStackTrace()/getStackTrace()_ 方法打印或者获取栈轨迹.
+ _getStackTrace()_ 会通过数组的形式返回栈轨迹, [其中每一个元素都表示栈中的一帧](). 栈顶元素是调用序列中的最后一个方法调用, 这符合栈的先进后出原则. 
    ```java
    public class WhoCalled {
        static void f(){
            try{
                throw new Exception();
            } catch (Exception e){
                for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                    System.out.println(stackTraceElement.getClassName() + "  " + stackTraceElement.getLineNumber());
                }
                System.out.println("-----------------new function-----------------");
            }
        }
        static void g(){f();}
        static void h(){g();}
    
        public static void main(String[] args) {
            WhoCalled.f();
            WhoCalled.g();
            WhoCalled.h();
        }
    }

    /*Output: 顺序是按照栈的出栈顺序定义的. 
    net.mindview.test.WhoCalled  14
    net.mindview.test.WhoCalled  26
    sun.reflect.NativeMethodAccessorImpl  -2
    sun.reflect.NativeMethodAccessorImpl  62
    sun.reflect.DelegatingMethodAccessorImpl  43
    java.lang.reflect.Method  498
    com.intellij.rt.execution.application.AppMain  147
    -----------------new function-----------------
    net.mindview.test.WhoCalled  14
    net.mindview.test.WhoCalled  22
    net.mindview.test.WhoCalled  27
    sun.reflect.NativeMethodAccessorImpl  -2
    sun.reflect.NativeMethodAccessorImpl  62
    sun.reflect.DelegatingMethodAccessorImpl  43
    java.lang.reflect.Method  498
    com.intellij.rt.execution.application.AppMain  147
    -----------------new function-----------------
    net.mindview.test.WhoCalled  14
    net.mindview.test.WhoCalled  22
    net.mindview.test.WhoCalled  23
    net.mindview.test.WhoCalled  28
    sun.reflect.NativeMethodAccessorImpl  -2
    sun.reflect.NativeMethodAccessorImpl  62
    sun.reflect.DelegatingMethodAccessorImpl  43
    java.lang.reflect.Method  498
    com.intellij.rt.execution.application.AppMain  147
    -----------------new function-----------------
    */
    ```

+ 重新抛出异常.
    + 通过在catch语句中重新throw可以抛出当前捕获的异常. [重抛异常会把当前捕获异常抛给上一级环境中, 同一个try块的后续子catch字句将被忽略.]() :heavy_exclamation_mark: 
    ```java
    catch(Exception exception){
      System.out.println("Catch an exception");
      throw exception;
    }
    ```
    
    + 但是如果仅仅只是抛出异常对象, 那么上级环境在调用 _printStackTrace()_ 时只会显示原来抛出点的调用栈信息, 而非重新抛出点的信息. :bangbang:
    ```java
    public class WhoCalled {
        static void f() throws Exception {
            try{
                throw new Exception();
            } catch (Exception e){
                throw e;
            }
        }
        static void g() throws Exception {
            try {
                f();
            } catch (Exception e) {
                throw e;
            }
        }
        static void h() throws Exception {
            try {
                g();
            } catch (Exception e) {
                throw e;
            }
        }
    
        public static void main(String[] args) {
            try {
                WhoCalled.h();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  
    /*Output: 看下面输出信息可以明显发现e只记录了原抛出点信息, 而没有记录新的抛出点位置. 
    java.lang.Exception
    	at net.mindview.test.WhoCalled.f(WhoCalled.java:14)
    	at net.mindview.test.WhoCalled.g(WhoCalled.java:21)
    	at net.mindview.test.WhoCalled.h(WhoCalled.java:28)
    	at net.mindview.test.WhoCalled.main(WhoCalled.java:36)
  	    ......
    */
    ```
    
    + [因此可以通过fillInStackTrace()方法会将当前调用位置作为新的异常抛出点位置.]() :bangbang: 
    ```java
    public class WhoCalled {
        static void f() throws Exception {
            try{
                throw new Exception();
            } catch (Exception e){
                throw e;
            }
        }
        static void g() throws Exception {
            try {
                f();
            } catch (Exception e) {
                throw e;
            }
        }
        static void h() throws Exception {
            try {
                g();
            } catch (Exception e) {
                throw (Exception) e.fillInStackTrace();
            }
        }
    
        public static void main(String[] args) {
            try {
                WhoCalled.h();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
  
    /*Output: 可以明显发现通过fillInStackTrace()方法重新定位了异常的抛出位置. 
    java.lang.Exception
    	at net.mindview.test.WhoCalled.h(WhoCalled.java:30)
    	at net.mindview.test.WhoCalled.main(WhoCalled.java:36)
  	    ......
    */
    ```
    
    + [你也可以通过在重抛异常时, 抛出一个新的异常对象, 效果类似调用 _fillInStackTrace()_ 方法.]()

+ [异常链](): 当捕获一个异常后抛出另一个新的异常, 并希望吧原始异常的信息保存下来时, 这个被称为异常链.  :bangbang:  
jdk1.4 以后所有Throwable的子类在构造器中都可以接受一个cause(因由)对象作为参数, 这个cause就表示原始异常, 这样就将两个异常串联起来从而具备了异常追踪的功能.  
但是在Throwable子类中只有 **Error**, **Exception**, **RuntimeException** 提供了带cause参数的构造器.   
[因此如果要把其他类型的异常链接起来, 应该使用异常类提供的 _initCause()_ 方法而不是构造器.]()  
    ```java
    public class WhoCalled {
        static void f() throws Exception {
            try{
                throw new Exception();
            } catch (Exception e){
                throw (Exception) new Exception("f() exception").initCause(e);
            }
        }
        static void g() throws Exception {
            try {
                f();
            } catch (Exception e) {
                throw (Exception) new Exception("g() exception").initCause(e);
            }
        }
        static void h() throws Exception {
            try {
                g();
            } catch (Exception e) {
                throw (Exception) new Exception("h() exception").initCause(e);
            }
        }
    
        public static void main(String[] args) {
            try {
                WhoCalled.h();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    /*
    java.lang.Exception: h() exception
    	at net.mindview.test.WhoCalled.h(WhoCalled.java:30)
    	at net.mindview.test.WhoCalled.main(WhoCalled.java:36)
    	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
    	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
    	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
    	at java.lang.reflect.Method.invoke(Method.java:498)
    	at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)
    Caused by: java.lang.Exception: g() exception
    	at net.mindview.test.WhoCalled.g(WhoCalled.java:23)
    	at net.mindview.test.WhoCalled.h(WhoCalled.java:28)
    	... 6 more
    Caused by: java.lang.Exception: f() exception
    	at net.mindview.test.WhoCalled.f(WhoCalled.java:16)
    	at net.mindview.test.WhoCalled.g(WhoCalled.java:21)
    	... 7 more
    Caused by: java.lang.Exception
    	at net.mindview.test.WhoCalled.f(WhoCalled.java:14)
    	... 8 more
    */
    ```

####7. _JAVA标准异常_
+ **Throwable** 接口表示任何可以作为异常抛出的类, 可分为两类: 
    + **Error** 类表示编译时和系统错误, 除特殊情况外, 一般这些错不用你关心. 
    + **Exception** 类表示异常基类. 
+ **RuntimeException** 类是从Exception继承而来的一类特殊异常类, 这些类是在运行时抛出, 属于不受检查异常, 因为他们会系统自动捕获, 因此不需要对他们进行手动捕获也无需手动声明. [这类错误一般是指程序bug.]()
    + 无法预料的错误, 比如空指针引用. 
    + 应该由程序员在代码中进行检查的错误, 比如数组下标越界使用等. 
+ [异常丢失问题, 看下面代码的输出结果, 发现 _f()_ 中抛出的异常应该被捕获, 但是finally语句造成个异常的丢失问题, 这个需要注意异常的处理逻辑!!!]() :bangbang:
    ```java
    public class WhoCalled {
        static void f() throws Exception {throw new Exception();}
        static void g() throws Exception {f();}
        static void h() throws Exception {
            try {
                f();
            }finally {
                return;
            }
        }
    
        public static void main(String[] args) {
            try {
                WhoCalled.h();
                System.out.println("can not catch exception from f()");
            } catch (Exception e) {
                System.out.println("catch exception from f()");
            }
        }
    }
  
    /*Output:
    can not catch exception from f()
    */
    ```
    
####8. [_使用finally进行清理_]()
+ 在catch语句后的可选finally语句表示无论异常是否被抛出, 总是执行.   
即使抛出异常无法处理跳到上一层处理, 也会在跳转之前执行finally语句.   
即时使用break或者continue语句是, finally语句一样也会执行.   
[对于有return的语句, finally一样也会在return前执行, 但是通过编译后的信息可以发现finally语句并不是在return语句返回前执行表达式, 两者是同时执行, 只是在return语句提前返回!!]()  
+ finally的用途: 当要把除内存之外的资源恢复到他们的初始状态时, 比如关闭文件或网络连接. 

####9. [_异常限制_]() :bangbang:


####10. _构造器_ 


####11. [_异常匹配_]()


####12. [_其他可选方式_]()

 
####13. [_异常使用指南_]() :bangbang:
应该在下列情况下使用异常: 

+ 1. 在恰当的级别处理问题. (在知道该如何处理的情况下才捕获异常)
+ 2. 解决问题并且重新调用产生异常的方法. 
+ 3. 进行少许修补, 然后绕过异常发生的地方继续执行. 
+ 4. 用别的数据进行计算, 以代替方法预计会返回的值. 
+ 5. 把当前运行环境下能做的事情尽量做完, 然后把_相同_的异常重抛到更高层. 
+ 6. 把当前运行环境下能做的事情尽量做完, 然后吧_不同_的异常抛到更高层. 
+ 7. 终止程序. 
+ 8. 进行简化. (如果你的异常模式使问题变得太复杂, 那用起来会非常痛苦也很烦人)
+ 9. 让类库和程序更加安全. (这既是在为调试做短期投资, 也是在为程序的健壮性做长期投资)




