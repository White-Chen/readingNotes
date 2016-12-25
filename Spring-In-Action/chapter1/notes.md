###Chapter 1 : Spring之旅
####1. _Spring简化开发的4种关键策略_
+ 基于POJO的轻量级和最小侵入性编程。
+ 通过依赖注入和面向接口实现松耦合。
+ 基于切面和惯例进行声明式编程。
+ 通过切面和模板减少样板式代码。

####2. _maven项目添加SpringFramework依赖_
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <!--版本号自己根据需要选择，也可以写成动态获取当前版本号-->
        <version>4.3.5.RELEASE</version> 
    </dependency>
</dependencies>
```

####3. _POJO_
+ POJO的全称是：Plain Ordinary Java Object，意为简单的JAVA对象，实际就是普通的JavaBeans。
+ JavaBean vs POJO vs EJB。:heavy_exclamation_mark:
    + POJO:有一些private的参数作为对象的属性，然后针对每一个参数定义get和set方法访问的接口。没有从任何类继承、也没有实现任何接口，更没有被其它框架侵入的java对象。
    + JavaBean:是一种编写JAvA类的规范。
        + 所有属性为private。
        + 这个类必须有一个公共的缺省构造函数。即是提供无参数的构造器。
        + 这个类的属性使用getter和setter来访问，其他方法遵从标准命名规范。
        + 这个类应是可序列化的。实现serializable接口。 
    + EJB:一种javabean的组合规范，通过一组"功能"JavaBean的集合来实现某个企业组的业务逻辑。

####3. _依赖注入/Dependency Injection_
+ 依赖注入(DI)：

+ [mock测试就是在测试过程中，对那些不容易构建的对象用一个虚拟对象来代替测试的方法就叫mock测试.]() :bangbang: