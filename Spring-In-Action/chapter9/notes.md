###Chapter 9 : 保护Web应用

> Spring Security, 是一种基于Spring AOP和Servlet规范中的Filter实现的安全框架. 
> 
> Spring Security从两个角度来解决安全性问题.
> 1. 它使用Servlet规范中的Filter保护Web请求并限制URL级别的访问. 
> 2. Spring Security还能够使用Spring AOP保护方法调用——借助于对象代理和使用通知, 能够确保只有具备适当权限的用户才能访问安全保护的方法. 
>
> 本章关注如何将Spring Security用于Web层的安全性之中

####1. _Spring Security的模块_

| 模块                   | 描述                                                                                             |
|------------------------|--------------------------------------------------------------------------------------------------|
| ACL                    | 支持通过访问控制列表（access control list, ACL）为域对象提供安全性                               |
| 切面（Aspects）        | 一个很小的模块, 当使用Spring Security注解时, 会使用基于AspectJ的切面, 而不是使用标准的Spring AOP |
| CAS客户端（CASClient） | 提供与Jasig的中心认证服务（Central Authentication Service, CAS）进行集成的功能                   |
| 配置（Configuration）  | 包含通过XML和Java配置Spring Security的功能支持                                                   |
| 核心（Core）           | 提供Spring Security基本库                                                                        |
| 加密（Cryptography）   | 提供了加密和密码编码的功能                                                                       |
| LDAP                   | 支持基于LDAP进行认证                                                                             |
| OpenID                 | 支持使用OpenID进行集中式认证                                                                     |
| Remoting               | 提供了对Spring Remoting的支持                                                                    |
| 标签库（TagLibrary）   | Spring Security的JSP标签库                                                                       |
| Web                    | 提供了Spring Security基于Filter的Web安全性支持                                                   |

####2. _配置Spring Security_
+ Filter配置. 不管我们通过web.xml还是通过AbstractSecurityWebApplicationInitializer的子类来配置DelegatingFilterProxy, 它都会拦截发往应用中的请求, 并将请求委托给ID为springSecurityFilterChain bean. 
```xml
<filter>
    <filter-name>springSecurityFilterChain</filter-name>
    <filter-class>
        org.springframework.web.filter.DelegatingFilterProxy
    </filter-class>
</filter>
```

```java
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
public class SecurityWebInitializer
    extends AbstractSecurityWebApplicationInitializer {}
```

+ 安全性配置. @EnableWebSecurity注解将会启用Web安全功能. 但它本身并没有什么用处, Spring Security必须配置在一个实现了WebSecurityConfigurer的bean中, 或者（简单起见）扩展WebSecurityConfigurerAdapter. 
```java
package spitter.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
}
```

> 一般通过重载一下三个方法实现安全性的自定义配置
> 1. configure(WebSecurity): 通过重载, 配置Spring Security的Filter链
> 2. configure(HttpSecurity): 通过重载, 配置如何通过拦截器保护请求
> 3. configure(AuthenticationManagerBuilder): 通过重载, 配置user-detail服务

> 为了让Spring Security满足我们应用的需求, 还需要再添加一点配置.   
> 1. 配置用户存储;
> 2. 指定哪些请求需要认证, 哪些请求不需要认证, 以及所需要的权限;
> 3. 提供一个自定义的登录页面, 替代原来简单的默认登录页. 

####3. _选择查询用户详细信息的服务_
+ 使用基于内存的用户存储
```java
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
    .inMemoryAuthentication()
    .withUser("user").password("password").roles("USER").and()
    .withUser("admin").password("password").roles("USER", "ADMIN");
    }
}
```

除了password()、roles()(roles()方法是authorities()方法的简写形式)和and()方法以外, withUser()返回的UserDetailsBuilder类还有其他的几个方法可以用来配置内存用户存储中的用户信息. 

| 方法                             | 描述                       |
|----------------------------------|----------------------------|
| accountExpired(boolean)          | 定义账号是否已经过期       |
| accountLocked(boolean)           | 定义账号是否已经锁定       |
| and()                            | 用来连接配置               |
| authorities(GrantedAuthority...) | 授予某个用户一项或多项权限 |
| authorities(List)                | 授予某个用户一项或多项权限 |
| authorities(String...)           | 授予某个用户一项或多项权限 |
| credentialsExpired(boolean)      | 定义凭证是否已经过期       |
| disabled(boolean)                | 定义账号是否已被禁用       |
| password(String)                 | 定义用户的密码             |
| roles(String...)                 | 授予某个用户一项或多项角色 |

