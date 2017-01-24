package net.mindview.test.chapter13;

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