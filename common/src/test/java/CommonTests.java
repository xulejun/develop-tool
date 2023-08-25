import cn.hutool.core.codec.Base64;
//import com.alibaba.fastjson2.JSONArray;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
//import com.alibaba.fastjson2.JSONObject;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.legend.common.compress.CompressUtil;
import com.legend.common.entity.User;
import com.legend.common.util.UnicodeUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.SetUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.similarity.JaroWinklerDistance;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.legend.common.util.JsonReplaceUtil.jsonFilter;


/**
 * @author legend xu
 * @date 2023/4/21
 */
@Slf4j
public class CommonTests {

    public static void main(String[] args) throws Exception {
        // 完全一致
        String sku3 = "幽兰 肉 园";
        String submit3 = "一米幽兰多肉园";
//        // 手动修改，完全不一致
//        String sku1 = "客运段对面沙县小吃";
//        String submit1 = "赖旺庄小珍特产";
//        // ocr 识别 sku 有误（1-3字符串）
//        String sku2 = "春英";
//        String submit2 = "春英";
//        // 未选择 sku 自提点
//        String sku3 = "客运段对面沙县小吃";
//        String submit3 = "切换自提点";
//        // sku 自提点截取（
//        String sku4 = "门中超市 (三期)撒的撒的撒打算大的";
//        String submit4 = "门口超市(三";
//        // sku 带 ...
//        String sku5 = "银泰广场 (董师傅) 自提点撒大大的撒打算大";
//        String submit5 = "银泰广场(董师傅)...";

//        System.out.println("完全一致：" + jaroWinkler(sku, submit));
//        System.out.println("手动修改，完全不一致：" + jaroWinkler(sku1, submit1));
//        System.out.println("未选择 sku 自提点：" + jaroWinkler(sku3, submit3));
//        System.out.println("sku 自提点截取（：" + jaroWinkler(sku4, submit4));
//        System.out.println("sku 带 ..." + jaroWinkler(sku5, submit5));
//        System.out.println("ocr 识别 sku 有误（1-3字符串）：" + jaroWinkler(sku2, submit2));
//
//        System.out.println();
//        System.out.println("完全一致：" + levenshtein(sku, submit));
//        System.out.println("手动修改，完全不一致：" + levenshtein(sku1, submit1));
//        System.out.println("未选择 sku 自提点：" + levenshtein(sku3, submit3));
//        System.out.println("sku 自提点截取（：" + levenshtein(sku4, submit4));
//        System.out.println("sku 带 ..." + levenshtein(sku5, submit5));
//        System.out.println("ocr 识别 sku 有误（1-3字符串）：" + levenshtein(sku2, submit2));
//
//        System.out.println();
//        System.out.println("完全一致：" + cos(sku, submit));
//        System.out.println("手动修改，完全不一致：" + cos(sku1, submit1));
////        System.out.println("未选择 sku 自提点：" + levenshtein(sku3, submit3));
//        System.out.println("sku 自提点截取（：" + cos(sku4, submit4));
//        System.out.println("sku 带 ..." + cos(sku5, submit5));
//        System.out.println("ocr 识别 sku 有误（1-3字符串）：" + cos(sku2, submit2));
        sqlGenerate();

    }

    private static void sqlGenerate() throws Exception {
        // 输出的sql
        File deleteFile = new File("C:\\Users\\DELL\\Desktop\\alter.sql");
        FileOutputStream fileOutputStream = new FileOutputStream(deleteFile);

        // 解析的Excel文件
//        File parseFile = new File("/Users/xuxing12/Documents/test.xlsx");
        File parseFile = new File("/Users/xuxing12/Documents/not_similar-xuxing.xlsx");

        // Excel页数（从0开始）
        int sheetNo = 0;
        Workbook workbook = WorkbookFactory.create(parseFile);
        Sheet sheet = workbook.getSheetAt(sheetNo);
        int lastRowNum = sheet.getLastRowNum();

        // 行索引从0开始
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            // 列索引从0开始
            String sku = row.getCell(0).getStringCellValue();
            String submit = row.getCell(1).getStringCellValue();
            // 对原数据做处理，只保留中文数字和字母
            sku = sku.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");
            submit = submit.replaceAll("[^a-zA-Z0-9\\u4e00-\\u9fa5]", "");

//            if (StrUtil.isBlank(sku) || StrUtil.isBlank(submit)) {
//                break;
//            }
            double similar = jaroWinkler(sku, submit);
//            System.out.println(similar);
//            Cell cell = row.createCell(2);
//            cell.setCellValue(similar);
            System.out.println(similar);
        }
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