###Chapter 7 : Spring MVC的高级技术

> 本章节主要介绍一些常用功能的使用

####1. _Spring MVC 配置的替代方案_
+ 自定义DispatcherServlet配置
    + 重载customizeRegistration(). 此类的方法之一就是customizeRegistration(). 在AbstractAnnotationConfigDispatcherServletInitializer将DispatcherServlet注册到Servlet容器中之后, 就会调用customizeRegistration(), 并将Servlet注册后得到的Registration.Dynamic传递进来. 通过重载customizeRegistration()方法, 我们可以对DispatcherServlet进行额外的配置. 
    ```java
    /*示例*/
    @Override
    protected void customizeRegistration(Dynamic registration) {
      registration.setMultipartConfig(
      new MultipartConfigElement("/tmp/spittr/uploads"));
    }
    ```
    
+ [添加其他的Servlet和Filter](). 基于Java的初始化器(initializer)的一个好处就在于我们可以定义任意数量的初始化器类. 因此, 如果我们想往Web容器中注册其他组件的话, 只需创建一个新的初始化器就可以了. 最简单的方式就是实现Spring的WebApplicationInitializer接口. 
```java
package com.myapp.config;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;
import org.springframework.web.WebApplicationInitializer;
import com.myapp.MyServlet;
public class MyServletInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext)
            throws ServletException {
        Dynamic myServlet =
        servletContext.addServlet("myServlet", MyServlet.class);
        myServlet.addMapping("/custom/**");
    }
}
```

+ [注册Filter](). 两种方案, 后者更加便捷, 前者定制性更强.
    + 
```java
@Override
public void onStartup(ServletContext servletContext)
        throws ServletException {
    javax.servlet.FilterRegistration.Dynamic filter =
            servletContext.addFilter("myFilter", MyFilter.class);
    filter.addMappingForUrlPatterns(null, false, "/custom/*");
}
```

```java
@Override
protected Filter[] getServletFilters() {
    return new Filter[] { new MyFilter() };
}
```

+ [在web.xml中声明DispatcherServlet. 如果同时配置Java和xml, xml优先.]()  :bangbang:
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
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

+ [设置web.xml使用基于Java的配置]()
```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app version="2.5"
    xmlns="http://java.sun.com/xml/ns/javaee"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="http://java.sun.com/xml/ns/javaee
        http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <context-param>
        <param-name>contextClass</param-name>
        <param-value>
            org.springframework.web.context.support.AnnotationConfigWebApplicationContext
        </param-value>
    </context-param>
    <context-param>
        <param-name>contextConfigLocation</param-name>
        <param-value>com.habuma.spitter.config.RootConfig</param-value>
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
            <param-name>contextClass</param-name>
            <param-value>
            org.springframework.web.context.support.AnnotationConfigWebApplicationContext
            </param-value>
        </init-param>
        <init-param>
            <param-name>contextConfigLocation</param-name>
            <param-value>
            com.habuma.spitter.config.WebConfigConfig
            </param-value>
        </init-param>
        <load-on-startup>1</load-on-startup>
    </servlet>
    <servlet-mapping>
        <servlet-name>appServlet</servlet-name>
        <url-pattern>/</url-pattern>
    </servlet-mapping>
</web-app>
```

####2. _处理multipart形式的数据_
+ 配置multipart解析器
    + 从Spring 3.1开始, Spring内置了两个MultipartResolver的实现供我们选择: 
        + CommonsMultipartResolver: 使用Jakarta Commons FileUpload解析multipart请求; 
        + StandardServletMultipartResolver: 依赖于Servlet 3.0对multipart请求的支持(始于Spring 3.1). [不需要额外添加依赖, 优先选择.]()
        
    + 在Spring应用上下文中定义
    ```java
    @Bean
    public MultipartResolver multipartResolver() throws IOException {
      return new StandardServletMultipartResolver();
    }
    ```
    
    + 注册解析器
    ```java
    @Override
    protected void customizeRegistration(Dynamic registration) {
      registration.setMultipartConfig(
              new MultipartConfigElement("/tmp/spittr/uploads"));
    }
    ```
    
    ```java
    @Override
    protected void customizeRegistration(Dynamic registration) {
      registration.setMultipartConfig(
          // 单个文件最大, 总请求最大, 缓存大小
          new MultipartConfigElement("/tmp/spittr/uploads",
              2097152, 4194304, 0));
    }
    ```
    
