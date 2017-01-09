###Chapter 3 : 高级装配  :bangbang:
####1. _Spring Profile_  

> 开发软件的一个最大的挑战之一就是从开发换件到其他环境的切换。经常，在开发环境可以很好运行的软件，到其他环境就不能使用了。数据库配置、加密算法和外部系统的继承只是总舵可能会改变整个部署环境的事情中的一个。  

> 比如配置DatsSource，一种解决方案是将不同的DataSource放在不同的配置文件中，然后使用maven的profile在编译器决定何时使用哪一种配置方式。这个问题是，它需要应用程序去重新编译每一个环境。对于从开发到QA，重新编译不是一个大的问题。但是，在QA到生成环境中要求一个重新编译可能会引入一些BUG，从而导致QA团队的困难。  

> Spring3.1以后提供了一种不需要重新构建的解决方案，Spring会在运行时再根据环境决定每个bean的创建与否，这使得同一个部署单元可以适用于所有的环境！！

+ 配置profile bean:
    + 在JavaConfig中，你可以使用@Profile注解去标明一个Bean属于哪个profile。比如，上面的DataSource可能像下面这样定义.  
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Profile;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
    import javax.sql.DataSource;
  
    @Configuration
    // 我需要你注意的最重要的事情就是，@Profile注解是作用于类的。它告诉Spring，该类的所有Bean只有在 
    // dev profile中才会被激活。如果dev profile没有被激活，则该类下面的Bean会被忽略。
    @Profile("dev")
    public class DevelopmentProfileConfig {
        @Bean(destroyMethod = "shutdown")
        public DataSource dataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:schema.sql")
                    .addScript("classpath:test-data.sql")
                    .build();
        }
    }
    ```

    同时，你可能有另外的生成环境配置文件类。如下：  
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Profile;
    import org.springframework.jndi.JndiObjectFactoryBean;
    import javax.sql.DataSource;
    
    @Configuration
    //在这里，bean不会被创建，除非prod profile激活。
    @Profile("prod")
    public class ProductionProfileConfig {
        @Bean
        public DataSource dataSource() {
            JndiObjectFactoryBean jndiObjectFactoryBean =
                    new JndiObjectFactoryBean();
            jndiObjectFactoryBean.setJndiName("jdbc/myDS");
            jndiObjectFactoryBean.setResourceRef(true);
            jndiObjectFactoryBean.setProxyInterface(
                    javax.sql.DataSource.class);
            return (DataSource) jndiObjectFactoryBean.getObject();
        }
    }
    ```
    
    [上面两段配置类中的bean只有在对应的Profile文件被激活才会分别被创建！这是类级别的.]()  
    [但是Spring3.2开始，可以将 **@profile** 应用在方法级别. 这个特性使得将所有Bean定义组合到一个文件中成为可能.]()  
    将上面两个配置类整合为一个:  
    ```java
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.Profile;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
    import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
    import org.springframework.jndi.JndiObjectFactoryBean;
    import javax.sql.DataSource;
    
    @Configuration
    public class DataSourceConfig {
        @Bean(destroyMethod = "shutdown")
        //对应名称为dev的profile
        @Profile("dev")
        public DataSource embeddedDataSource() {
            return new EmbeddedDatabaseBuilder()
                    .setType(EmbeddedDatabaseType.H2)
                    .addScript("classpath:schema.sql")
                    .addScript("classpath:test-data.sql")
                    .build();
        }
    
        @Bean
        //对应名称为prod的profile
        @Profile("prod")
        public DataSource jndiDataSource() {
            JndiObjectFactoryBean jndiObjectFactoryBean =
                    new JndiObjectFactoryBean();
            jndiObjectFactoryBean.setJndiName("jdbc/myDS");
            jndiObjectFactoryBean.setResourceRef(true);
            jndiObjectFactoryBean.setProxyInterface(javax.sql.DataSource.class);
            return (DataSource) jndiObjectFactoryBean.getObject();
        }
    }
    ```
    
    [对于没有显示声明@Profile的方法或者配置类，其相应的bean则默认始终创建.]() :bangbang:  
    Spring同样 **支持** 通过xml配置文件配置Profile，当然也支持在一个XML中配置多个属于不同Profile的bean.
  
    ```xml
    <!--多个Profile-->
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xsi:schemaLocation="http://www.springframework.org/schema/jdbc
    http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd"
    profile="dev">
        <jdbc:embedded-database id="dataSource">
            <jdbc:script location="classpath:schema.sql" />
            <jdbc:script location="classpath:test-data.sql" />
        </jdbc:embedded-database>
    </beans>
    ```
    
    ```xml
    <!--多个Profile-->
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xmlns:jdbc="http://www.springframework.org/schema/jdbc"
    xmlns:jee="http://www.springframework.org/schema/jee"
    xmlns:p="http://www.springframework.org/schema/p"
    xsi:schemaLocation="
    http://www.springframework.org/schema/jee
    http://www.springframework.org/schema/jee/spring-jee.xsd
    http://www.springframework.org/schema/jdbc
    http://www.springframework.org/schema/jdbc/spring-jdbc.xsd
    http://www.springframework.org/schema/beans
    http://www.springframework.org/schema/beans/spring-beans.xsd">
        <beans profile="dev">
            <jdbc:embedded-database id="dataSource">
                <jdbc:script location="classpath:schema.sql" />
                <jdbc:script location="classpath:test-data.sql" />
            </jdbc:embedded-database>
        </beans>
        <beans profile="qa">
            <bean id="dataSource"
            class="org.apache.commons.dbcp.BasicDataSource"
            destroy-method="close"
            p:url="jdbc:h2:tcp://dbserver/~/test"
            p:driverClassName="org.h2.Driver"
            p:username="sa"
            p:password="password"
            p:initialSize="20"
            p:maxActive="30" />
        </beans>
        <beans profile="prod">
            <jee:jndi-lookup id="dataSource"
            jndi-name="jdbc/myDatabase"
            resource-ref="true"
            proxy-interface="javax.sql.DataSource" />
        </beans>
    </beans>
    ```
    
    这个就是JavaConfig一样，只有在特定的profile被激活的时候，该Bean才会被创建.  
    
