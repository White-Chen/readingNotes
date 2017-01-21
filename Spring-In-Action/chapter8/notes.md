###Chapter 8 : 使用 Spring Web Flow

> 这一章介绍了基于Spring MVC之上的另外一个框架Spring Web Flow的使用
> 因为需要结合具体的应用进行说明, 因此笔记主要会以介绍配置以及概念为主
> 更加详细的使用请结合[官方文档](http://docs.spring.io/spring-webflow/docs/2.4.4.RELEASE/reference/html/)使用


> 有时候, Web应用程序需要控制网络冲浪者的方向, 引导他们一步步地访问应用. 比较典型的例子就是电子商务站点的结账流程, 从购物车开始, 应用程序会引导你依次经过派送详情、账单信息以及最终的订单确认流程. 而Spring Web Flow是一个Web框架, 它适用于元素按规定流程运行的程序.

+ 配置Spring Web Flow
    + 基于xml配置(书上介绍的版本)
        + 命名空间声明
        ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <beans xmlns="http://www.springframework.org/schema/beans"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xmlns:flow="http://www.springframework.org/schema/webflow-config"
          xsi:schemaLocation=
            "http://www.springframework.org/schema/webflow-config
            http://www.springframework.org/schema/webflow-config/[CA]
            spring-webflow-config-2.3.xsd
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd">
        ```
  
        + 装配流程执行器. 流程执行器负责创建和执行流程, 它并不负责加载流程定义. 
        ```xml
        <flow:flow-executor id="flowExecutor" />
        ```
        
        + 配置流程注册表 
        ```xml
        <flow:flow-registry id="flowRegistry" base-path="/WEB-INF/flows">
              <flow:flow-location-pattern value="*-flow.xml" />
        </flow:flow-registry>
        <!--或者, 前者使用通配符, 后者可自定义id-->
        <flow:flow-registry id="flowRegistry">
              <flow:flow-location id="pizza"
                  path="/WEB-INF/flows/springpizza.xml" />
        </flow:flow-registry>
        ```
    
        + 处理流程请求. DispatcherServlet一般将请求分发给控制器. 但是对于流程而言, 我们需要一个FlowHandlerMapping来帮助DispatcherServlet将流程请求发送给Spring Web Flow. FlowHandlerMapping的工作仅仅是将流程请求定向到Spring Web Flow上, 响应请求的是FlowHandlerAdapter. FlowHandlerAdapter等同于Spring MVC的控制器, 它会响应发送的流程请求并对其进行处理. 
        ```xml
        <bean class=
          "org.springframework.webflow.mvc.servlet.FlowHandlerMapping">
              <property name="flowRegistry" ref="flowRegistry" />
        </bean>
        <bean class=
          "org.springframework.webflow.mvc.servlet.FlowHandlerAdapter">
              <property name="flowExecutor" ref="flowExecutor" />
        </bean>
        ```
    
    + 基于java配置(在2.4版本以后才支持), 个人更喜欢使用这种配置方式, 不过这种方式还是不够彻底, 因为具体的flow配置仍然需要使用xml才行, 期待后续的版本更新
    ```java
    // 注意声明了类需要在上下文配置进行配置引入, 通过@Import
    @Configuration
    @ComponentScan("com.pizza.web")
    public class WebFlowConfig
            extends AbstractFlowConfiguration{
    
        @Bean
        public FlowDefinitionRegistry flowDefinitionRegistry(){
            return getFlowDefinitionRegistryBuilder()
                    .setBasePath("/WEB-INF/flows")
                    .addFlowLocationPattern("*-flow.xml")
                    .build();
        }
    
        @Bean
        public FlowExecutor flowExecutor(){
            return getFlowExecutorBuilder(flowDefinitionRegistry())
                    .build();
        }
    
        @Bean
        public FlowHandlerAdapter flowHandlerAdapter(){
            FlowHandlerAdapter flowHandlerAdapter =
                    new FlowHandlerAdapter();
            flowHandlerAdapter.setFlowExecutor(flowExecutor());
            return flowHandlerAdapter;
        }
    
        @Bean
        public FlowHandlerMapping flowHandlerMapping(){
            FlowHandlerMapping flowHandlerMapping =
                    new FlowHandlerMapping();
            flowHandlerMapping.setFlowRegistry(flowDefinitionRegistry());
            return flowHandlerMapping;
        }
    }
    ```
    
####2. _流程的组件_
+ 状态分类

| 状态类型          | 它是用来做什么的                                                   |
|-------------------|--------------------------------------------------------------------|
| 行为（Action）    | 行为状态是流程逻辑发生的地方                                       |
| 决策（Decision）  | 决策状态将流程分成两个方向, 它会基于流程数据的评估结果确定流程方向 |
| 结束（End）       | 结束状态是流程的最后一站. 一旦进入End状态, 流程就会终止            |
| 子流程（Subflow） | 子流程状态会在当前正在运行的流程上下文中启动一个新的流程           |
| 视图（View）      | 视图状态会暂停流程并邀请用户参与流程                               |

+ 视图状态
```xml
<!--不定义view的话, view=id-->
<view-state id="welcome" view="greeting" />

<!--绑定了模型的view-->
<view-state id="takePayment" model="flowScope.paymentDetails"/>
```
    
+ 行为状态. \<action-state\>元素一般都会有一个\<evaluate\>作为子元素. \<evaluate\>元素给出了行为状态要做的事情. expression属性指定了进入这个状态时要评估的表达式. 
```xml
<action-state id="saveOrder">
    <evaluate expression="pizzaFlowActions.saveOrder(order)" />
    <transition to="thankYou" />
</action-state>
```
    
+ Spring Web Flow与表达式语言

> 在1.0版本的时候, Spring Web Flow使用的是对象图导航语言（Object-Graph Navigation Language , OGNL）. 
> 在2.0版本又换成了统一表达式语言（Unified Expression Language , UnifiedEL）. 
> 在2.1版本中, Spring Web Flow使用的是SpEL. 尽管可以使用上述的任意表达式语言来配置Spring Web Flow, 但SpEL是默认和推荐使用的表达式语言. 
> 因此, 当定义流程的时候, 我们会选择使用SpEL, 忽略掉其他的可选方案. 

+ 决策状态. 决策状态能够在流程执行时产生两个分支. 决策状态将评估一个Boolean类型的表达式, 然后在两个状态转移中选择一个, 这要取决于表达式会计算出true还是false. 
```xml
<!--then表示表达式返回为true, 返回false执行else-->
<decision-state id="checkDeliveryArea">
<if test="pizzaFlowActions.checkDeliveryArea(customer.zipCode)"
    then="addCustomer"
    else="deliveryWarning" />
</decision-state>
```
    
+ 子流程状态. \<subflow-state\>允许在一个正在执行的流程中调用另一个流程. 这类似于在一个方法中调用另一个方法. 
```xml
<subflow-state id="order" subflow="pizza/order">
    <input name="order" value="order"/>
    <transition on="orderCreated" to="payment" />
</subflow-state>
```

+ 结束状态. 
```xml
<end-state id="customerReady" />
```
    
+ 当到达\<end-state\>状态, 流程会结束. 接下来会发生什么取决于几个因素: 
    + 如果结束的流程是一个子流程, 那调用它的流程将会从\<subflow-state\>处继续执行. \<end-state\>的ID将会用作事件触发从\<subflow-state\>开始的转移. 
    + 如果<end-state>设置了view属性, 指定的视图将会被渲染. 视图可以是相对于流程路径的视图模板, 如果添加"externalRedirect:"前缀的话, 将会重定向到流程外部的页面, 如果添加"flowRedirect:"将重定向到另一个流程中. 
    + 如果结束的流程不是子流程, 也没有指定view属性, 那这个流程只是会结束而已. 浏览器最后将会加载流程的基本URL地址, 当前已没有活动的流程, 所以会开始一个新的流程实例. 
    

####3. _状态转移_
+ 转移连接了流程中的状态. 流程中除结束状态之外的每个状态, 至少都需要一个转移, 这样就能够知道一旦这个状态完成时流程要去向哪里. 状态可以有多个转移, 分别对应于当前状态结束时可以执行的不同的路径. 
    + 事件触发转移
    ```xml
    <!--触发phoneEntered是转移到lookupCustomer-->
    <transition on="phoneEntered" to="lookupCustomer"/>
    ```
    
    + 异常触发转移
    ```xml
    <transition
    on-exception=
      "com.springinaction.pizza.service.CustomerNotFoundException"
          to="registrationForm" />
    ```
    
    + 全局转移. 用于定义一些全局性的通用转移定义, 比如退出事件触发的转移. 
    ```xml
    <global-transitions>
          <transition on="cancel" to="endState" />
    </global-transitions>
    ```
    
####4. _流程数据_
+ 当流程从一个状态进行到另一个状态时, 它会带走一些数据. 有时候, 这些数据只需要很短的时间（可能只要展现页面给用户）. 有时候, 这些数据会在整个流程中传递并在流程结束的时候使用. 
+ 声明变量
    + 使用\<var\>元素
    ```xml
    <!--变量可以在流程的任意状态进行访问. -->
    <var name="customer" class="com.springinaction.pizza.domain.Customer"/>
    ```
    
    + 使用\<evaluate\>元素
    ```xml
    <evaluate result="viewScope.toppingsList"
          expression="T(com.springinaction.pizza.domain.Topping).asList()" />
    ```
    
    + 使用\<set\>元素
    ```xml
    <set name="flowScope.pizza"
          value="new com.springinaction.pizza.domain.Pizza()" />
    ```
    
+ 定义流程数据的作用域. Spring Web Flow定义了五种不同作用域.

| 范围         | 生命作用域和可见性                                                                           |
|--------------|----------------------------------------------------------------------------------------------|
| Conversation | 最高层级的流程开始时创建, 在最高层级的流程结束时销毁. 被最高层级的流程和其所有的子流程所共享 |
| Flow         | 当流程开始时创建, 在流程结束时销毁. 只有在创建它的流程中是可见的                             |
| Request      | 当一个请求进入流程时创建, 在流程返回时销毁                                                   |
| Flash        | 当流程开始时创建, 在流程结束时销毁. 在视图状态渲染后, 它也会被清除                           |
| View         | 当进入视图状态时创建, 当这个状态退出时销毁. 只在视图状态内是可见的                           |

+ 当使用\<var\>元素声明变量时, 变量始终是流程作用域的, 也就是在定义变量的流程内有效.
+ 当使用\<set\>或<evaluate>的时候, 作用域通过name或result属性的前缀指定. 
