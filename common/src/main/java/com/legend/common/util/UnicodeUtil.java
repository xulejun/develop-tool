package com.legend.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * unicode 解析工具
 *
 * @author legend xu
 * @date 2023/5/29
 */
public class UnicodeUtil {
    private static final Pattern PATTERN = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");

    /**
     * unicode解码 将Unicode的编码转换为中文
     *
     * @param unicode
     * @return 转换之后的内容
     */
    public static String unicodeDecode(String unicode) {
        Matcher matcher = PATTERN.matcher(unicode);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            unicode = unicode.replace(matcher.group(1), ch + "");
        }
        return unicode;
    }

    /**
     * @param str
     * @return
     * @Title: unicodeEncode
     * @Description: unicode编码 将中文字符转换成Unicode字符
     */
    public static String unicodeEncode(String str) {
        char[] utfBytes = str.toCharArray();
        String unicodeBytes = "";
        for (int i = 0; i < utfBytes.length; i++) {
            String hexB = Integer.toHexString(utfBytes[i]);
            if (hexB.length() <= 2) {
                hexB = "00" + hexB;
            }
            unicodeBytes = unicodeBytes + "u" + hexB;
        }
        return unicodeBytes;
    }
}