+ 基于数据库表进行认证. 

> 通过调用groupAuthoritiesByUsername()方法, 我们也能够将群组权限重写为自定义的查询语句. 
>
> 将默认的SQL查询替换为自定义的设计时, 很重要的一点就是要遵循查询的基本协议. 所有查询都将用户名作为唯一的参数. 认证查询会选取用户名、密码以及启用状态信息. 权限查询会选取零行或多行包含该用户名及其权限信息的数据. 群组权限查询会选取零行或多行数据, 每行数据中都会包含群组ID、群组名称以及权限. 
>
> passwordEncoder()方法可以接受Spring Security中PasswordEncoder接口的任意实现. Spring Security的加密模块包括了三个这样的实现: 
> + BCryptPasswordEncoder
> + NoOpPasswordEncoder
> + StandardPasswordEncoder. 

```java
@Autowired
DataSource dataSource;

@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
        .jdbcAuthentication()
        .dataSource(dataSource)
        .usersByUsernameQuery(
            "select username, password, true " +
            "from Spitter where username=?")
        .authoritiesByUsernameQuery(
            "select username, 'ROLE_USER' from Spitter where username=?")
        .passwordEncoder(new StandardPasswordEncoder("53cr3t"));;
}
```

+ [基于LDAP进行认证]() :bangbang:
```java
@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
        .ldapAuthentication()
        .userSearchFilter("(uid={0})")
        .groupSearchFilter("member={0}");
}
```

配置密码比对: 

```java
@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
        .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}");
}
```

引用远程的LDAP服务器:

```java
@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
        .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .contextSource()
        .url("ldap://habuma.com:389/dc=habuma,dc=com");
}
```

配置嵌入式的LDAP服务器:

```java
@Override
protected void configure(AuthenticationManagerBuilder auth)
        throws Exception {
    auth
        .ldapAuthentication()
        .userSearchBase("ou=people")
        .userSearchFilter("(uid={0})")
        .groupSearchBase("ou=groups")
        .groupSearchFilter("member={0}")
        .contextSource()
        .root("dc=habuma,dc=com");
        .ldif("classpath:users.ldif");
}
```

+ 配置自定义的用户服务. 需要提供一个自定义的UserDetailsService接口实现. loadUserByUsername()方法会返回代表给定用户的UserDetails对象
```java
public interface UserDetailsService {
    UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException;
}
```

####4. _拦截请求_
+ 需要重载安全性配置类中configure(HttpSecurity)方法. 
+ HttpSecurity类提供的可用配置方法

| 方法                       | 能够做什么                                                          |
|----------------------------|---------------------------------------------------------------------|
| access(String)             | 如果给定的SpEL表达式计算结果为true, 就允许访问                      |
| anonymous()                | 允许匿名用户访问                                                    |
| authenticated()            | 允许认证过的用户访问                                                |
| denyAll()                  | 无条件拒绝所有访问                                                  |
| fullyAuthenticated()       | 如果用户是完整认证的话（不是通过Remember-me功能认证的）, 就允许访问 |
| hasAnyAuthority(String...) | 如果用户具备给定权限中的某一个的话, 就允许访问                      |
| hasAnyRole(String...)      | 如果用户具备给定角色中的某一个的话, 就允许访问                      |
| hasAuthority(String)       | 如果用户具备给定权限的话, 就允许访问                                |
| hasIpAddress(String)       | 如果请求来自给定IP地址的话, 就允许访问                              |
| hasRole(String)            | 如果用户具备给定角色的话, 就允许访问                                |
| not()                      | 对其他访问方法的结果求反                                            |
| permitAll()                | 无条件允许访问                                                      |
| rememberMe()               | 如果用户是通过Remember-me功能认证的, 就允许访问                     |

