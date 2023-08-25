package com.legend.common.util;

import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 字符相似度比较
 *
 * @author legend xu
 * @date 2023/8/25
 */
public class StringSimilarUtil {

    public static void main(String[] args) {
        String sku = "幽兰 肉 园";
        String submit = "一米幽兰多肉园";
        // 对原数据做处理，只保留中文数字和字母
        sku = sku.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
        submit = submit.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
        double similar = jaroWinkler(sku, submit);
        System.out.println(similar);
    }

    /**
     * Jaro-Winkler
     *
     * @param x
     * @param y
     * @return
     */
    public static double jaroWinkler(String x, String y) {
        if (x.length() < 2 || y.length() < 2) {
            return 0.1;
        }
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

    public static float cos(String a, String b) {
        if (a == null || b == null) {
            return 0F;
        }
        Set<Integer> aChar = a.chars().boxed().collect(Collectors.toSet());
        Set<Integer> bChar = b.chars().boxed().collect(Collectors.toSet());

// 统计字频
        Map<Integer, Integer> aMap = new HashMap<>();
        Map<Integer, Integer> bMap = new HashMap<>();
        for (Integer a1 : aChar) {
            aMap.put(a1, aMap.getOrDefault(a1, 0) + 1);
        }
        for (Integer b1 : bChar) {
            bMap.put(b1, bMap.getOrDefault(b1, 0) + 1);
        }

// 向量化
        Set<Integer> union = SetUtils.union(aChar, bChar);
        int[] aVec = new int[union.size()];
        int[] bVec = new int[union.size()];
        List<Integer> collect = new ArrayList<>(union);
        for (int i = 0; i < collect.size(); i++) {
            aVec[i] = aMap.getOrDefault(collect.get(i), 0);
            bVec[i] = bMap.getOrDefault(collect.get(i), 0);
        }

        // 分别计算三个参数
        int p1 = 0;
        for (int i = 0; i < aVec.length; i++) {
            p1 += (aVec[i] * bVec[i]);
        }

        float p2 = 0f;
        for (int i : aVec) {
            p2 += (i * i);
        }
        p2 = (float) Math.sqrt(p2);

        float p3 = 0f;
        for (int i : bVec) {
            p3 += (i * i);
        }
        p3 = (float) Math.sqrt(p3);

        return ((float) p1) / (p2 * p3);
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
