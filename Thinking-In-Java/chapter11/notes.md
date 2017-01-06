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
+ 添加一组元素操作：
    + _Collection.addAll()_：这个方法相对Collections工具类提供的方法要简单一些.
    > boolean addAll(Collection<? extends E> c);
    
    + _Collections.addAll()_: 可以看到与上面的区别在于提供了一个可变输入参数，方法相对更加灵活，[英文第4版说这个操作更快，中文第四版说第一种方法更快，一脸懵逼]()。
    > public static <T> boolean addAll(Collection<? super T> c, T... elements) 
    
    + 示例如下。
    ```java
    class Snow{}
    class Powder extends Snow{}
    class Light extends Powder{}
    class Heavy extends Powder{}
    class Crusty extends Snow{}
    class Slush extends Snow{}
    public class AddingGroups {
        public static void main(String[] args) {
    
            // 注意这里新建一个ArrayList副本，而不是直接使用原Array
            Collection<Integer> collection = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
            Integer[] moreInts = {6, 7, 8, 9, 10};
    
            // 方法1
            collection.addAll(Arrays.asList(moreInts));
            // 方法2
            Collections.addAll(collection, 11, 12, 13, 14, 15);
            // 方法2
            Collections.addAll(collection, moreInts);
    
            // 这里直接使用原Array
            List<Integer> list = Arrays.asList(16, 17, 18, 19, 20);
            list.set(1, 21);
            // 这里因为Arrays.asList传回的List类底层仍然是Array类，因此无法对其大小做出更改，否则会报错，具体看下面输出去。
            //list.add(21);
          
            List<Snow> snow1 = Arrays.asList(
                    new Crusty(),
                    new Slush(),
                    new Powder()
            );
    
            //如果移除下面注释。编译器会报错：Error:(50, 41) java: incompatible types: java.util.List<net.mindview.test.Powder> cannot be converted to java.util.List<net.mindview.test.Snow>
            //List<Snow> snows = Arrays.asList(
            //        new Light(),
            //        new Heavy()
            //);
    
            List<Snow> snows = new ArrayList<Snow>();
            Collections.addAll(snows, new Light(), new Heavy());
    
            List<Snow> snow4 = Arrays.<Snow>asList(
                    new Light(), new Heavy()
            );
        }
    }
    // 如果移除list.add(21);注释，可以发现
    /* Output:
    Exception in thread "main" java.lang.UnsupportedOperationException
        at java.util.AbstractList.add(AbstractList.java:148)
        at java.util.AbstractList.add(AbstractList.java:108)
        at net.mindview.test.AddingGroups.main(AddingGroups.java:25)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at com.intellij.rt.execution.application.AppMain.main(AppMain.java:147)
    */    
    // 注释最后一行
    /* Output: 顺利编译
    */
    ```
    
    + 上面示例中涉及到Arrays.asList()的一个问题：[方法返回的List类只是提供了List的接口方法，但是底层仍然是Array类，因此无法对其包装后的容器类进行大小变更，否则会报 **UnsupportedOperationException** 运行时错误.]() 下面是官方方法注释说明. :bangbang:
    ```java
    /**
     * Returns a fixed-size list backed by the specified array.  (Changes to
     * the returned list "write through" to the array.)  This method acts
     * as bridge between array-based and collection-based APIs, in
     * combination with {@link Collection#toArray}.  The returned list is
     * serializable and implements {@link RandomAccess}.
     *
     * <p>This method also provides a convenient way to create a fixed-size
     * list initialized to contain several elements:
     * <pre>
     *     List&lt;String&gt; stooges = Arrays.asList("Larry", "Moe", "Curly");
     * </pre>
     *
     * @param <T> the class of the objects in the array
     * @param a the array by which the list will be backed
     * @return a list view of the specified array
     */
    ```
    
    + 另外一个问题是Arrays.asList()的类型推力问题：[Arrays.asList()必须要声明包装类型Arrays.<ClassName>asList()，否则只能识别出当前Array类中的顶层类，下面显示了具体错误.]() :bangbang:
    ```java
    // 下面的输出是在JDK1.6中报的错，这个问题似乎在JDK1.8这个版本被修复了，至少我用JDK1.8编译是没有问题的
    /*Output:
    Error:(50, 41) java: incompatible types: 
    java.util.List<net.mindview.test.Powder> 
    cannot be converted to java.util.List<net.mindview.test.Snow>
    */
    ```
    
    + [综合问题，显然通过 **Collections.addAll()** 方法更加灵活方便.]() :heavy_exclamation_mark:
    
    
####3. _List interface_
+ [java.util.List.subList时最好小心点.]() :bangbang:
    + 它返回原来list的从(fromIndex, toIndex)之间这一部分的视图，之所以说是视图，是因为实际上，返回的list是靠原来的list支持的。所以，你对原来的list和返回的list做的“非结构性修改”(non-structural changes)，都会影响到彼此对方。所谓的“非结构性修改”，是指不涉及到list的大小改变的修改。相反，结构性修改，指改变了list大小的修改.
    + [**如果发生结构性修改的是返回的子list，那么原来的list的大小也会发生变化**.]()
    + [**而如果发生结构性修改的是原来的list（不包括由于返回的子list导致的改变），那么返回的子list语义上将会是undefined。在AbstractList（ArrayList的父类）中，undefined的具体表现形式是抛出一个ConcurrentModificationException.**]()
    + 官方文档
    ```java
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
    ```
    
    + 示例：
    ```java
    
    ```
####4. __
####5. __
####6. __
####7. __
####8. __
####9. __