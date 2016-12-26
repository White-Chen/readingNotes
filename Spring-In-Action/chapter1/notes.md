###Chapter 1 : Spring之旅
####1. _Spring简化开发的4种关键策略_
+ 基于POJO的轻量级和最小侵入性编程. 
+ 通过依赖注入和面向接口实现松耦合. 
+ 基于切面和惯例进行声明式编程. 
+ 通过切面和模板减少样板式代码. 

####2. _maven项目添加SpringFramework依赖_
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <!--版本号自己根据需要选择, 也可以写成动态获取当前版本号-->
        <version>4.3.5.RELEASE</version> 
    </dependency>
</dependencies>
```

####3. _POJO_
+ POJO的全称是: Plain Ordinary Java Object, 意为简单的JAVA对象, 实际就是普通的JavaBeans. 
+ JavaBean vs POJO vs EJB. :heavy_exclamation_mark:
    + POJO:有一些private的参数作为对象的属性, 然后针对每一个参数定义get和set方法访问的接口. 没有从任何类继承、也没有实现任何接口, 更没有被其它框架侵入的java对象. 
    + JavaBean:是一种编写JAvA类的规范. 
        + 所有属性为private. 
        + 这个类必须有一个公共的缺省构造函数. 即是提供无参数的构造器. 
        + 这个类的属性使用getter和setter来访问, 其他方法遵从标准命名规范. 
        + 这个类应是可序列化的. 实现serializable接口.  
    + EJB:一种javabean的组合规范, 通过一组"功能"JavaBean的集合来实现某个企业组的业务逻辑. 

####4. _依赖注入/Dependency Injection_
+ 任何有实际意义的应用都是由两个或更多地类组成的, 它们之间相互协作来完成特定的业务逻辑. 通常, 每个对象负责管理和自己协作的对象（它依赖的对象）引用, 这就难免导致了高度耦合和难以测试. 
+ 例如下面的DamselRescuingKnight类, 在构造函数中自行创建了RescueDamselQuest, 这使得二者耦合在一起；更糟糕的是, 为这个DamselRescuingKnight编写单元测试将出奇的困难. 在这样的测试中, 你必须保证当embarkOnQuest方法被调用时, embrak方法也被调用, 但是没有一个简明的方式能够实现这点. 
    ```java
    //骑士类接口
    interface Knight {
        //响应请求
        void embarkOnQuest();
    }
    
    //请求类接口
    interface Quest {
        //解决
        void embark();
    }
    //请求接口具体实现类
    public class RescueDamselQuest implements Quest {
    
        @Override
        public void embark() {
            System.out.println("Embarking on a quest to rescue the damsel.");
        }
    }
    //骑士接口具体实现类
    public class DamselRescuingKnight implements Knight {
        private RescueDamselQuest quest;
      
        public DsmselRescuingKnight() {
            quest = new RescueDamselQuest();
        }
      
        public void embarkOnQuest() throws QuestException {
            quest.embrak();
        }
    }
    ```

+ **耦合的两面性**: 一方面耦合的代码难以测试、复用和理解；另一方面, 一定的耦合又是必须的, 为了完成特定功能不同的类必须以适当的方式进行交互. 
+ **控制反转(IOC), 也叫依赖注入(DI)**:将各层的对象以松耦合的方式组织在一起, 解耦, 各层对象的调用完全面向接口. 当系统重构的时候, 代码的改写量将大大减少. 通过依赖注入, 对象的依赖关系将由负责协调系统中各个对象的第三方组件在创建对象时设定. 对象无需创建和管理它们的依赖关系——依赖关系会被自动注入到需要它们的对象中去. 
+ 如下面代码, BraveKnight并没有自行创建Quest, 而是在构造函数时把探险任务作为构造器参数传入, 这是依赖注入的一种方式 **构造器注入**: 
    ```java
    public class BraveKnight implements Knight {
    
        private Quest quest;
    
        public BraveKnight(Quest quest){
            this.quest = quest;
        }
    
        @Override
        public void embarkOnQuest() {
            quest.embark();
        }
    }
    ```

+ 对依赖进行替换的最常用方法之一, 就是测试的时候使用mock实现. 你无法充分测试DamselRescuingKnight, 因为它是紧耦合的, 但可以轻松测试BraveKnight, 只需要给它提供一个Quest的mock实现. 
+ [mock测试就是在测试过程中, 对那些不容易构建的对象用一个虚拟对象来代替测试的方法就叫mock测试. 比较常用的有Mockito]():bangbang:. 如下面代码, 通过现有的mock对象, 你可以创建一个新的BraveKnight实例, 通过构造器注入mock Quest. 当调用embarkOnQuest()方法时, 你可以要求Mockito框架验证Quest的mock实现的embrack方法仅仅被调用了一次. 
    ```java
    public class BraveKnightTest {
    
        @Test
        public void knightShouldEmbarkOnQuest(){
            // static导入
            //创建 mock Quest
            Quest mockQuest = mock(Quest.class);
            //注入 mock Quest
            BraveKnight knight = new BraveKnight(mockQuest);
            knight.embarkOnQuest();
            //验证方法被调用的次数为 1
            verify(mockQuest,times(1)).embark();
        }
    }
    ```

+ 如上, 虽然通过 **构造器注入** 以及接口实现了低耦合, 但是当我们实现了如下一个新的SlayDragonQuest类时, 我们应该如何在BraveKnight不知情的情况下传入? 需要用到**bean装配**的概念. 
    ```java
    public class SlayDragonQuest implements Quest {
    
        private PrintStream stream;
    
        public SlayDragonQuest(PrintStream stream){this.stream = stream;}
    
        @Override
        public void embark() {
            stream.println("Embarking on quest to slay the dragon!");
        }
    }
    ```
    
+ 装配(wiring):创建应用组件之间协作的行为. Spring提供多种装配bean的方式, 比如下面的两种
    + 基于XML装配: 下面的语法在第二章介绍, 注意xsi哪一行, 表示对springframework的xml语法支持
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <!--注入Quest bean-->
        <bean id = "knight" class="BraveKnight">
            <constructor-arg ref="quest"/>
        </bean>
    
        <!--创建SlayDragonQuest实例-->
        <bean id="quest" class="SlayDragonQuest">
            <constructor-arg value="#{T(System).out}"/>
        </bean>
    
    </beans>
    ```
    
    + 基于Java装配: 基本上是基于Annotation实现的, 注意需要导包
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    /**
     * Created by ChenZhePC on 2016/12/26.
     */
    @Configuration //使用注解
    public class KnightConfig {
    
        @Bean //使用Bean注解
        public Knight knight(){
            return new BraveKnight(quest());
        }
    
        @Bean
        public Quest quest() {
            return new SlayDragonQuest(System.out);
        }
    }
    ```
    
+ Spring通过诸如上面的方式进行装配, 从而**实现在不改变所依赖的类的情况下, 修改依赖关系**. 
+ 在配置完类之间的依赖关系后, Spring通过 **Application Context** 读取配置装载bean的定义并把它们组装起来. **Application Context** 全权**负责对象的创建和组装**. 
+ 下面是两种分别对应上面两种装配方式的bean装载方式: :heavy_exclamation_mark:
    + 基于xml配置的上下文装载方式: 
    ```java
    import org.springframework.context.support.ClassPathXmlApplicationContext;
    /**
     * Created by ChenZhePC on 2016/12/26.
     */
    public class KnightMain {
      
        // Application Context 加载xml配置
        public static void main(String[] args) {
            ClassPathXmlApplicationContext context =
                    new ClassPathXmlApplicationContext(
                            "META-INF/spring/knights.xml");
            Knight knight = context.getBean(Knight.class);
            knight.embarkOnQuest();
            context.close();
        }
    }
  
    /* Output:
    十二月 26, 2016 12:07:06 下午 org.springframework.context.support.ClassPathXmlApplicationContext prepareRefresh
    信息: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@238e0d81: startup date [Mon Dec 26 12:07:05 CST 2016]; root of context hierarchy
    十二月 26, 2016 12:07:07 下午 org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
    信息: Loading XML bean definitions from class path resource [META-INF/spring/knights.xml]
    Embarking on quest to slay the dragon!
    十二月 26, 2016 12:07:08 下午 org.springframework.context.support.ClassPathXmlApplicationContext doClose
    信息: Closing org.springframework.context.support.ClassPathXmlApplicationContext@238e0d81: startup date [Mon Dec 26 12:07:05 CST 2016]; root of context hierarchy
    */
    ```
    
    + 基于Java配置的上下文装载方式: [这种方式运行起来好像比xml方式慢, 个人感觉]()
    ```java
    import org.springframework.context.annotation.AnnotationConfigApplicationContext;
    /**
     * Created by ChenZhePC on 2016/12/26.
     */
    public class KnightMain {
    
        public static void main(String[] args) {
            // Application Context 加载Java配置
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(KnightConfig.class);
            Knight knight = context.getBean(Knight.class);
            knight.embarkOnQuest();
            context.close();
        }
    }
    /* Output:
    十二月 26, 2016 2:45:21 下午 org.springframework.context.annotation.AnnotationConfigApplicationContext prepareRefresh
    信息: Refreshing org.springframework.context.annotation.AnnotationConfigApplicationContext@49c2faae: startup date [Mon Dec 26 14:45:21 CST 2016]; root of context hierarchy
    Embarking on quest to slay the dragon!
    十二月 26, 2016 2:45:21 下午 org.springframework.context.annotation.AnnotationConfigApplicationContext doClose
    信息: Closing org.springframework.context.annotation.AnnotationConfigApplicationContext@49c2faae: startup date [Mon Dec 26 14:45:21 CST 2016]; root of context hierarchy
    */
    ```
    
####5. _面向切面编程/aspect-oriented programming_
+ DI能够让相互依赖的组件保持松散耦合, 面向切面变成(AOP)允许你把通用的功能分离出来形成可重用的组件. 
+ 除了实现自身核心的功能之外, 有些组件还经常承担着额外的职责. 注入日志, 事物管理和安全这样的系统服务经常融入到自身具有核心业务逻辑的组件中去, 这些系统级服务通常被称为**横切关注点/aspect points**, 因为他们会跨域系统的多个组件. [按自己理解, 一般是业务无关的更加通用的系统级服务.]()
+ 如果把关注点分散到多个组件会造成双重复杂: 
    + 方法的调用重复出现在各个模块中, 且一旦调用逻辑发生变化, 所有调用的地方都需要调整. 
    + 组件会因为那些与核心业务逻辑无关的代码而变得混乱. 
+ 比如你现在有一个Minstrel类, 可以吟唱. 
    ```java
    import java.io.PrintStream;
    public class Minstrel {
        private PrintStream stream;
        public Minstrel(PrintStream stream){
            this.stream = stream;
        }
        // 发生在knight响应quest前
        public void singBeforeQuest(){
            stream.println("Fa la la, the knight is so brave!");
        }
        // 发生在knight响应quest后
        public void singAfterQuest(){
            stream.println("Do la mi fa so, the brave knight " +
                "did embark on a quest!");
        }
    }
    ```
    
+ 现在你需要在BraveKnight响应请求前后实现吟唱, 最简单的做法如下: 
    ```java
    public class BraveKnight implements Knight {
    
        private Quest quest;
        private Minstrel minstrel;
    
        public BraveKnight(Quest quest, Minstrel minstrel){
            this.quest = quest;
            this.minstrel = minstrel;
        }
    
        @Override
        public void embarkOnQuest() {
            minstrel.singBeforeQuest();
            quest.embark();
            minstrel.singAfterQuest();
        }
    }
    ```
    
+ 很显然, 这种做法是有很多缺点的: 
    + BraveKnight类不应该负责Minstrel类的管理和方法调用, 但事实上它是这么做的. 
    + BraveKnight类因为需要需要调用Minstrel类方法, 所以要注入Minstrel类. 这使得我们需要改写构造函数, 并且额外增加一些验证, 增加了程序的复杂性. 
    + 需要改写embarkOnQuest方法, 增强了方法和类之间的耦合性. 
+ 那么, AOP可以帮助我们解决上述问题, 下面通过将Minstrel类声明为一个切面, 并实现上面代码的功能. :heavy_exclamation_mark:
    + [\<aop:config\> 配置AOP标签]()
    + [\<aop:aspect\> 配置切面类]()
    + [\<aop:pointcut\> 配置切入点]()
    + [\<aop:before\> 声明在切入点之前切面类需要进行的操作]()
    + [\<aop:after\> 声明在切入点之后切面类需要进行的操作]()
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
    
        <!--注入Quest bean-->
        <bean id = "knight" class="BraveKnight">
            <constructor-arg ref="quest"/>
        </bean>
    
        <!--创建SlayDragonQuest实例-->
        <bean id="quest" class="SlayDragonQuest">
            <constructor-arg value="#{T(System).out}"/>
        </bean>
    
        <!--创建Minstrel实例-->
        <bean id="minstrel" class="Minstrel">
            <constructor-arg value="#{T(System).out}"/>
        </bean>
    
        <!--AOP配置-->
        <aop:config>
            <!--把minstrel声明为一个切面-->
            <aop:aspect ref="minstrel">
                <!--切面关注点, 也就是BraveKnight类的embarkOnQuest方法被调用时-->
                <aop:pointcut id="embark"
                              expression="execution(* *.embarkOnQuest(..))"/>
    
                <!--在切面关注点之前执行-->
                <aop:before pointcut-ref="embark"
                            method="singBeforeQuest"/>
    
                <!--在切面关注点之后执行-->
                <aop:after  pointcut-ref="embark"
                            method="singAfterQuest"/>
            </aop:aspect>
        </aop:config>
    </beans>
    ```
    
