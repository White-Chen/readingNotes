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
+ 自动化装配方案在便利性方面，在三种方案中最为强大。
+ **第一步**，创建可被发现的bean
    + 1. 假设有如下接口
    ```java
    public interface CompactDisc {
        void play();
    }
    ```
    
    + 2. 通过[@Component]()注解声明其实现类作为组件类，并告知Spring为其创建bean
    ```java
    // CompactDisc接口实现类
    @Component //声明当前类是组件，会被自动扫描
    public class SgtPeppers implements CompactDisc {
    
        private String title = "Sgt. Pepper's Lonely Hearts Club Band";
        private String artist = "The beatles";
    
        public void play() {
            System.out.println("Playing " + title + " by " + artist);
        }
    }
    ```
    
    + 3. 通过Java配置类开启组件扫描(默认不启用)。注解[@ComponentScan]()默认会扫描与配置类相同的包以及其所有子包中，带有[@Component]()注解的类，并分为创建一个bean。
    ```java
    @Configuration // 声明当前类为Spring配置类
    @ComponentScan // 启用组件扫描！！
    public class CDPlayerConfig {
        // 不需要定义类
    }
    ```
    
    + 3. 通过XML启用组件扫描(与上面的Java方案二选一，一般用上面的方案)。
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
    
    + 4. 测试组件扫描是否奏效(这一步用于测试，实际可以忽略)。[@ContextConfiguration(classes=...class)]()注解表明加载对应应用上下文。[@autowired]()注解表示自动装配对应bean。注意使用[@RunWith()]()注解前需要在maven中为项目添加spring-test包依赖。
    ```java
    //调用Spring-test中的测试类，需要在maven中配置spring-test包依赖
    @RunWith(SpringJUnit4ClassRunner.class)
    
    //通过Java配置类，从而开启组件扫描
    @ContextConfiguration(classes = CDPlayerConfig.class)
    
    //通过xml加载xml，从而开启组件扫描
    //@ContextConfiguration(locations = "classpath:autoconfig/cfg/CDPlayerConfig.xml")
    public class CDPlayerTest {
    
        //自动装配
        @Autowired
        private CompactDisc cd;
    
        //测试方法
        @Test
        public void cdShouldNotBeNull(){
            //断言，判断是否为null，如果为null会throw
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
    
    + 通过上面少量配置就能够用来发现和创建任意数量的bean。
    
+ **第二步**，为组件扫描的bean命名。
    + 在不显示声明时，spring默认将类名的第一个字母变为小写作为bean的ID
    ```java
    @Component //声明当前类是组件，会被自动扫描
    public class SgtPeppers implements CompactDisc {
      //...
    }
    ```
    
    + 通过[@Component()]()实现
    ```java
    @Component("musicClub") //声明当前类是组件，会被自动扫描
    public class SgtPeppers implements CompactDisc {
    //...
    }
    ```
    
    + 通过[@Named()]()实现，这个需要依赖Javax中的依赖注入规范实现，需要添加包依赖。spring支持将其作为@Component注解的替代方案，有细微差别，但绝大多数场景可以替换，最好还是用@Component
    ```java
    import javax.inject.Named;
    @Named("musicClub") //需要添加上面的包依赖
    public class SgtPeppers implements CompactDisc {
      //...
    }
    ```

+ **第三步**，在Java配置类中设置组件扫描的基础包。
    + 指定包位置
    ```java
    @ComponentScan("autoconfig")
    //或者
    @ComponentScan(basePackages="autoconfig")
    ```
    
    + 指定多包位置，通过数组实现。
    ```java
    @ComponentScan(basePackages={"autoconfig","otherconfig",})
    ```
    
    + 指定包中包含的类或者接口(更加类型安全)，通过数组。[通过marker interface实现，这种方式依然能够保持对重构友好的接口引用，但是可以避免引用任何实际的引用程序代码.]() :heavy_exclamation_mark:
    ```java
    @ComponentScan(basePackageClassed={autoconfig.CDPlayerConfig.class})
    ```

+ **第四步**，通过为bean添加注解实现自动装配。
    + 构造器自动装配，当[@Autowired]()注解添加在构造器上是，会spring在创建CDPlayer时通过该构造器创建，且会传入可设置给CompactDisc的类。
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
    
    + 其他方法中的自动装配，Spring会尝试满足方法参数上所声明的依赖。[required=false表明没有匹配bean时，Spring会让这个bean处于未装配状态，此时虽然不会报错，但是调用当前类可能会出现NullPointerException。如果有多个bean匹配，Spring会抛出异常，表明没有明确指定要选择哪个bean进行匹配，这就是自动装配的歧义性。]
    ```java
    @Autowired(required=false)
    public CDPlayer(CompactDisc cd){this.cd = cd;}
    ```

    + [@Inject注解与@Autowired的关系，类似@Named注解与@Component注解的关系。都可以使用。]()

+ **第五步**，验证自动装配。
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
    
    //调用Spring-test中的测试类，需要在maven中配置spring-test包依赖
    @RunWith(SpringJUnit4ClassRunner.class)
    
    //通过Java配置类，从而开启组件扫描
    @ContextConfiguration(classes = CDPlayerConfig.class)
    
    //通过xml加载xml，从而开启组件扫描
    //@ContextConfiguration(locations = "classpath:autoconfig/cfg/CDPlayerConfig.xml")
    public class CDPlayerTest {
    
        //来源于System Rules库的JUnit规则，该规则能够基于控制台的输出编写断言
        /* 需要添加如下依赖，否则无法使用
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
            //注意因为java的println换行符是\r\n，需要统一，否则会判断不等
            assertEquals(
                    "Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles\r\n",
                    log.getLog()
            );
        }
    }
  
    /* Output: 如果不等会报错，正常输出表示相等。
    Playing Sgt. Pepper's Lonely Hearts Club Band by The beatles
    */
    ```
    
####2. _通过Java代码转配bean_
+ 


####3. _通过XML装配bean_


####4. _导入和混合配置_



