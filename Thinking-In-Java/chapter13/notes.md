###Chapter 13 : Strings  
**字符串操作是计算机程序设计中最常见的行为**

####1. _不可变_
+ String类中每一个看起来会修改String值的方法, 实际上都是创建了一个全新的String对象, 以包含修改后的字符串内容, 而最初的String对象则丝毫未动. 

####2. _+号重载_

>在JVM中, 关于String的符号重载操作实际是通过 **_StringBuilder_** 类进行实现的. 

+ 测试代码
```java
/**
 * Created by ChenZhePC on 2017/1/14.
 */
public class Concatenation {
    public static void main(String[] args) {
        String mango = "mango";
        String s = "abc" + mango + "def" + 47;
        System.out.println(s);
    }
}
```

+ 二进制码
```
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
      12: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
      15: aload_1
      16: invokevirtual #6                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
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

> 在JVM中基本所有String的符号重载操作等同于隐式调用StringBuilder, 与显示调用StringBuilder的区别在于: 
> 前者每一次操作都会新建一个StringBuilder实例, 而显示调用StringBuilder的话则只会在new时新建一个StringBuilder实例. 

+ 测试代码
```java
/**
 * Created by ChenZhePC on 2017/1/23.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */
public class WhitherStringBuilder  {

    public String implicit(String[] field){
        String result = "";
        for (int i = 0; i < field.length; i++) {
            result += field[i];
        }
        return result;
    }

    public String explicit(String[] field){
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < field.length; i++) {
            result.append(field[i]);
        }
        return result.toString();
    }
}
```

+ 二进制码
```
public class net.mindview.test.chapter13.WhitherStringBuilder {
  public net.mindview.test.chapter13.WhitherStringBuilder();
    Code:
       0: aload_0
       1: invokespecial #1                  // Method java/lang/Object."<init>":()V
       4: return

  public java.lang.String implicit(java.lang.String[]);
    Code:
       0: ldc           #2                  // String
       2: astore_2
       3: iconst_0
       4: istore_3
       5: iload_3
       6: aload_1
       7: arraylength
       8: if_icmpge     38
      11: new           #3                  // class java/lang/StringBuilder
      14: dup
      15: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
      18: aload_2
      19: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;

      22: aload_1
      23: iload_3
      24: aaload
      25: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;

      28: invokevirtual #6                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      31: astore_2
      32: iinc          3, 1
      35: goto          5
      38: aload_2
      39: areturn

  public java.lang.String explicit(java.lang.String[]);
    Code:
       0: new           #3                  // class java/lang/StringBuilder
       3: dup
       4: invokespecial #4                  // Method java/lang/StringBuilder."<init>":()V
       7: astore_2
       8: iconst_0
       9: istore_3
      10: iload_3
      11: aload_1
      12: arraylength
      13: if_icmpge     30
      16: aload_2
      17: aload_1
      18: iload_3
      19: aaload
      20: invokevirtual #5                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;

      23: pop
      24: iinc          3, 1
      27: goto          10
      30: aload_2
      31: invokevirtual #6                  // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
      34: areturn
}
```

####4. _无意识递归_

> 当覆盖由 **_Object_** 类继承的 _toString()_ 方法时.
> 如果在@Override时, 不慎调用显式或者隐式地使用了this.toString()则会导致抛出运行时异常StackOverflowError, 原因是无限递归. 
> [当需要输出对象地址时请调用父类的方法 _super.toString()_.]()

+ 测试代码
```java
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ChenZhePC on 2017/1/23.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */
public class InfiniteRecursion{

    @Override
    public String toString() {
        return "InfiniteRecursion address : " + this + "\n";
    }

    public static void main(String[] args) {
        List<InfiniteRecursion> list =
                new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(new InfiniteRecursion());
        }
        System.out.println(list);
    }
}

// 可以发现输出结果是StackOverflowError运行时异常, 这也是一个典型的递归导致的栈溢出异常
/* Output: 
Exception in thread "main" java.lang.StackOverflowError
	at java.lang.StringBuilder.append(StringBuilder.java:136)
	at net.mindview.test.chapter13.InfiniteRecursion.toString(InfiniteRecursion.java:18)
	at java.lang.String.valueOf(String.java:2994)
	at java.lang.StringBuilder.append(StringBuilder.java:131)
	at net.mindview.test.chapter13.InfiniteRecursion.toString(InfiniteRecursion.java:18)
	....
	....
    at java.lang.StringBuilder.append(StringBuilder.java:131)
    at net.mindview.test.chapter13.InfiniteRecursion.toString(InfiniteRecursion.java:18)
 */
