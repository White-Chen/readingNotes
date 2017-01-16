###Chapter 5 : 构建Spring Web应用程序
本章通过构建一个非常简单的类似早期Twitter的应用, 一些代码并不适宜放入当前笔记, 想看源代码的请点击[这里](src/main).

####1. [__DispatcherServlet__]()
+ DispatcherServlet工作流程图:

![](pics/dispatcherServlet.jpg)

+ DispatcherServlet工作流程:
    + 1. 在Spring MVC中, DispatcherServlet就是前端控制器. DispatcherServlet的任务是将请求发送给Spring MVC控制器（controller）
    + 2. DispatcherServlet需要知道应该将请求发送给哪个控制器. 所以DispatcherServlet以会查询一个或多个处理器映射, 来确定请求的下一站在哪里. 处理器映射会根据请求所携带的URL信息来进行决策. 
    + 3. 一旦选择了合适的控制器, DispatcherServlet会将请求发送给选中的控制器. **实际上, 设计良好的控制器本身只处理很少甚至不处理工作, 而是将业务逻辑委托给一个或多个服务对象进行处理.** 
    + 4. 控制器在完成逻辑处理后, 通常会产生一些信息, 这些信息需要返回给用户并在浏览器上显示. 这些信息被称为模型 **model**. 控制器所做的最后一件事就是将模型数据打包, 并且标示出用于渲染输出的视图名. 它接下来会将请求连同模型和视图名发送回DispatcherServlet.
    + 5. DispatcherServlet将会使用视图解析器（view resolver）来将逻辑视图名匹配为一个特定的视图实现, **它可能是也可能不是JSP**. 
    + 6. DispatcherServlet交付模型数据.
    + 7. 视图将使用模型数据渲染输出, 这个输出会通过响应对象传递给客户端.
    
####2. _搭建Spring MVC_
+ 配置依赖包. 
    + 以Maven为例, 注意这里省略了Spring的基础依赖配置. 
    ```xml
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-web</artifactId>
        <version>4.3.5.RELEASE</version>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
        <version>4.3.5.RELEASE</version>
    </dependency>
    ```
    
+ 配置DispatcherServlet
    + 传统方式: 在web.xml文件中配置. 
    + 本章方式: 在Servlet容器中配置. 
    ```java
    package spittr.config;
    import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
    /**
     * \* Created with Chen Zhe on 1/15/2017.
     * \* Description: 负责将请求路由到其他的组件中
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    public class SpittrWebAppInitializer
            extends AbstractAnnotationConfigDispatcherServletInitializer{
    
        /**
         * 配置RootConfig类的位置
         * @return 返回带有@configuration注解的类, 用于配置ContextLoaderListener应用上下文
         */
        @Override
        protected Class<?>[] getRootConfigClasses() {
            return new Class<?>[] { RootConfig.class};
        }
    
        /**
         * 配置WebConfig类的位置
         * @return 返回带有@Configuration注解的bean, 用于配置DispatcherServlet应用上下文
         */
        @Override
        protected Class<?>[] getServletConfigClasses() {
            return new Class<?>[] { WebConfig.class};
        }
    
        /**
         * 将一个或多个路径映射到DispatcherServlet上
         * 这里通过 "/" 声明它会是默认Servlet, 他会处理进入应用的所有请求. 
         */
        @Override
        protected String[] getServletMappings() {
            return new String[] { "/" };
        }
    }
    ```

+ **AbstractAnnotationConfigDispatcherServletInitializer** 剖析

>   在Servlet 3.0+ 环境中, 容器会在类路径中查找实现javax.servlet.ServletContainerInitializer
    接口的类, 如果能发现的话, 就会用它来配置Servlet容器. 
    Spring提供了这个接口的实现, 名为SpringServletContainerInitializer, 这个类
    反过来又会查找实现WebApplicationInitializer的类并将配置的任务交给它们来完成. 
    Spring 3.2引入了一个便利的WebApplicationInitializer基础实现, 也就是
    AbstractAnnotationConfigDispatcherServletInitializer. 因为我们的SpittrWebAppInitializer
    扩展了AbstractAnnotationConfigDispatcherServletInitializer（同时也就实现了
    WebApplicationInitializer）, 因此当部署到Servlet 3.0容器中的时候, 容器会自
    动发现它, 并用它来配置Servlet上下文. 
    getServletMappings(),  它会将一个或多个路径映射到DispatcherServlet上. 上面示例中, 它映射的是"/", 这表示它会是应用的默认Servlet. 它会处理进入应用的所有请求.  
    getServletConfigClasses()方法返回的带有@Configuration注解的类将会用来定义DispatcherServlet应用上下文中的bean. 
