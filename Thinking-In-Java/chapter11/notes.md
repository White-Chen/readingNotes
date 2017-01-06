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


####3. _List interface_
+ [java.util.List.subList时最好小心点.]() :bangbang:
    + [它返回原来list的从(fromIndex, toIndex)之间这一部分的视图，之所以说是视图，是因为实际上，返回的list是靠原来的list支持的。所以，你对原来的list和返回的list做的“非结构性修改”(non-structural changes)，都会影响到彼此对方。所谓的“非结构性修改”，是指不涉及到list的大小改变的修改。相反，结构性修改，指改变了list大小的修改.]() 
    + [**如果发生结构性修改的是返回的子list，那么原来的list的大小也会发生变化**.]()
    + [**而如果发生结构性修改的是原来的list（不包括由于返回的子list导致的改变），那么返回的子list语义上将会是undefined。在AbstractList（ArrayList的父类）中，undefined的具体表现形式是抛出一个ConcurrentModificationException.**]()
    + 官方文档
    >>>
        /**
            * Returns a view of the portion of this list between the specified
            * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.  (If
            * {@code fromIndex} and {@code toIndex} are equal, the returned list is
            * empty.)  The returned list is backed by this list, so non-structural
            * changes in the returned list are reflected in this list, and vice-versa.
            * The returned list supports all of the optional list operations.
            *
            * <p>This method eliminates the need for explicit range operations (of
            * the sort that commonly exist for arrays).  Any operation that expects
            * a list can be used as a range operation by passing a subList view
            * instead of a whole list.  For example, the following idiom
            * removes a range of elements from a list:
            * <pre>
            *      list.subList(from, to).clear();
            * </pre>
            * Similar idioms may be constructed for {@link #indexOf(Object)} and
            * {@link #lastIndexOf(Object)}, and all of the algorithms in the
            * {@link Collections} class can be applied to a subList.
            *
            * <p>The semantics of the list returned by this method become undefined if
            * the backing list (i.e., this list) is <i>structurally modified</i> in
            * any way other than via the returned list.  (Structural modifications are
            * those that change the size of this list, or otherwise perturb it in such
            * a fashion that iterations in progress may yield incorrect results.)
            *
            * @throws IndexOutOfBoundsException {@inheritDoc}
            * @throws IllegalArgumentException {@inheritDoc}
            */
    >>>
    
    + 示例：
    ```java

    ```
####4. __
####5. __
####6. __
####7. __
####8. __
####9. __