+ [注意需要在xmlns中声明aop, 这些IDE回自动化, 此外因为用AspectJ的语法支持(后面章节会介绍), 需要提前在maven中导入两个包, 不然会报错!!.]() :bangbang:
    ```xml
    ....
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjrt</artifactId>
        <version>1.5.4</version>
    </dependency>
    <dependency>
        <groupId>org.aspectj</groupId>
        <artifactId>aspectjweaver</artifactId>
        <version>1.8.10</version>
        <scope>runtime</scope>
    </dependency>
    ....
    ```

+ 通过修改上面的xml配置, 可以在不需要修改BraveKnight的情况下, 做到面向切面编程, 输出效果如下: 
    ```java
    /* Output:
    十二月 26, 2016 4:07:58 下午 org.springframework.context.support.ClassPathXmlApplicationContext prepareRefresh
    信息: Refreshing org.springframework.context.support.ClassPathXmlApplicationContext@21b8d17c: startup date [Mon Dec 26 16:07:58 CST 2016]; root of context hierarchy
    十二月 26, 2016 4:08:00 下午 org.springframework.beans.factory.xml.XmlBeanDefinitionReader loadBeanDefinitions
    信息: Loading XML bean definitions from class path resource [META-INF/spring/knights.xml]
    Fa la la, the knight is so brave!
    Embarking on quest to slay the dragon!
    Do la mi fa so, the brave knight did embark on a quest!
    十二月 26, 2016 4:08:03 下午 org.springframework.context.support.ClassPathXmlApplicationContext doClose
    信息: Closing org.springframework.context.support.ClassPathXmlApplicationContext@21b8d17c: startup date [Mon Dec 26 16:07:58 CST 2016]; root of context hierarchy
    */
    ```
    