>   getRootConfigClasses()方法返回的带有@Configuration注解的类将会用来配置ContextLoaderListener创建的应用上下文中的bean. 

>   DispatcherServlet和一个Servlet监听器（也就是ContextLoaderListener）的关系. 
    当DispatcherServlet启动的时候, 它会创建Spring应用上下文, 并加载配置文件或配
    置类中所声明的bean. 在程序清单5.1的getServletConfigClasses()方法中, 我们要
    求DispatcherServlet加载应用上下文时, 使用定义在WebConfig配置类（使用Java配
    置）中的bean. 但是在Spring Web应用中, 通常还会有另外一个应用上下文. 另外的
    这个应用上下文是由ContextLoaderListener创建的. 
    我们希望DispatcherServlet加载包含Web组件的bean, 如控制器、视图解析器以及处理
    器映射, 而ContextLoaderListener要加载应用中的其他bean. 这些bean通常是驱动应
>   用后端的中间层和数据层组件. 

+ 启动Spring MVC
    + 创建带有@EnableWebMvc注解的类. [注意看代码注释.]()
    ```java
    package spittr.config;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.ViewResolver;
    import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
    import org.springframework.web.servlet.view.InternalResourceViewResolver;
    /**
     * \* Created with Chen Zhe on 1/15/2017.
     * \* Description:
     *      DispatcherServlet会加载当前类也就是@Configuration注解的类, 用于配置DispatcherServlet
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    
    @Configuration                  //声明当前Java类为Spring配置类
    @EnableWebMvc                   //启用Spring MVC
    @ComponentScan("spittr.web")    //配置组件扫描路径, 否则Spring将只能找到显式声明在当前类中的控制器
                                    //书上写的包路径有问题, 配置包路径与实际包名称不符, 需要自己注意
    public class WebConfig
            extends WebMvcConfigurerAdapter{
    
        /**
         * Description : 配置试图解析器, 如果不配置则会使用默认的BeanNameViewResolver. 
         *               Spring默认会使用BeanNameView-Resolver, 这个视图解析器会查找ID
         *               与视图名称匹配的bean, 并且查找的bean要实现View接口, 它以这样的
         *               方式来解析视图. 
         */
        @Bean
        public ViewResolver viewResolver(){
            InternalResourceViewResolver resolver =
                    new InternalResourceViewResolver();
            //用于拼接, 从而匹配对应的视图, 注意看后缀设置使得即使文件的匹配更加灵活, 可以不是jsp
            resolver.setPrefix("/WEB-INF/views/");
            resolver.setSuffix(".jsp");
            resolver.setExposeContextBeansAsAttributes(true);
            return resolver;
        }
    
        /**
         * Description : 配置静态资源的处理, 否则的话DispatcherServlet会处理所有静态资源的请求
         */
        @Override
        public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
            configurer.enable();
        }
    }

    ```
    
+ 创建RootConfig类, 因为聚焦于Web开发, 所以暂时应用程序上下文配置的较为简单
    + 示例代码: 
    ```java
    package spittr.config;
    import org.springframework.context.annotation.ComponentScan;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.context.annotation.FilterType;
    import org.springframework.web.servlet.config.annotation.EnableWebMvc;
    /**
     * \* Created with Chen Zhe on 1/15/2017.
     * \* Description:
     *      DispatcherServlet会加载当前类也就是@Configuration注解的类, 用于配置ContextLoaderListener
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    @Configuration
    //书上写的包路径有问题, 配置包路径与实际包名称不符, 需要自己注意
    @ComponentScan(basePackages = {"spittr.web"},
        excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ANNOTATION, value = EnableWebMvc.class)
        })
    public class RootConfig{
    }

    ```