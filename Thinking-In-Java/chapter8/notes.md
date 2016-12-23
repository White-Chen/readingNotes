###Chapter 8 : Polymorphism
####1. _动态绑定_ :heavy_exclamation_mark:
+ 多态的实现方式就是动态绑定. 
+ 方法绑定的实现方法: 
    + **前期绑定**: 在编译时, 程序运行前, 编译器与链接器将对应的方法进行链接. 这种方法在C语言这种面向过程的语言中比较常见. 
    + **后期绑定/动态绑定**: 绑定过程发生在运行时, 而不再是编译时. 这种绑定方法需要实现一种机制去确定对象的类型, 以此避免在编译时确定类型信息, 而是在运行时确定类型信息. (这个机制应该是值RTTI？ 之前提到过, 后面的章节应该会详细说). 
+ JAVA中除了 **static** 和 **final** 修饰的方法之外, 其余所有的方法都是进行后期绑定的. 需要注意的是 **private** 方法也属于 **final** 方法, 可能原因是对于这两个关键字修饰的方法, 其意义都是不可被子类复写, 因此也就不存在多态. :bangbang:
+ 当声明了一个 **final** 方法, 在编译时相当于告诉编译器关闭动态绑定功能, 这是编译器可能会对代码进行些许的优化. 
+ 通过动态绑定, 可以实现父类指针指向子类目标, 这也就意味着可以通过直接操作父类指针调用子类复写的. 
+ “动态是一种可以将改变事物与未改变事物分离开来的技术”, 也就是如果仅仅只指针指向的对象增加了复写方法或者一些别的操作, 只要操作父类的指针没有发生变化, 即使不编译操作指针相关的类一样可以正常实现多态特性(表述可能不清楚, 意思就是多态是发生在运行时的). 

####2. _缺陷_
+ "覆盖"私有方法: 用 **private** 修饰的方法同样也不能被覆盖, 因此无法实现动态绑定, 但是在这种情况因为方法名称, 返回值类型, 参数列表的相同会导致一种迷惑性. 
    + [这个问题并不是技术上的问题, 编译器并没有编译错误, 只是会影响代码的可读性, 因此应该尽可能避免使用和父类私有方法相同的方法名. ]()
    ```java
    public class PrivateOverride{
      private void f(){System.out.println("private f()");}
      public static void main(String[] args){
          PrivateOverride po = new Derived();
          po.f();
      }
    }
  
    class Derived extends PrivateOverride{
      //@Override 如果用这个注解, 编译时会报错, 因为根本就没有方法可供覆盖
      public void f(){System.out.println("public f()");}
    }
  
    /* Output
      private f()  
    */
    ```
    
+ 成员变量和静态方法: 动态绑定只发生在那些普通的方法调用. 比如: 直接访问某个成员变量, 这个访问在编译时就已经完成. 
    + 看下面输出第一行, 直接获取的i是父类的i值, 而不是子类覆盖方法后应该返回的i值, 这是因为这一切在编译时已经完成, 并不支持运行时的动态绑定. 但是通过来访问则没有问题. 
    + [这个问题主要是因为编写习惯的问题, 因为良好的编程习惯要求我们私有化成员变量, 同时请不是要使用相同名称的公共变量, 以避免歧义. ]()
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
    
####3. _构造方法的调用顺序_
+ 顺序如下: 
    + 以递归的形式, 调用基类构造器, 必须保证当前类的父类有限被调用. (注意父类构造函数被调用时, 会首先进行父类的成员变量初始化)
    + 按照申明顺序初始化成员变量. (当前类)
    + 当前类的构造函数被调用. 
