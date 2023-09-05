package com.legend.common.hutool;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;

/**
 * hutool比较工具的使用
 *
 * @author xlj
 * @date 2021/5/8 10:59
 */
public class CompareUse {
    public static void main(String[] args) {
        String str1 = "朝阳区安达街旺座国际商务广场斜对面（近轻轨3号线南昌路站）";
        String str2 = "朝阳区安袄街旺座国际商务广场斜对面(祈轻劲3 地图/导航号线南昌路站)吉大一院、文化广场";
        // 计算两个字符串的相似度
        double similar = StrUtil.similar(str1, str2);
        System.out.println("hutool 字符串相似度为：" + similar);

        System.out.println("jaro-winkler：" + jaroWinkler(str1, str2));

    }

    /**
     * Jaro-Winkler 字符串相似度匹配
     *
     * @param x
     * @param y
     * @return
     */
    public static double jaroWinkler(String x, String y) {
        JaroWinklerDistance distance = new JaroWinklerDistance();
        return 1 - distance.apply(x, y);
    }

    /**
     * levenshtein
     *
     * @param x
     * @param y
     * @return
     */
    public static double levenshtein(String x, String y) {
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            // 如果需要，可以选择忽略大小写
            return (maxLength - StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

    /**
     * 汉明距离，等长字符串计算
     *
     * @param a
     * @param b
     * @return
     */
    public static float hamming(String a, String b) {
        if (a == null || b == null) {
            return 0f;
        }
        if (a.length() != b.length()) {
            return 0f;
        }

        int disCount = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                disCount++;
            }
        }
        return (float) disCount / (float) a.length();
    }
}