####6. _使用模板消除样板式代码_ :bangbang:
+ 通过模板类减少重复写样式式的代码. 比如通过JDBC访问数据库查询数据, 你总是需要重复写获取connneciton实例, try-catch等一样的代码. 
+ Spring提供了许多模板类, 从而简化了这些不必要的重复劳动. 比如基于SE5的JdbcTemplate实现的JdbcTemplate, 这样只需要关注于核心逻辑, 而不需要迎合JDBC API需求. 
+ 详细的介绍会在后面章节介绍. 

####7. _bean的声明周期_ :bangabng:
+ 在传统JAVA应用中bean的生命周期: 
    + 1 new实例化
    + 2 使用实例化的bean
    + 3 GC根据条件自动回收bean实例
+ 在Spring中, bean的生命周期由Spring容器负责, 它是Spring框架的核心, Spring自带的容器分为两类: 
    + [bean工厂(org.springframework.beans.factory.BeanFactory 接口)](): 提供DI支持, 但是对于大多数应用太低级, 所以应用上下文更受欢迎, 书里主要使用后者. 
    + [应用上下文(org.springframework.context.ApplicationContext 接口)](): 基于bean工厂构建, 提供框架级别的服务. Spring自带的应用上下文最常用的有下面5个: 
        + AnnotationConfigApplicationContext: 从一个或多个基于Java的配置类中加载Spring应用上下文. 
        + AnnotationConfigWebApplicationContext: 从一个或多个基于Java的配置类中加载Spring Web应用上下文. 
        + ClassPathXmlApplicationContext: 从类路径下的一个或多个XML配置文件中加载上下文定义, 把应用上下文的定义文件作为类资源. 
        + FileSystemXmlApplicationContext: 从文件系统下的一个或多个XML配置文件中加载上下文定义. [和上面的区别在于, 一个是从文件系统中按路径查找, 一个是从所有类路径下查找.]()
        + XmlWebApplicationContext: 从Web应用下的一个或多个XML配置文件中加载上下文定义. 