+ 示例如下, 看输出结构可以明显与上面顺序对应起来. 
    ```java
    public class Sandwich extends PortableLunch{
        private static int i;
        private Bread b = new Bread("Sandwich");
        private Cheese c = new Cheese("Sandwich");
        private Lettuce l = new Lettuce("Sandwich");
        public Sandwich(String callMethodName) {
            super(callMethodName);
            System.out.println("Sandwich " + callMethodName + " " + i++);
        }
        public static void main(String[] args) {
            new Sandwich("Sandwich");
        }
    }
    class Meal{
        private static int i;
        Meal(String callMethodName){
            System.out.println("Meal " + callMethodName + " " + i++);
        }
    }
    class Bread{
        private static int i;
        Bread(String callMethodName){
            System.out.println("Bread " + callMethodName + " " + i++);
        }
    }
    class Cheese{
        private static int i;
        Cheese(String callMethodName){
            System.out.println("Cheese " + callMethodName + " " + i++);
        }
    }
    class Lettuce{
        private static int i;
        Lettuce(String callMethodName){
            System.out.println("Lettuce " + callMethodName + " " + i++);
        }
    }
    class Lunch extends Meal{
        private static int i;
        private Bread b = new Bread("Lunch");
        private Cheese c = new Cheese("Lunch");
        private Lettuce l = new Lettuce("Lunch");
        Lunch(String callMethodName){
            super(callMethodName);
            System.out.println("Lunch " + callMethodName + " " + i++);
        }
    }
    class PortableLunch extends Lunch{
        private static int i;
        private Bread b = new Bread("PortableLunch");
        private Cheese c = new Cheese("PortableLunch");
        private Lettuce l = new Lettuce("PortableLunch");
        PortableLunch(String callMethodName){
            super("PortableLunch");
            System.out.println("PortableLunch " + callMethodName + " " + i++);
        }
    }
    /* Output: 调用顺序可以明显看出来 
    Meal PortableLunch 0
    Bread Lunch 0
    Cheese Lunch 0
    Lettuce Lunch 0
    Lunch PortableLunch 0
    Bread PortableLunch 1
    Cheese PortableLunch 1
    Lettuce PortableLunch 1
    PortableLunch Sandwich 0
    Bread Sandwich 2
    Cheese Sandwich 2
    Lettuce Sandwich 2
    Sandwich Sandwich 0 
    */
    ```


####4. _继承类的内存清理_
+ 主要是注意成员变量的清理顺序是按照声明升序的逆序, 父类内存清理方法总是在当前类的清理完最后调用. 
+ 如果对象中存在与其他对象共享的成员变量, 此时内存的清理需要格外的注意, 需要添加一下额外的策略, 保证每个成员变量的清理是安全的. 
    ```java
    public class ReferenceCounting {
        public static void main(String[] args) {
            Shared shared = new Shared();
            Composing[] s = {new Composing(shared),new Composing(shared),new Composing(shared),new Composing(shared),new Composing(shared)};
            for (int i = 0; i < s.length; i++) {
                s[i].dispose();
            }
        }
    }
    
    class Shared{
        private int refcount = 0;
        private static long counter = 0; // 使用long, 避免实际生产中溢出问题
        private final long id = counter ++; // 使用final避免初始化后被改变
        public Shared(){
            System.out.println("Creating " + this);
            /*refcount ++;*/
        }
        public void addRef(){
            refcount ++;
        }
        protected void dispose(){
            if(--refcount == 0)
                System.out.println("Dispose " + this);
        }
        @Override
        public String toString() {
            return "Shared "+ id;
        }
    }
    
    class Composing{
        private Shared shared;
        private static long counter = 0;
        private final long id = counter ++;
        public Composing(Shared shared){
            System.out.println("Creating " + this);
            this.shared = shared;
            this.shared.addRef();
        }
        protected void dispose(){
            System.out.println("disposing " + this);
            shared.dispose();
        }
        @Override
        public String toString() {
            return "Composing " + id;
        }
    }
  
    /* Output:
      Creating Shared 0
      Creating Composing 0
      Creating Composing 1
      Creating Composing 2
      Creating Composing 3
      Creating Composing 4
      disposing Composing 0
      disposing Composing 1
      disposing Composing 2
      disposing Composing 3
      disposing Composing 4
      Dispose Shared 0``
    */
    ```

+ 如果在构造方法内计数, 而不是用专门的方法计数, 会出现技术错误问题: :heavy_exclamation_mark:
    ```java
    /* Output: 
     Creating Shared 0
     Creating Composing 0
     Creating Composing 1
     Creating Composing 2
     Creating Composing 3
     Creating Composing 4
     disposing Composing 0
     Dispose Shared 0
     disposing Composing 1
     disposing Composing 2
     disposing Composing 3
     disposing Composing 4
    */
    ```
    