+ 激活Profile  
Spring在确定哪个profile处于激活状态时，需要依赖两个独立的属性：[spring.profiles.active]()和[spring.profiles.default]()。  
**Step 1** 如果设置了spring.profiles.active属性的话，那么它的值就会用来确定哪个profile是激活的。  
**Step 2** 但如果没有设置spring.profiles.active属性的话，那Spring将会查找spring.profiles.default的值。  
**Step 3** 如果spring.profiles.active和spring.profiles.default均没有设置的话，那就没有激活的profile，因此**只会创建那些没有定义在profile中的bean**。  
    
    [有多重方式来设置这两个属性:]()
    
        + 作为DispatcherServlet的初始化参数；
        + 作为Web应用的上下文参数；
        + 作为JNDI条目；
        + 作为环境变量；
        + 作为JVM的系统属性；
        + 在集成测试类上，使用@ActiveProfiles注解设置；
        
    作者推荐在Web开发中使用DispatcherServlet的参数将spring.profile.default设置为开发环境的profile, [同时在Servlet上下文进行设置，主要是为了兼顾到ContextLoaderListener.]()  

    ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <web-app version="2.5"
      xmlns="http://java.sun.com/xml/ns/javaee"
      xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
      xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
      http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
        <context-param>
          <param-name>contextConfigLocation</param-name>
          <param-value>/WEB-INF/spring/root-context.xml</param-value>
        </context-param>
      <context-param>
        <param-name>spring.profiles.default</param-name>
        <param-value>dev</param-value>
        </context-param>
      <listener>
          <listener-class>
              org.springframework.web.context.ContextLoaderListener
          </listener-class>
      </listener>
      <servlet>
          <servlet-name>appServlet</servlet-name>
          <servlet-class>
               org.springframework.web.servlet.DispatcherServlet
          </servlet-class>
          <init-param>
              <param-name>spring.profiles.default</param-name>
              <param-value>dev</param-value>
          </init-param>
          <load-on-startup>1</load-on-startup>
      </servlet>
      <servlet-mapping>
      <servlet-name>appServlet</servlet-name>
      <url-pattern>/</url-pattern>
      </servlet-mapping>
    </web-app>
    ```
    
    当更换应用程序部署环境时，负责部署的人根据情况使用系统属性、环境变量或JNDI设置spring.profiles.active即可。当设置spring.profiles.active以后，至于spring.profiles.default置成什么值就已经无所谓了；系统会优先使用spring.profiles.active中所设置的profile。   
     
+ 使用profile进行测试  
当运行集成测试时，通常会希望采用与生产环境（或者是生产环境的部分子集）相同的配置进行测试。
但是，如果配置中的bean定义在了profile中，那么在运行测试时，我们就需要有一种方式来启用合适的profile。
Spring提供了@ActiveProfiles注解，我们可以使用它来指定运行测试时要激活哪个profile。在集成测试时，通常想要激活的是开发环境的profile。例如，下面的测试类片段展现了使用@ActiveProfiles激活dev profile。  
    
    ```java
    @RunWith(SpringJUnit4ClassRunner.class)
    @ContextConfiguration(classes={PersistenceTestConfig.class})
    @ActiveProfiles("dev")
    public class PersistenceTest {
    ...
    }
    ```
    
####2. _条件化的bean声明_



####3. _自动装配与歧义性_


####4. _bean的作用域_


####5. _Spring表达式语言_