+ 在上面的这些Spring容器中, bean的生命周期比传统JAVA应用中的生命周期复杂的多, 详情如下:  :bangbang:
    + 1. Spring对bean进行实例化. 
    + 2. Spring将值和bean的引用注入到bean对应的属性中. 
    + 3. 如果bean实现了BeanNameAware接口, Spring将bean的ID传递给setBeanName()方法. 
    + 4. 如果bean实现了BeanFactoryAware接口, Spring将调用setBeanFactory()方法, 将BeanFactory容器实例传入. 
    + 5. 如果bean实现了ApplicationContextAware接口, Spring将调用setApplicationContext()方法, 将bean所在的应用上下文的引用传入进来. 
    + 6. 如果bean实现了BeanPostProcessor接口, Spring将调用它们的postProcessBeforeInitialization()方法. 
    + 7. 如果bean实现了InitializingBean接口, Spring将调用他们的afterPropertiesSet()方法. 类似地, 如果bean使用init-method声明了初始化方法, 该方法也会被调用. 
    + 8. 如果bean实现了BeanPostProcessor接口, Spring将调用它们的postProcessAfterInitialization()方法. 
    + [9. 此时, bean已经准备就绪, 可以被应用程序使用, 它们将一直驻留在应用上下文中, 直到该上下文被销毁.]()
    + 10. 如果bean实现了DisposableBean接口, Spring将调用它的destroy()接口方法, 直到该应用上下文被销毁. 
    + 11. 如果bean实现了自定义的destroy方法, 则会被调用. 
    
####7. _Spring模块_
+ 核心: spring-core, spring-beans, spring-context, spring-expression, spring-context-support. 
+ 测试: spring-test. 
+ 数据访问与集成: spring-jdbc, spring-orm, spring-jms, spring-messaging, spring-oxm, spring-transaction. 
+ Web与远程调用: spring-web, spring-webmvc, spring-webmvc-portlet, ``spring-websocket. 
+ Instrumentation: 这个没听过. . . 

####8. _Spring4.0新特性_
+ 详情见书P30, 不一一列举. 因为之前也没有特别的深入的使用过，所以目前对这些新的特性没有什么强烈的感觉。