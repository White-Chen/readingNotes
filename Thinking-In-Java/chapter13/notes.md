###Chapter 13 : Strings  
**字符串操作是计算机程序设计中最常见的行为**

####1. _不可变_
+ String类中每一个看起来会修改String值的方法，实际上都是创建了一个全新的String对象，以包含修改后的字符串内容，而最初的String对象则丝毫未动。

####2. _+号重载_
    
    
+ 二进制码
```
javap -c Concatenation.class
Compiled from "Concatenation.java"
public class net.mindview.test.chapter13.Concatenation {
  public net.mindview.test.chapter13.Concatenation();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public static void main(java.lang.String[]);
    Code:
       0: ldc           #2                  // String mango
       2: astore_1
       3: new           #3                  // class java/lang/StringBuilder
       6: dup
       7: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
      10: ldc           #5                  // String abc
      12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/Stri
ngBuilder;
      15: aload_1
      16: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/Stri
ngBuilder;
      19: ldc           #7                  // String def
      21: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      24: bipush        47
      26: invokevirtual #8                  // Method java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
      29: invokevirtual #9                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      32: astore_2
      33: getstatic     #10                 // Field java/lang/System.out:Ljava/io/PrintStream;
      36: aload_2
      37: invokevirtual #11                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
      40: return
}
```

####3. _StringBuilder_


####4. _无意识递归_


####5. _String基本方法_

|              方法名              | 参数, 重载                                                                                         | 用途                                                                                                    |
|:--------------------------------:|----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| **构造方法**                     | 重载版本: 默认版本, String, StringBuilder, StringBuffer, char数组, byte数组                        | 创建String对象                                                                                          |
| **length()**                     |                                                                                                    | String中字符的个数                                                                                      |
| **charAt()**                     | int 索引位置                                                                                       | 取得String中该索引位置上的char字符                                                                      |
| **getChars(), get Bytes()**      | 起点索引, 终点索引, 目标数组, 目标数组的起始索引                                                   | 复制char或byte到目标数组中                                                                              |
| **toCharArray()**                |                                                                                                    | 生成一个char[], 包含String的所有字符                                                                    |
| **equals()**                     | 与之进行比较的String                                                                               | 比较两个String的内容是否相同                                                                            |
| **compareTo()**                  | 与之进行比较的String                                                                               | 按字典顺序比较String的内容，如果为负数、零或正数。注意，大小写不等相等。                                |
| **contains()**                   | 要搜索的CharSequence                                                                               | 如果该String对象包含参数的内容，则返回true。                                                            |
| **contentEquals()**              | 与之进行比较的CharSequence或StringBuffer                                                           | 如果该String与参数的内容完全一致，则返回true.                                                           |
| **equalsIgnoreCase()**           | 与之进行比较的String                                                                               | 忽略大小写，如果两个String内容相同，则返回true。                                                        |
| **regionMatcher()**              | 该String的索引偏移量，另一个String及其索引偏移量，要比较的长度，重载版本增加了是否忽略大小写的功能 | 返回boolean结果，以表明所比较区域是否相等。                                                             |
| **startsWith()**                 | 可能的起始String。重载版本在参数中增加了偏移量。                                                   | 返回boolean结果，以表明该String是否以此参数起始。                                                       |
| **endsWith()**                   | 该String可能的后缀String                                                                           | 返回boolean结果，以表示该String是否以此参数作为后缀。                                                   |
| **indexOf()，lastIndexOf()**     | 重载版本包括: char，char与起始索引，String，String与起始索引                                       | 如果该String并不包含此参数，就返回-1，否则返回此参数在String中的其实索引。lastIndexOf()是从后向前搜索。 |
| **subString()(subSequence())**   | 重载版本：起始索引，其实索引+终点索引                                                              | 返回一个新的String对象，以包含参数指定的子字符串。                                                      |
| **concat()**                     | 要连接的Sting                                                                                      | 返回一个新的String对象，内容为原始String连接上输入String                                                |
| **replace()**                    | 要替换掉的字符，用来进行替换的新字符。也可以用一个CharSequence来替换另一个CharSequence             | 返回替换字符后的新String对象。[如果没有替换发生，则返回原始的String对象.]()                              |
| **toLowerCase(), toUpperCase()** |                                                                                                    | 将字符串的大小写改变后，返回一个新的String对象。[如果没有改变发生，则返回原始的String对象.]()           |
| **trim()**                       |                                                                                                    | 将字符的大小改变后，返回一个新String对象。[如果没有改变发生，则返回原始的String对象.]()                 |
| **valueOf()**                    | 重载版本: Object；char[]; char[], 偏移量，与字符个数；boolean；char；int；long；float；double      | 返回一个表示参数内容的String                                                                            |
| [**intern()**]() :bangbang:      |                                                                                                    | [为每个唯一的字符序列生成一个且仅生成一个String应用，这个可以用来减少内容占用.]()                       |

####6. _格式化输出_


####7. _正则表达式_


####8. _扫描输入_


####9. _StringTokenizer_