+ [所有配合均可以用and()方法连接]()
+ [规则会按照给定的顺序发挥作用. 所以, 很重要的一点就是将最为具体的请求路径放在前面, 而最不具体的路径（如anyRequest()）放在最后面. 如果不这样做的话, 那不具体的路径配置将会覆盖掉更为具体的路径配置. ]()

+ Spring Security通过一些安全性相关的表达式扩展了Spring表达式语言.

| 安全表达式               | 计算结果                                                                        |
|--------------------------|---------------------------------------------------------------------------------|
| authentication           | 用户的认证对象                                                                  |
| denyAll                  | 结果始终为false                                                                 |
| hasAnyRole(list ofroles) | 如果用户被授予了列表中任意的指定角色, 结果为true                                |
| hasRole(role)            | 如果用户被授予了指定的角色, 结果为true                                          |
| hasIpAddress(IPAddress)  | 如果请求来自指定IP的话, 结果为true                                              |
| isAnonymous()            | 如果当前用户为匿名用户, 结果为true                                              |
| isAuthenticated()        | 如果当前用户进行了认证的话, 结果为true                                          |
| isFullyAuthenticated()   | 如果当前用户进行了完整认证的话（不是通过Remember-me功能进行的认证）, 结果为true |
| isRememberMe()           | 如果当前用户是通过Remember-me自动认证的, 结果为true                             |
| permitAll                | 结果始终为true                                                                  |
| principal                | 用户的principal对象                                                             |

+ 强制通道的安全性: requiresChannel()方法会为选定的URL强制使用HTTPS. requiresInsecure()方法则将匹配方法使用HTTP通道. 

+ 防止跨站请求伪造. 

> 从Spring Security 3.2开始, 默认就会启用CSRF防护. 
> Spring Security通过一个同步token的方式来实现CSRF防护的功能. 它将会拦截状态变化的请求（例如, 非GET、HEAD、OPTIONS和TRACE的请求）并检查CSRF token. 如果请求中不包含CSRF token的话, 或者token不能与服务器端的token相匹配, 请求将会失败, 并抛出CsrfException异常. 
> 所有的表单必须在一个"_csrf"域中提交token, 而且这个token必须要与服务器端计算并存储的token一致, 这样的话当表单提交的时候, 才能进行匹配. 
> 如果使用Spring的表单绑定标签的话, \<sf:form\>标签会自动为我们添加隐藏的CSRF token标签. 
> 可以在配置中通过调用csrf().disable()禁用Spring Security的CSRF防护功能. 

####5. _认证用户_
+ 在configure(HttpSecurity)方法中, 调用formLogin()可以启用默认的登录页面. 
+ 在formLogin()后通过调用loginPage(String)方法可以配置自定义的登陆界面对应的视图名称. 
+ 在configure()方法所传入的HttpSecurity对象上调用httpBasic()可以启用HTTP Basic认证. 
+ 在HttpSecurity对象上调用rememberMe()可以启用记忆功能
```java
@Override
protected void configure(HttpSecurity http) throws Exception {
    http
        .formLogin()
        .loginPage("/login")
        .and()
        .rememberMe()
        .tokenValiditySeconds(2419200)
        .key("spittrKey");
}
```

+ 在HttpSecurity对象中logout()提供了配置退出行为的方法. 调用logoutSuccessUrl()表明在退出成功之后, 浏览器需要重定向到指定位置. 

####6. _保护视图_
+ 标签库

| JSP标签                        | 作用                                                                               |
|--------------------------------|------------------------------------------------------------------------------------|
| \<security:accesscontrollist\> | 如果用户通过访问控制列表授予了指定的权限, 那么渲染该标签体中的内容                 |
| \<security:authentication\>    | 渲染当前用户认证对象的详细信息                                                     |
| \<security:authorize\>         | 如果用户被授予了特定的权限或者SpEL表达式的计算结果为true, 那么渲染该标签体中的内容 |

+ \<security:authentication\> 标签属性

| 认证属性    | 描述                                             |
|-------------|--------------------------------------------------|
| authorities | 一组用于表示用户所授予权限的GrantedAuthority对象 |
| Credentials | 用于核实用户的凭证（通常, 这会是用户的密码）     |
| details     | 认证的附加信息（IP地址、证件序列号、会话ID等）   |
| principal   | 用户的基本信息对象                               |