+ 构造方法内部的多态方法调用会导致一种问题: 如果在父类构造方法内调用了被子类覆盖的动态绑定方法, 就要用到该方法被子类覆盖后的定义, 而如果该方法内部试图操作子类某些成员变量, 此时这种调用的效果可能相当难以预料. 因为被覆盖的方法在子类对象被完全构造之前就被调用了(调用发生在父类构造方法中), 这就会造成一些隐藏错误. 通过下面例子可以明显发现在子类成员变量 _length_ 在初始化之前已经被试图获取, 虽然结果并不是我们想要的1或者5, 但是程序运行并没有报错. 这种情况发生的原因是, 在通过关键字 **new** 一个实例对象时, 非配给该对象的内存空间已经全被初始化为二进制0, 这也就保证了初始化不会报错. [针对这样的额问题, 编写构造方法时有一条准则: 用尽可能简单的方法是对象进入正常状态, 并尽可能避免调用其他方法](). :heavy_exclamation_mark:
    ```java
    class Graph{
      void draw(){System.out.println("Graph.draw()");}
      Graph(){
          System.out.println("before Graph draw()");  
          draw();
          System.out.println("after Graph draw()");  
      }
    }
    class Square extends Graph{
      private int length = 1;
      Square(int length){
          this.length = length;
          System.out.println("Square length = " + length);
      }
      @Override
      void draw(){
        System.out.println("Square length = " + length);
      }
    }
    public class Test{
      public static void main(String[] args){
          new Square(5);
      }
    }
  
    /* Output:
    before Graph draw()
    Square length = 0 //注意这里在调用父类构造方法是调用了子类覆盖后的方法draw(), 但是此时子类成员变量length并没有初始化, 因此返回的值既不是1也不是5, 这种调用方法就产生了我们不想得到的结果. 
    after Graph draw()
    Square length = 5
    */
    ```

####5. _协变返回类型_
+ 从SE5开始提供的一种新的特性: 子类中覆盖方法的返回值类型可以是父类对应方法返回值类型的子类. 

####6. _用继承进行设计_
+ 虽然继承看上去很美好, 但是在新建类时首先考虑使用继承技术, 反而会加重设计上的负担, 是的事情变得复杂起来, 更好的选择是首选使用"组合"的方式, 因为"组合"可以动态选择类型, 这样更加灵活. 
+ 书中作者给出了一条通用准则: [用继承表达行为的差异, 并用字段表达状态上的变化](). 
+ 纯继承vs扩展继承: 纯继承更加理想化, 但是想始终扩展继承更多, 不过需要注意的是扩展继承在向上类型转换时会丢失扩展的特性. 
+ JAVA在进行向下类型转化时, 会在运行时进行强制性类型检查, 无论是否进行显式的强转, 如果转换错误则会报 **ClassCastException**. [JAVA在运行时的类型检查用到了一种技术 **runtime type information(RTTI)**]() :heavy_exclamation_mark:, 这个在后面章节会进行详细介绍. 

