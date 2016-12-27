###Chapter 2 : 装配Bean
####0. _装配机制_
+ 本章主要介绍三种主要装配机制
    + 1. 隐式的bean发现机制和自动装配 
    + 2. 在Java中进行显式配置
    + 3. 在XML中进行显式配置
    
####1. [_自动化装配bean_]() :bangbang:
+ Spring从两个角度来实现自动化装配
    + 组件扫描(component scanning): spring会自动发现应用上下文中所创建的bean
    + 自动转配(autowiring): Spring自动满足bean之间的依赖
+ 自动化装配方案在便利性方面, 在三种方案中最为强大. 
+ **第一步**, 创建可被发现的bean
    + 1. 假设有如下接口
    ```java
    public interface CompactDisc {
        void play();
    }
    ```
    
    + 2. 通过[@Component]()注解声明其实现类作为组件类, 并告知Spring为其创建bean
    ```java
    // CompactDisc接口实现类
    @Component //声明当前类是组件, 会被自动扫描
    public class SgtPeppers implements CompactDisc {
    
        private String title = "Sgt. Pepper's Lonely Hearts Club Band";
        private String artist = "The beatles";
    
        public void play() {
            System.out.println("Playing " + title + " by " + artist);
        }
    }
    ```
    
    + 3. 通过Java配置类开启组件扫描(默认不启用). 注解[@ComponentScan]()默认会扫描与配置类相同的包以及其所有子包中, 带有[@Component]()注解的类, 并分为创建一个bean. 
    ```java
    @Configuration // 声明当前类为Spring配置类
    @ComponentScan // 启用组件扫描！！
    public class CDPlayerConfig {
        // 不需要定义类
    }
    ```
    
    + 3. 通过XML启用组件扫描(与上面的Java方案二选一, 一般用上面的方案). 
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--配置启用组件扫描 后面是扫描的根包-->
        <context:component-scan base-package="autoconfig"/>
    </beans>
    ```
    
    + 4. 测试组件扫描是否奏效(这一步用于测试, 实际可以忽略). [@ContextConfiguration(classes=...class)]()注解表明加载对应应用上下文. [@autowired]()注解表示自动装配对应bean. 注意使用[@RunWith()]()注解前需要在maven中为项目添加spring-test包依赖. 
    ```java
    //调用Spring-test中的测试类, 需要在maven中配置spring-test包依赖
    @RunWith(SpringJUnit4ClassRunner.class)
    
    //通过Java配置类, 从而开启组件扫描
    @ContextConfiguration(classes = CDPlayerConfig.class)
    
    //通过xml加载xml, 从而开启组件扫描
    //@ContextConfiguration(locations = "classpath:autoconfig/cfg/CDPlayerConfig.xml")
    public class CDPlayerTest {
    
        //自动装配
        @Autowired
        private CompactDisc cd;
    
        //测试方法
        @Test
        public void cdShouldNotBeNull(){
            //断言, 判断是否为null, 如果为null会throw
            assertNotNull(cd);
            System.out.println("装配成功");
            cd.play();
        }
    }
  
    /*Output:
    装配成功
    Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles
    */
    ```
    
    + 通过上面少量配置就能够用来发现和创建任意数量的bean. 
    
+ **第二步**, 为组件扫描的bean命名. 
    + 在不显示声明时, spring默认将类名的第一个字母变为小写作为bean的ID
    ```java
    @Component //声明当前类是组件, 会被自动扫描
    public class SgtPeppers implements CompactDisc {
      //...
    }
    ```
    
    + 通过[@Component()]()实现
    ```java
    @Component("musicClub") //声明当前类是组件, 会被自动扫描
    public class SgtPeppers implements CompactDisc {
    //...
    }
    ```
    
    + 通过[@Named()]()实现, 这个需要依赖Javax中的依赖注入规范实现, 需要添加包依赖. spring支持将其作为@Component注解的替代方案, 有细微差别, 但绝大多数场景可以替换, 最好还是用@Component
    ```java
    import javax.inject.Named;
    @Named("musicClub") //需要添加上面的包依赖
    public class SgtPeppers implements CompactDisc {
      //...
    }
    ```

+ **第三步**, 在Java配置类中设置组件扫描的基础包. 
    + 指定包位置
    ```java
    @ComponentScan("autoconfig")
    //或者
    @ComponentScan(basePackages="autoconfig")
    ```
    
    + 指定多包位置, 通过数组实现. 
    ```java
    @ComponentScan(basePackages={"autoconfig","otherconfig",})
    ```
    
    + 指定包中包含的类或者接口(更加类型安全), 通过数组. [通过marker interface实现, 这种方式依然能够保持对重构友好的接口引用, 但是可以避免引用任何实际的引用程序代码.]() :heavy_exclamation_mark:
    ```java
    @ComponentScan(basePackageClassed={autoconfig.CDPlayerConfig.class})
    ```

+ **第四步**, 通过为bean添加注解实现自动装配. 
    + 构造器自动装配, 当[@Autowired]()注解添加在构造器上是, 会spring在创建CDPlayer时通过该构造器创建, 且会传入可设置给CompactDisc的类. 
    ```java
    @Component
    public class CDPlayer implements MediaPlayer {
    
        private CompactDisc cd;
    
        @Autowired
        public CDPlayer(CompactDisc cd){this.cd = cd;}
    
        public void play() {
            cd.play();
        }
    }
    ```
    
    + 其他方法中的自动装配, Spring会尝试满足方法参数上所声明的依赖. [required=false表明没有匹配bean时, Spring会让这个bean处于未装配状态, 此时虽然不会报错, 但是调用当前类可能会出现NullPointerException. 如果有多个bean匹配, Spring会抛出异常, 表明没有明确指定要选择哪个bean进行匹配, 这就是自动装配的歧义性. ]
    ```java
    @Autowired(required=false)
    public CDPlayer(CompactDisc cd){this.cd = cd;}
    ```

    + [@Inject注解与@Autowired的关系, 类似@Named注解与@Component注解的关系. 都可以使用. ]()

+ **第五步**, 验证自动装配. 
    ```java
    import org.junit.Rule;
    import org.junit.Test;
    import org.junit.contrib.java.lang.system.StandardOutputStreamLog;
    import org.junit.runner.RunWith;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.test.context.ContextConfiguration;
    import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
    import static org.junit.Assert.assertEquals;
    import static org.junit.Assert.assertNotNull;
    
    //调用Spring-test中的测试类, 需要在maven中配置spring-test包依赖
    @RunWith(SpringJUnit4ClassRunner.class)
    
    //通过Java配置类, 从而开启组件扫描
    @ContextConfiguration(classes = CDPlayerConfig.class)
    
    //通过xml加载xml, 从而开启组件扫描
    //@ContextConfiguration(locations = "classpath:autoconfig/cfg/CDPlayerConfig.xml")
    public class CDPlayerTest {
    
        //来源于System Rules库的JUnit规则, 该规则能够基于控制台的输出编写断言
        /* 需要添加如下依赖, 否则无法使用
        <dependency>
            <groupId>com.github.stefanbirkner</groupId>
            <artifactId>system-rules</artifactId>
            <version>1.16.1</version>
        </dependency>
        */
        @Rule
        public final StandardOutputStreamLog log =
                new StandardOutputStreamLog();
    
        //自动装配cd
        @Autowired
        private CompactDisc cd;
    
        //自动装配player
        @Autowired
        private MediaPlayer player;
    
        //测试方法
        @Test
        public void cdShouldNotBeNull(){
            assertNotNull(cd);
        }
    
        @Test
        public void play(){
            player.play();
            //注意因为java的println换行符是\r\n, 需要统一, 否则会判断不等
            assertEquals(
                    "Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles\r\n",
                    log.getLog()
            );
        }
    }
  
    /* Output: 如果不等会报错, 正常输出表示相等. 
    Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles
    */
    ```
    
####2. [_通过Java代码转配bean_]() :heavy_exclamation_mark:
+ 尽管自动配置在很多情况下是很好的. 但是有的时候, 自动配置不能满足要求, 这个时候就需要进行显式的配置. 比如, 你需要从第三库里面装配Bean, 因为你没有权利修改源码, 所以你就没有机会在类里面添加@AutoWired注解或者@Component注解, 在这种情况下, 你必须使用显式配置. 
+ **第一步**, 创建一个Java的配置类
    + @Configuration表明该类是一个Spring配置类
    ```java
    import org.springframework.context.annotation.Configuration;
    @Configuration
    public class CDPlayerConfig {
    }
    ```
    
+ **第二步**, 声明简单的Bean
    + 在JavaConfig中, 为了声明一个Bean, 你需要创建一个方法, 该方法返回你需要的Bean的类型. 然后使用@Bean来注解该方法. [@Bean注解告诉Spring, 该方法是用来产生Bean的方法, 并且将该Bean注入到Spring上下文中去. 方法的主体就是返回你希望的Bean类型的实体. 默认情况下, Bean的ID跟@Component得到的ID是一样的. 如果需要重新定义, 指明@Bean注解的name属性就可以了.]()
    ```java
    @Configuration // 声明当前类为Spring配置类
    public class CDPlayerConfig {
        @Bean("musicClubBand")
        public CompactDisc sgtPeppers() {
            return new SgtPeppers();
        }
          
    }
    ```
+ **第三步**, 使用JavaConfig进行注入
    + 使用Java注入, 只需要调用使用@Bean注解的方法即可. [看起来好像CDPlayer()是调用了sgtPeppers()方法, 但是其实不是. 它只是调用了sgtPeppers方法产生的Bean, 并没有重新调用sgtPeppers方法. Sprig会拦截所有对它的调用并确保直接返回该方法所创建的bean.]()
    ```java
    @Configuration // 声明当前类为Spring配置类
    public class CDPlayerConfig {
        @Bean
        public CompactDisc sgtPeppers() {
            return new SgtPeppers();
        }
        @Bean
        public CDPlayer cdPlayer(){
            return new CDPlayer(sgtPeppers());
        }
    }
    ```
    
    + 另一种注入方式: 直接使用参数提供, Spring会获取该参数需要的Bean, 然后注入进去. 当然, 通过这种方法注入之后, 在函数体中, 你可以随便使用这个注入的Bean. [这里使用了设置器注入的模式](). 再一次说明, @Bean注解的方法体中可以使用任意有效的java表达式, 并不是只有构造器注入和设置器注入这两种方法, 也可以使用其他的方法. 比如: 
    ```java
    @Bean
    public CDPlayer cdPlayer(CompactDisc compactDisc) {
        CDPlayer cdPlayer = new CDPlayer(compactDisc);
        cdPlayer.setCompactDisc(compactDisc);
        return cdPlayer;
    }
    ```

+ **第四步**, 验证Java装配. 这里和上面自动装配的方式想相同, 需要注意的是要提前将CDPlayer类中@Autowired注解都删除, 以此证明上述操作是起作用的. 
    ```java
    /*Output: 没有报错表明通过
    Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles
    */
    ```

####3. _通过XML装配bean_
+ **现在Spring强烈推荐使用自动配置和基于Java的配置, XMLConfig应该不是你的第一选择. 新开发的项目中, 尽量使用自动发现和基于Java的配置**. 
+ **第一步**, 创建一个基本的XMLConfig. IDE提供这个功能. 
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
            <beans xmlns="http://www.springframework.org/schema/beans"
                   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                   xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    </beans>
    ```
        
