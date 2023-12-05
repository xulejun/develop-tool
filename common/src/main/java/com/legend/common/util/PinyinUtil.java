package com.legend.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * @author 602
 * @date 2023/12/5
 */
public class PinyinUtil {
    public static void main(String[] args) {
        String chinese = "重庆市";
        String pinyin = chineseToPinyin(chinese);
        String pinyin1 = toPinyinInitials(chinese);
        // zhongqingshi
        System.out.println(pinyin);
        // zqs
        System.out.println(pinyin1);
    }

    public static String chineseToPinyin(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        char[] chars = chinese.toCharArray();
        for (char c : chars) {
            try {
                String[] arr = PinyinHelper.toHanyuPinyinStringArray(c, format);
                if (arr == null || arr.length == 0) {
                    pinyin.append(c);
                } else {
                    pinyin.append(arr[0]);
                }
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                e.printStackTrace();
            }
        }
        return pinyin.toString();
    }


    /**
     * 拼音首字母-小写
     *
     * @param chinese
     * @return
     */
    public static String toPinyinInitials(String chinese) {
        StringBuilder pinyin = new StringBuilder();
        char[] chars = chinese.toCharArray();
        for (char aChar : chars) {
            String[] pinyinArray = PinyinHelper.toHanyuPinyinStringArray(aChar);
            if (pinyinArray != null && pinyinArray.length > 0) {
                pinyin.append(pinyinArray[0].charAt(0));
            } else {
                pinyin.append(aChar);
            }
        }
        return pinyin.toString();
    }

}