```

####5. _String基本方法_

|              方法名              | 参数, 重载                                                                                         | 用途                                                                                                    |
|:--------------------------------:|----------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------|
| **构造方法**                     | 重载版本: 默认版本, String, StringBuilder, StringBuffer, char数组, byte数组                        | 创建String对象                                                                                          |
| **length()**                     |                                                                                                    | String中字符的个数                                                                                      |
| **charAt()**                     | int 索引位置                                                                                       | 取得String中该索引位置上的char字符                                                                      |
| **getChars(), get Bytes()**      | 起点索引, 终点索引, 目标数组, 目标数组的起始索引                                                   | 复制char或byte到目标数组中                                                                              |
| **toCharArray()**                |                                                                                                    | 生成一个char[], 包含String的所有字符                                                                    |
| **equals()**                     | 与之进行比较的String                                                                               | 比较两个String的内容是否相同                                                                            |
| **compareTo()**                  | 与之进行比较的String                                                                               | 按字典顺序比较String的内容, 如果为负数、零或正数. 注意, 大小写不等相等.                                 |
| **contains()**                   | 要搜索的CharSequence                                                                               | 如果该String对象包含参数的内容, 则返回true.                                                             |
| **contentEquals()**              | 与之进行比较的CharSequence或StringBuffer                                                           | 如果该String与参数的内容完全一致, 则返回true.                                                           |
| **equalsIgnoreCase()**           | 与之进行比较的String                                                                               | 忽略大小写, 如果两个String内容相同, 则返回true.                                                         |
| **regionMatcher()**              | 该String的索引偏移量, 另一个String及其索引偏移量, 要比较的长度, 重载版本增加了是否忽略大小写的功能 | 返回boolean结果, 以表明所比较区域是否相等.                                                              |
| **startsWith()**                 | 可能的起始String. 重载版本在参数中增加了偏移量.                                                    | 返回boolean结果, 以表明该String是否以此参数起始.                                                        |
| **endsWith()**                   | 该String可能的后缀String                                                                           | 返回boolean结果, 以表示该String是否以此参数作为后缀.                                                    |
| **indexOf(), lastIndexOf()**     | 重载版本包括: char, char与起始索引, String, String与起始索引                                       | 如果该String并不包含此参数, 就返回-1, 否则返回此参数在String中的其实索引. lastIndexOf()是从后向前搜索.  |
| **subString()(subSequence())**   | 重载版本: 起始索引, 其实索引+终点索引                                                              | 返回一个新的String对象, 以包含参数指定的子字符串.                                                       |
| **concat()**                     | 要连接的Sting                                                                                      | 返回一个新的String对象, 内容为原始String连接上输入String                                                |
| **replace()**                    | 要替换掉的字符, 用来进行替换的新字符. 也可以用一个CharSequence来替换另一个CharSequence             | 返回替换字符后的新String对象. [如果没有替换发生, 则返回原始的String对象.]()                              |
| **toLowerCase(), toUpperCase()** |                                                                                                    | 将字符串的大小写改变后, 返回一个新的String对象. [如果没有改变发生, 则返回原始的String对象.]()           |
| **trim()**                       |                                                                                                    | 将字符的大小改变后, 返回一个新String对象. [如果没有改变发生, 则返回原始的String对象.]()                 |
| **valueOf()**                    | 重载版本: Object; char[]; char[], 偏移量, 与字符个数; boolean; char; int; long; float; double      | 返回一个表示参数内容的String                                                                            |
| [**intern()**]() :bangbang:      |                                                                                                    | [为每个唯一的字符序列生成一个且仅生成一个String应用, 这个可以用来减少内容占用.]()                       |

####6. [_格式化输出_]() :heavy_exclamation_mark: 

> + System.out.format()和System.out.printf(). 效果和C中的printf方法相似, 支持格式化输入或输出. 
> + **Formatter**. 在Java中, 所有新的格式化功能都由java.util.Formatter类处理. 可以将Formatter看做一个翻译器, 它将你的格式化字符串翻译成需要的结果.
> + 格式化说明符  
>   %\[argument_index$\]\[flags\]\[width\]\[.precision\]conversion
>   + %: 必写, 占位符. 
>   + \[arument_index$\]: 可选, 表示对应参数下标. 
>   + \[flags\]: 可选, 表示数据的对齐方式, 默认是右对齐, "-"表示左对齐. 
>   + \[width\]: 可选, 表示最小长度, 如果输入不够则用空格补位. 
>   + \[.precision\]: [可选, 当类型是String时表示最大长度; 当类型是浮点型时, 表示小数点显示位数, 默认是6位, 如果小数点位数过多则四舍五入, 如果位数太少用0补位. 不能用于整数类型.]()
>   + conversion: 必写, 类型转换符. 
    
+ 类型转换符

| 类型转换字符 | 说明             |
|--------------|------------------|
| d            | 整数型           |
| c            | Unicode字符      |
| b            | Boolean值        |
| s            | String           |
| f            | 浮点型(十进制)   |
| e            | 浮点型(科学计数) |
| x            | 整数(十六进制)   |
| h            | 散列码(十六进制) |
| %            | 字符"%"          |

+ %b可以接受任何类型的转换, 但是除了boolean基础类型或Boolean包装类, [其他任意类型的只要非null转换值均为 **true**, 即使整数型0转换得到的也是 **true**.]()
+ String内置静态方法format(), 效果类似Formatter.format(), 只不过返回一个String值. 其内部是通过创建一个Formatter对象进行工作的, 所以效果等同. 但是调用书写更加简洁. 
+ 基于%x实现十六进制转储(dump)
```java
import net.mindview.util.BinaryFile;
import java.io.File;
import java.io.IOException;