+ **第二步**, 声明一个简单的Bean
    + 如果缺少明确的身份认证id, Spring会根据该类的完全名称设定该Bean的名称, 为xmlconfig.SgtPeppers#0, #0代表这是该类的第一个Bean, 如果在其他地方声明了该类的另外一个Bean, 则名称为soundsystem.SgtPeppers#1, 后面的数据为枚举, 一次枚举下去. 虽然方便, 但是这种自动产生的名称并没有多大的用途, 所以一般情况我们都需要为Bean指定一个显式的名称. 
        ```xml
        <!--声明bean, 如果没有显式声明id, 默认id=xmlconfig.SgtPeppers#0-->
        <bean id="compactDisc" class="xmlconfig.SgtPeppers" />
        ```
        
        + 缺点: 
            + 它也没有JavaConfig那样, 对Bean进行各种操作的能力. 
            + Bean声明使用的class是使用的字符串形式, Spring的XML配置并没有提供编译时检验.
             
+ **第三步**, 构造器注入的方式初始化一个Bean. 
    + 提供两种配置方案: <constructor-arg>元素配置和c-namespace配置. [前者比后者代码片段少但是难以读懂. 另一方面, 前者能做一些后者不能做的事情.]()
    + 以构造器注入为例, 下面分别对应<constructor-arg>元素配置和c-namespace配置方案. [可以发现c-namespace除了代码简洁外, 配置起来相对另一个复杂很多, 需要使用占位符来避免名称发生变化.]()
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:c="http://www.springframework.org/schema/c"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--声明bean-->
        <bean id="compactDisc" class="xmlconfig.SgtPeppers" />
    
        <!--构造器注入, constructor-arg元素注入方式-->
        <bean id="cdPlayer" class="xmlconfig.CDPlayer">
            <constructor-arg ref="compactDisc" />
        </bean>
    
        <!--构造器注入, c-namespace元素注入方式, 使用它必须在xml顶部声明-->
        <!--这个属性名字以命名空间c:开始, 接下来就是需要注入的参数的名字, 
            -ref告诉Spring, 后面是一个Bean的引用而不是一个String的字面值. 
            使用c-namespace比使用元素更加简洁, 这是我希望它的一个原因. 但是我感觉并不容易读懂. -->
        <!---->
        <bean id="cdPlayer2" class="xmlconfig.CDPlayer" c:cd-ref="compactDisc">
        </bean>
      
        <!--直接使用的是参数的名字, 当调试的时候, 参数名字变化了, 会导致有问题. 
            但是Spring提供了一种解决方案, 通过使用参数的索引来命名属性的名字-->
        <bean id="cdPlayer3" class="xmlconfig.CDPlayer" c:_0-ref="compactDisc">
        </bean>
    </beans>
    ```
        
    + 上面是引用bean注入, 如果直接注入字面值的话应使用下面方式. 区别不大, 主要是标签不一样, [需要注意的是书上第二种方式写的有问题, 我自己在IDE里照着书写会报错, 下面是正确的写法, 中文翻译书太坑....]() :bangbang:
    ```xml
    <!--使用元素声明构造函数的注入, 但是这里元素的值不再使用ref了, 而是使用的value, 
            也就是说, ref代表的是其他Bean的引用, 而使用value,代表后面跟的是一个值, 而不是引用了. 
            -->
        <bean id="compactDisc2"
              class="xmlconfig.BlankDisc">
            <constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" />
            <constructor-arg value="The Beatles" />
        </bean>
    
        <!--使用c-namespace的方式 参数名, 这里书上写的有问题, 两个参数名都多了一个_会报错-->
        <bean id="compactDisc3" class="xmlconfig.BlankDisc" c:title="Sgt. Pepper's Lonely Hearts Club Band" c:artist="The Beatles">
        </bean>
    
        <!--使用c-namespace的方式 参数索引-->
        <bean id="compactDisc3" class="xmlconfig.BlankDisc" c:_0="Sgt. Pepper's Lonely Hearts Club Band" c:_1="The Beatles">
        </bean>
    ```
    
    + [集合注入. 需要注意的是c-namespace不支持集合注入]:bangbang:
    ```xml
    <!--注意c-namespace不支持集合注入-->
        <!--集合注入, 这里注入的是给定值-->
        <bean id="compactDisc4" class="xmlconfig.collections.BlankDisc">
            <constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" />
            <constructor-arg value="The Beatles" />
            <constructor-arg>
                <list>
                    <value>Sgt. Pepper's Lonely Hearts Club Band</value>
                    <value>With a Little Help from My Friends</value>
                    <value>Lucy in the Sky with Diamonds</value>
                    <value>Getting Better</value>
                    <value>Fixing a Hole</value>
                    <!-- ...other tracks omitted for brevity... -->
                </list>
            </constructor-arg>
        </bean>
    
        <!--集合注入, 这里注入的是引用id-->
        <bean id="sgtPeppers" class="java.lang.String" c:_0="sgtPeppers"/>
        <bean id="whiteAlbum" class="java.lang.String" c:_0="whiteAlbum"/>
        <bean id="hardDaysNight" class="java.lang.String" c:_0="hardDaysNight"/>
        <bean id="compactDisc5" class="xmlconfig.collections.BlankDisc">
            <constructor-arg value="Sgt. Pepper's Lonely Hearts Club Band" />
            <constructor-arg value="The Beatles" />
            <constructor-arg>
                <list>
                    <ref bean="sgtPeppers" />
                    <ref bean="whiteAlbum" />
                    <ref bean="hardDaysNight" />
                    <!-- ...other tracks omitted for brevity... -->
                </>
            </constructor-arg>
        </bean>
    ```
    
+ **第四步**, 设置属性. 
    + 假设有如下类, 如果不设置compactDisc, 那么在调用play()时会发生空指针异常. 
    ```java
    public class CDPlayer implements MediaPlayer {
      private CompactDisc compactDisc;
    
      @Autowired
      public void setCompactDisc(CompactDisc compactDisc) {
        this.compactDisc = compactDisc;
      }
    
      public void play() {
        compactDisc.play();
      }
    }
    ```
    
    + 下面介绍几种用法, 具体对应看注释. [下面的p-namespace用法和上面的c-namespace用法一样, 只是用途不一样.]()
    ```xml
    <!--属性注入 使用property标签-->
        <bean id="cdPlayer4"
              class="xmlconfig.properties.CDPlayer">
            <property name="compactDisc" ref="compactDisc" />
        </bean>
    
        <!--属性注入 使用p-namespace标签-->
        <bean id="cdPlayer5"
              class="xmlconfig.properties.CDPlayer"
              p:compactDisc-ref="compactDisc2" />
    
        <!--集合类属性注入 使用property标签-->
        <bean id="compactDisc6"
              class="xmlconfig.properties.BlankDisc">
            <property name="title"
                      value="Sgt. Pepper's Lonely Hearts Club Band" />
            <property name="artist" value="The Beatles" />
            <property name="tracks">
                <list>
                    <value>Sgt. Pepper's Lonely Hearts Club Band</value>
                    <value>With a Little Help from My Friends</value>
                    <value>Lucy in the Sky with Diamonds</value>
                    <value>Getting Better</value>
                    <value>Fixing a Hole</value>
                    <!-- ...other tracks omitted for brevity... -->
                </list>
            </property>
        </bean>
    
        <!--使用Spring提供的util-namespace去简化list的声明, 首先引入命名空间-->
        <util:list id="trackList">
            <value>Sgt. Pepper's Lonely Hearts Club Band</value>
            <value>With a Little Help from My Friends</value>
            <value>Lucy in the Sky with Diamonds</value>
            <value>Getting Better</value>
            <value>Fixing a Hole</value>
            <!-- ...other tracks omitted for brevity... -->
        </util:list>
        <bean id="compactDisc7"
              class="xmlconfig.properties.BlankDisc"
              p:title="Sgt. Pepper's Lonely Hearts Club Band"
              p:artist="The Beatles"
              p:tracks-ref="trackList"/>
    ```

####4. _导入和混合配置_
+ 自动装配会考虑到Spring容器中的所有Bean, 不管这些Bean是来自JavaConfig、XmlConfig还是Component-Scan. 在显示配置中的时候, 你怎么去引用JavaConfig或者XmlConfig. 
+ **背景**: 假设到一定时候, 配置类或者配置XML变得臃肿, 你希望去将它们分开描述. 
+ 首先对于JavaConfig. [有两种方式, 方式一: 在另一个配置类中@Import. 方式二: 新建一个顶层配置类, 然后同时导入两个分离出来的配置类.]()
+ 其次对于XML. 我们可以使用import resource的方式进行导入, 思路和JavaConfig一样. 
+ **背景2**: 假设配置类被分到了不同格式的配置文件中, 如何对他们进行合并. 
+ [**在JavaConfig中引用XmlConfig**](): 
    ```java
    @Configuration
    @Import({Config_1.class})
    @ImportResource({"classpath:config_2.xml"})
    public class SystemConfig {
    }
    ```
    
+ [**在XML配置中引用JavaConfig**](): 
    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:c="http://www.springframework.org/schema/c"
    xsi:schemaLocation="http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">
    
        <bean class="xmlconfig.Config_1" />
    
        <import resource="config_2.xml" />
    
    </beans>
    ```

