###Chapter 11 : Holding Your Objects
####1. _容器_
+ Java在java.util包中为我们提供了一组预置的容器类以供“保存一组对象”，不同于数组的定长，容器类是不定长的可以自动扩容。
+ 以下是它们的关系图：
![](pics/container.png)
+ 如上容器类按照具体用途和用法，划分为两种不同的概念
    + **Collection(接口)**：一个独立元素的序列。这些元素都服从一条或多条规则。
        + **List(Collection的子接口)**：按照插入顺序保存元素。
        + **Set(Collection的子接口)**：不能有重复元素。
        + **Queue(Collection的子接口)**：按照队列规则来确定对象产生的顺序。
    + **Map(接口)**：一组成对的“键值对”对象，允许使用键来查找值。映射表允许我们使用另一个对象查找某个对应对象。因为是键相当于值的索引，因此需要遵循[索引唯一]()原则。
    
+ 类型安全容器：通过类型约束可以在[编译时]()检查容器相关操作是否类型安全，而不是在[运行时]()通过类型检查再报错。[需要注意的对于基础类型，应该使用它们的包装类进行声明.]()
    > Collection<ClassName> collectinon = new ArrayList<ClassName>(); 
    > Map<ClassName> map = new HashMap<ClassName,ClassName>();

####2. _Collection interface_
+ 添加元素操作：_Collections.addAll()_方法可以接受一个Collectino对象；一个Array对象；或可变参数。
    ```java
   
    ```


####3. __
####4. __
####5. __
####6. __
####7. __
####8. __
####9. __