/**
 * Created by ChenZhePC on 2017/1/23.
 * Description :
 *
 * @author ChenZhe
 * @author q953387601@163.com
 * @version 1.0.0
 */
public class Hex {
    public static String format(byte[] data){
        StringBuilder result =
                new StringBuilder();
        int n = 0;
        for (byte b : data) {
            if (n % 16 == 0)
                result.append(String.format("%05X: ", n));
            result.append(String.format("%02X ", b));
            n ++;
            if (n % 16 ==0)
                result.append("\n");
        }
        result.append("\n");
        return result.toString();
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0){
            System.out.println(
                    format(BinaryFile.read("Thinking-In-Java\\mindview\\target\\classes\\net\\mindview\\test\\chapter13\\Hex.class")));
        }
        else {
            System.out.println(
                    format(BinaryFile.read(new File(args[0]))));
        }
    }
}

/*
* 00000: CA FE BA BE 00 00 00 34 00 62 0A 00 05 00 32 07
00010: 00 33 0A 00 02 00 32 08 00 34 07 00 35 0A 00 36
00020: 00 37 0A 00 38 00 39 0A 00 02 00 3A 08 00 3B 0A
00030: 00 3C 00 3D 08 00 3E 0A 00 02 00 3F 09 00 40 00
00040: 41 08 00 42 0A 00 43 00 44 0A 00 15 00 45 0A 00
00050: 46 00 47 07 00 48 0A 00 12 00 49 0A 00 43 00 4A
00060: 07 00 4B 01 00 06 3C 69 6E 69 74 3E 01 00 03 28
00070: 29 56 01 00 04 43 6F 64 65 01 00 0F 4C 69 6E 65
00080: 4E 75 6D 62 65 72 54 61 62 6C 65 01 00 12 4C 6F
00090: 63 61 6C 56 61 72 69 61 62 6C 65 54 61 62 6C 65
000A0: 01 00 04 74 68 69 73 01 00 21 4C 6E 65 74 2F 6D
000B0: 69 6E 64 76 69 65 77 2F 74 65 73 74 2F 63 68 61
000C0: 70 74 65 72 31 33 2F 48 65 78 3B 01 00 06 66 6F
000D0: 72 6D 61 74 01 00 16 28 5B 42 29 4C 6A 61 76 61
000E0: 2F 6C 61 6E 67 2F 53 74 72 69 6E 67 3B 01 00 01
000F0: 62 01 00 01 42 01 00 04 64 61 74 61 01 00 02 5B
00100: 42 01 00 06 72 65 73 75 6C 74 01 00 19 4C 6A 61
.....
00680: 00 04 00 02 16 15 00 2E 00 00 00 04 00 01 00 2F
00690: 00 01 00 30 00 00 00 02 00 31
*/
```

####7. _正则表达式_

> 正则表达式提供了一种完全通用的方式, 能够解决各种字符串处理相关的问题: 匹配, 选择, 编辑以及验证. 

+ 基础
    + 需要注意的是!!在正则表达式中反斜杠\表示字符转译. 同样在Java字符串中反斜杠\也表示字符转译. 因此, 在Java中使用正则表达式, 如果想要插入一个普通反斜杠, 需要使用\\\\, 前两个和后两个在Java字符串中分别转译为单反斜杠\, 得到的\\在正则表达式中转译为一个反斜杠\. 不过换行符合制表符之类的只需要使用单反斜线: \n\t. 
    + String类内置了matches("regular expressions")方法用于最简单的正则表达式匹配. 
    + String类内置的split("regular expressions")方法可以将字符串从正则表达式匹配的地方切开. 该方法有一个重载版本, 允许限制字符串分割的次数. 
    + String类内置的replaceFirst/replaceAll/replace("regular expressions","replaced string")同样支持正则表达式匹配替换. 
    
+ 正则表达式简单介绍

| 字符         | 说明                                                  |
|--------------|-------------------------------------------------------|
| B            | 指定字符B, 其他同理                                   |
| \xhh         | 十六进制值为0xhh的字符                                |
| \uhhhh       | 十六进制表示为0xhhhh的Unicode字符                     |
| \t           | 制表符Tab                                             |
| \n           | 换行符                                                |
| \r           | 回车                                                  |
| \f           | 换页                                                  |
| \e           | 转义(Escape)                                          |
| ------       |                                                       |
| .            | 任意字符                                              |
| \[abc]        | 包含a、b和c的任意字符(与a|b|c作用相同)                |
| \[^abc]       | 除了a、b和c之外的任何字符(否定)                       |
| \[a-zA-Z]     | 从a到z或从A到Z的任意字符(范围)                        |
| \[abc\[hij]]   | 任意a、b、c、h、i和j字符(与a|b|c|h|i|j作用相同)(合并) |
| \[a-z&&\[hij]] | 任意h、i或j(相交)                                     |
| \s           | 空白符(空格、tab、换行和回车)                         |
| \S           | 非空白符(\[^\s])                                       |
| \d           | 数字\[0-9]                                             |
| \D           | 非数字\[^0-9]                                          |
| \w           | 词字母\[a-zA-Z0-9]                                     |
| \W           | 非词字母\[^\w]                                         |
|              |                                                       |
| 逻辑操作符   | --------------------------------------                |
| XY           | Y跟在X后面                                            |
| X|Y          | X或Y                                                  |
| (X)          | 捕获组. 可以在表达式中庸\i引用第i个捕获组             |
|              |                                                       |
| 边界匹配符   | --------------------------------------                |
| ^            | 一行的起始                                            |
| $            | 一行的结束                                            |
| \b           | 词边界                                                |
| \B           | 非词边界                                              |
| \G           | 前一个匹配的结束                                      |

+ 量词

贪婪型: 会为所有可能的模式发现尽可能多的匹配. 

勉强型: 量词匹配满足模式所需的最少字符数. 

占有型: 只有在Java语言中才可用. 当正则表达式被应用于字符串时, 会产生相当多的状态, 一边在匹配失败时可以回溯. 而占有型量词并不保存这些中间状态, 因此它们可以防止回溯. 这个常常用于防止正则表达式失控, 因此可以使正则表达式执行起来更加有效. 

| 贪婪型  | 勉强型   | 占有型   | 如何匹配              |
|---------|----------|----------|-----------------------|
|  X?     | X??      | X?+      | 一个或零个X           |
| X*      | X*?      | X*+      | 零个或多个X           |
| X+      | X+?      | X++      | 一个或多个X           |
| X{n}    | X{n}?    | X{n}+    | 恰好n次X              |
| X{n,}   | X{n,}?   | X{n,}+   | 至少n次X              |
| X{n, m} | X{n, m}? | X{n, m}+ | X至少n次, 且不超过m次 |

+ 多数正则表达式都接受CharSequence类型的参数. 
+ 使用java.util.regex包进行字符串检索
    + 1. 首先使用static Pattern.compile()根据输入的String类型生成一个Pattern对象. 
    + 2. 调用生成的Pattern对象的matcher()方法进行正则表达式匹配, 并生成一个Matcher对象. 
    + 3. Matcher对象提供了很多基于当前Pattern与String的相关操作, 比如替换操作等. 
    
```java
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * \* Created with Chen Zhe on 1/23/2017.
 * \* Description: Input: abcabcabcdefabc abc+ (abc)+ (abc){2,}
 * \* @author ChenZhe
 * \* @author q953387601@163.com
 * \* @version 1.0.0
 * \
 */
