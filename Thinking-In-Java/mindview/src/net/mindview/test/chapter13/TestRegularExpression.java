package net.mindview.test.chapter13;

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
            System.out.println("Regular expression： \"" + arg + "\"");
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
Regular expression： "abcabcabcdefabc"
Match "abcabcabcdefabc" at positions 0-14
Regular expression： "abc+"
Match "abc" at positions 0-2
Match "abc" at positions 3-5
Match "abc" at positions 6-8
Match "abc" at positions 12-14
Regular expression： "(abc)+"
Match "abcabcabc" at positions 0-8
Match "abc" at positions 12-14
Regular expression： "(abc){2,}"
Match "abcabcabc" at positions 0-8
*/