####7. _设计模式: 状态模式_ :bangbang:
+ 补充: 针对上面的设计准则, 作者提到了一种设计模式: [状态模式](). :bangbang:
    + 用一句话来表述, 状态模式把所研究的对象的行为包装在不同的状态对象里, 每一个状态对象都属于一个抽象状态类的一个子类. 状态模式的意图是让一个对象在其内部状态改变的时候, 其行为也随之改变. 
    + 状态模式所涉及到的角色有: 
        + 环境(Context)角色, 也成上下文: 定义客户端所感兴趣的接口, 并且保留一个具体状态类的实例. 这个具体状态类的实例给出此环境对象的现有状态. 
        + 抽象状态(State)角色: 定义一个接口, 用以封装环境（Context）对象的一个特定的状态所对应的行为. 
        + 具体状态(ConcreteState)角色: 每一个具体状态类都实现了环境（Context）的一个状态所对应的行为. 
    + 使用场景: 考虑一个在线投票系统的应用, 要实现控制同一个用户只能投一票, 如果一个用户反复投票, 而且投票次数超过5次, 则判定为恶意刷票, 要取消该用户投票的资格, 当然同时也要取消他所投的票；如果一个用户的投票次数超过8次, 将进入黑名单, 禁止再登录和使用系统. 要使用状态模式实现, 首先需要把投票过程的各种状态定义出来, 根据以上描述大致分为四种状态: 正常投票、反复投票、恶意刷票、进入黑名单. 然后创建一个投票管理对象（相当于Context）. 
        + 抽象状态类--State
        ```java
        public interface VoteState {
            /**
             * 处理状态对应的行为
             * @param user    投票人
             * @param voteItem    投票项
             * @param voteManager    投票上下文, 用来在实现状态对应的功能处理的时候, 可以回调上下文的数据
             */
            public void vote(String user,String voteItem,VoteManager voteManager);
        }    
        ```
        
        + 具体状态类--ConcreteState--正常投票
        ```java
        public class NormalVoteState implements VoteState {
            @Override
            public void vote(String user, String voteItem, VoteManager voteManager) {
                //正常投票, 记录到投票记录中
                voteManager.getMapVote().put(user, voteItem);
                System.out.println("恭喜投票成功");
            }
        
        }
        ```
        
        + 具体状态类--ConcreteState--重复投票
        ```java
        public class RepeatVoteState implements VoteState {
            @Override
            public void vote(String user, String voteItem, VoteManager voteManager) {
                //重复投票, 暂时不做处理
                System.out.println("请不要重复投票");
            }
        
        }
        ```
        
        + 具体状态类--ConcreteState--恶意刷票
        ```java
        public class SpiteVoteState implements VoteState {
            @Override
            public void vote(String user, String voteItem, VoteManager voteManager) {
                // 恶意投票, 取消用户的投票资格, 并取消投票记录
                String str = voteManager.getMapVote().get(user);
                if(str != null){
                    voteManager.getMapVote().remove(user);
                }
                System.out.println("你有恶意刷屏行为, 取消投票资格");
            }
        
        }
        ```
        
        + 具体状态类--ConcreteState--黑名单
        ```java
        public class BlackVoteState implements VoteState {
            @Override
            public void vote(String user, String voteItem, VoteManager voteManager) {
                //记录黑名单中, 禁止登录系统
                System.out.println("进入黑名单, 将禁止登录和使用本系统");
            }
        
        }
        ```
        
        + 环境类--ConcreteState--Context
        ```java
        public class VoteManager {
            //持有状体处理对象
            private VoteState state = null;
            //记录用户投票的结果, Map<String,String>对应Map<用户名称, 投票的选项>
            private Map<String,String> mapVote = new HashMap<String,String>();
            //记录用户投票次数, Map<String,Integer>对应Map<用户名称, 投票的次数>
            private Map<String,Integer> mapVoteCount = new HashMap<String,Integer>();
            /**
             * 获取用户投票结果的Map
             */
            public Map<String, String> getMapVote() {
                return mapVote;
            }
            /**
             * 投票
             * @param user    投票人
             * @param voteItem    投票的选项
             */
            public void vote(String user,String voteItem){
                //1.为该用户增加投票次数
                //从记录中取出该用户已有的投票次数
                Integer oldVoteCount = mapVoteCount.get(user);
                if(oldVoteCount == null){
                    oldVoteCount = 0;
                }
                oldVoteCount += 1;
                mapVoteCount.put(user, oldVoteCount);
                //2.判断该用户的投票类型, 就相当于判断对应的状态
                //到底是正常投票、重复投票、恶意投票还是上黑名单的状态
                if(oldVoteCount == 1){
                    state = new NormalVoteState();
                }
                else if(oldVoteCount > 1 && oldVoteCount < 5){
                    state = new RepeatVoteState();
                }
                else if(oldVoteCount >= 5 && oldVoteCount <8){
                    state = new SpiteVoteState();
                }
                else if(oldVoteCount > 8){
                    state = new BlackVoteState();
                }
                //然后转调状态对象来进行相应的操作
                state.vote(user, voteItem, this);
            }
        }
        ```
        
        + 测试类
        ```java
        public class Client {
            public static void main(String[] args) {
                VoteManager vm = new VoteManager();
                for(int i=0;i<9;i++){
                    vm.vote("u1","A");
                }
            }
        
        }
        ```
        
        + 输出
        ```java
        /* Output:
        恭喜投票成功
        请不要重复投票
        请不要重复投票
        请不要重复投票
        你有恶意刷屏行为, 取消投票资格
        你有恶意刷屏行为, 取消投票资格
        你有恶意刷屏行为, 取消投票资格
        你有恶意刷屏行为, 取消投票资格
        进入黑名单, 将禁止登录和使用本系统
        */
        ```