public class TestRegularExpression {
    public static void main(String[] args) {
        if (args.length < 2){
            System.out.println("Usage: \njava TestRegularExpression " +
                                "CharacterSequence regularExpression+");
            System.exit(0);
        }
        System.out.println("Input: \"" + args[0] + "\"");
        for (String arg : args) {
            System.out.println("Regular expression:  \"" + arg + "\"");
            Pattern pattern = Pattern.compile(arg);
            Matcher matcher = pattern.matcher(args[0]);
            while (matcher.find()){
                System.out.println("Match \"" + matcher.group() + "\" at positions " +
                                    matcher.start() + "-" + (matcher.end() - 1));
            }
        }
    }
}

/* Output:
Input: "abcabcabcdefabc"
Regular expression:  "abcabcabcdefabc"
Match "abcabcabcdefabc" at positions 0-14
Regular expression:  "abc+"
Match "abc" at positions 0-2
Match "abc" at positions 3-5
Match "abc" at positions 6-8
Match "abc" at positions 12-14
Regular expression:  "(abc)+"
Match "abcabcabc" at positions 0-8
Match "abc" at positions 12-14
Regular expression:  "(abc){2,}"
Match "abcabcabc" at positions 0-8
*/
```

+ Matcher类相关操作

| 方法名               | 用途                                                                                                                    |
|----------------------|-------------------------------------------------------------------------------------------------------------------------|
| Matcher.matches()    | 对整个字符串进行匹配,只有整个字符串都匹配了才返回true                                                                   |
| Matcher.lookingAt()  | 对字符串进行匹配,只有匹配到的字符串在最前面才返回true                                                                   |
| Matcher.find()       | 对字符串进行匹配, 匹配到的字符串可以在任何位置.                                                                         |
| Matcher.find(int)    | 从输入位置进行匹配                                                                                                      |
| Matcher.start()      | 返回匹配到的子字符串在字符串中的索引位置(注意从这开始到一下, 所有操作必须在匹配完才可以, 否则会报IllegalStateException) |
| Matcher.end()        | 返回匹配到的子字符串的最后一个字符在字符串中的索引位置+1值.                                                             |
| Matcher.group()      | 返回匹配到的子字符串                                                                                                    |
| Matcher.groupCount() | 返回分组数                                                                                                              |
| Matcher.start(int)   | Matcher.start()的重载, 匹配时选择对应输入值的分组                                                                       |
| Matcher.end(int)     | Matcher.end()的重载, 匹配时选择对应输入值的分组                                                                         |
| Matcher.group(int)   | Matcher.group()的重载, 匹配对应输入值的分组                                                                             |

+ [Pattern.compile(String regex, int flag)的使用.]() :bangbang:

| 编译标记                     | 效果                                                                                                                                                                                         |
|------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Pattern.CANON_EQ             | 当且仅当两个字符的"正规分解(canonical decomposition)"都完全相同的情况下, 才认定匹配. 比如用了这个标志之后, 表达式"a\u030A"会匹配"?". 默认情况下, 不考虑"规范相等性(canonical equivalence)".  |
| [Pattern.CASE_INSENSITIVE(?i)]() | 默认情况下, 大小写不明感的匹配只适用于US-ASCII字符集. 这个标志能让表达式忽略大小写进行匹配. 要想对Unicode字符进行大小不明感的匹 配, 只要将UNICODE_CASE与这个标志合起来就行了.                |
| [Pattern.COMMENTS(?x)]()         | 在这种模式下, 匹配时会忽略(正则表达式里的)空格字符(译者注: 不是指表达式里的"\\s", 而是指表达式里的空格, tab, 回车之类). 注释从#开始, 一直到这行结束. 可以通过嵌入式的标志来启用Unix行模式.   |
| Pattern.DOTALL(?s)           | 在这种模式下, 表达式'.'可以匹配任意字符, 包括表示一行的结束符. 默认情况下, 表达式'.'不匹配行的结束符.                                                                                        |
| [Pattern.MULTILINE(?m)]()        | 在这种模式下, '^'和'$'分别匹配一行的开始和结束. 此外, '^'仍然匹配字符串的开始, '$'也匹配字符串的结束. 默认情况下, 这两个表达式仅仅匹配字符串的开始和结束.                                    |
| Pattern.UNICODE_CASE(?u)     | 在这个模式下, 如果你还启用了CASE_INSENSITIVE标志, 那么它会对Unicode字符进行大小写不明感的匹配. 默认情况下, 大小写不敏感的匹配只适用于US-ASCII字符集.                                         |
| Pattern.UNIX_LINES(?d)       | 在这个模式下, 只有'\n'才被认作一行的中止, 并且与'.', '^', 以及'$'进行匹配.                                                                                                                   |

+ String.split(int limited): limited输入参数限制了分割数组的最大长度

+ [StringBuffer相关扩展]() :bangbang:
    + matcher.appendReplacement(StringBuffer sb, String replacement): 在输入字符串中知道匹配位置, 然后替换为replacement, 并将其压入sb
    + matcher.appendTail(StringBuffer sb): 一般是使用过上面的方法后, 将字符串中最后没有匹配的子字符串压入sb的尾部. 
    + 示例
    ```java
    import java.util.regex.Matcher;
    import java.util.regex.Pattern;
    
    /**
     * \* Created with Chen Zhe on 1/24/2017.
     * \* Description:
     * \* @author ChenZhe
     * \* @author q953387601@163.com
     * \* @version 1.0.0
     * \
     */
    public class TestStringBufferRegex {
        public static void main(String[] args) {
            String input =
                    "Here's a block of text to use as input to " +
                            "the regular expression matcher. Note that we'll " +
                            "first extract the block of text by looking for " +
                            "the special delimiters, then process the " +
                            "extracted block";
            StringBuffer stringBuffer = new StringBuffer();
            Matcher matcher = Pattern
                                    .compile("[aeiou]")
                                    .matcher(input);
            while (matcher.find())
                matcher.appendReplacement(
                        stringBuffer,
                        matcher.group().toUpperCase());
            matcher.appendTail(stringBuffer);
            System.out.println(stringBuffer);
        }
    }
    
    /* Output:
    * HErE's A blOck Of tExt tO UsE As
    * InpUt tO thE rEgUlAr ExprEssIOn
    * mAtchEr. NOtE thAt wE'll fIrst
    * ExtrAct thE blOck Of tExt by
    * lOOkIng fOr thE spEcIAl dElImItErs,
    * thEn prOcEss thE ExtrActEd blOck
    */
    ```
    
+ Matcher重置
    + Matcher.reset(): 将Matcher对象重新设置到当前字符序列的起始位置. 
    + Matcher.reset(CharSequence newInput): 将字符串newInput替换当前Matcher对象中的字符序列. 

####8. _扫描输入_
+ 一般操作是通过按行读取, 之后进行分词, 并进行响应的转换从而获得想要的数据. 
+ Scanner类默认的定界符是空格, 但是可以通过useDelimiter(String pattern)从而自定义定界符, 支持正则表达式. 
+ Scanner.next(String pattern)和Scanner.hasNext(String pattern)可以接受字符串类型的正则表达式, 从而进行限定扫描. 

####9. _StringTokenizer_

> 这是SE4之前用于字符串分词的类, 现在基本已经弃用了, 至少这本书是这样介绍的, 因为他在使用复杂模式分割是比较困难. 