+ 处理multipart请求
    + 页面声明部分
    ```html
    <form method="POST" th:object="${spitter}"
        enctype="multipart/form-data">
        ...
        ...
    </form>
    ```
    
    + 后台处理部分
    ```java
    @RequestMapping(value="/register", method=POST)
    public String processRegistration(
        @RequestPart("profilePicture") MultipartFile profilePicture,
        @Valid Spitter spitter,
        Errors errors) {
        ...
    }
    ```
    
    + 写到磁盘
    ```java
    profilePicture.transferTo(
          new File("/data/spittr/" + profilePicture.getOriginalFilename()));
    ```
    
    + 将文件保存到Amazon S3中, [因为国内被墙试不了o(╯□╰)o](). 
    
    + 以Part的形式接受上传的文件, 这个和MultipartFile基本没区别, 出了个别方法名称不一样, 就不详细介绍了. 
    
####3. _处理异常_
+ Spring提供了多种方式将异常转换为响应: 
    + 特定的Spring异常将会自动映射为指定的HTTP状态码; 
    + 异常上可以添加@ResponseStatus注解, 从而将其映射为某一个HTTP状态码; 
    + 在方法上可以添加@ExceptionHandler注解, 使其用来处理异常. 
    
+ 通过@ResponseStatus注解将异常映射为HTTP状态码
```java
//示例
package spittr.web;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
@ResponseStatus(value=HttpStatus.NOT_FOUND,
            reason="Spittle Not Found")
public class SpittleNotFoundException extends RuntimeException {
}
```

```java
//示例
@RequestMapping(value="/{spittleId}", method=RequestMethod.GET)
public String spittle(
        @PathVariable("spittleId") long spittleId,
        Model model) {
    Spittle spittle = spittleRepository.findOne(spittleId);
    if (spittle == null) {
        throw new SpittleNotFoundException();
    }
    model.addAttribute(spittle);
    return "spittle";
}
```

+ 编写异常处理的方法
    + 假设下面代码可能会报错.
    ```java
    @RequestMapping(method=RequestMethod.POST)
    public String saveSpittle(SpittleForm form, Model model) {
      try {
          spittleRepository.save(
          new Spittle(null, form.getMessage(), new Date(),
          form.getLongitude(), form.getLatitude()));
          return "redirect:/spittles";
      } catch (DuplicateSpittleException e) {
          return "error/duplicate";
      }
    }
    ```
    
    + 那么通过编写异常处理方法, 可以将错误处理流程抽离出来. [注意这里只能处理同一个Controller中所有的相对应的异常类, 并不能做到全局处理.]()
    ```java
    public String saveSpittle(SpittleForm form, Model model) {
        spittleRepository.save(
              new Spittle(null, form.getMessage(), new Date(),
                      form.getLongitude(), form.getLatitude()));
        return "redirect:/spittles";
    }
  
    @ExceptionHandler(DuplicateSpittleException.class)
    public String handleDuplicateSpittle() {
        return "error/duplicate";
    }
    ```
    
+ 为控制器添加通知, [全局性.]() 任意带有@ControllerAdvice注解的类, 这个类会包含一个或多个如下类型的方法. :bangbang:
    + @ExceptionHandler注解标注的方法;
    + @InitBinder注解标注的方法; 
    + @ModelAttribute注解标注的方法. 
    + 示例: 
    ```java
    package spitter.web;
    import org.springframework.web.bind.annotation.ControllerAdvice;
    import org.springframework.web.bind.annotation.ExceptionHandler;
    @ControllerAdvice
    public class AppWideExceptionHandler {
      @ExceptionHandler(DuplicateSpittleException.class)
      public String duplicateSpittleHandler() {
          return "error/duplicate";
      }
    }
    ```

####4. _跨重定向请求传递数据_
+ forward前缀会保留模型数据, 但是redirect前缀重定向后会清除模型数据. 
+ 通过URL模板进行重定向.  [缺点只能传递String和int这些基本数据, 不能传递复杂的模型数据.]()
```java
@RequestMapping(value="/register", method=POST)
public String processRegistration(
        Spitter spitter, Model model) {
    spitterRepository.save(spitter);
    model.addAttribute("username", spitter.getUsername());
    model.addAttribute("spitterId", spitter.getId());
    return "redirect:/spitter/{username}";
}

// 实际输出: /spitter/habuma?spitterId=xxxx
// 不匹配的参数会作为请求参数加入
```

+ 使用flash属性. RedirectAttributes和Model类似, 不过提供了额外的addFlashAttribute方法用于添加模型数据. 
```java
@RequestMapping(value="/register", method=POST)
public String processRegistration(
        Spitter spitter, RedirectAttributes model) {
    spitterRepository.save(spitter);
    model.addAttribute("username", spitter.getUsername());
    model.addFlashAttribute("spitter", spitter);
    return "redirect:/spitter/{username}";
}
```