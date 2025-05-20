package com.legend.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

// 解码 base64，gzip解压
public class GzipUtil {
    public static void main(String[] args) throws Exception {
        String filePath3 = "/Users/xuxing12/IdeaProjects/develop-tool/common/src/test/resources/test3.txt";
        String outJsonPath = "/Users/xuxing12/IdeaProjects/develop-tool/common/src/main/java/com/legend/common/format/JsonFormat2.json";
        FileWriter fileWriter = new FileWriter(outJsonPath);
        String base64GzipData = new String(Files.readAllBytes(Paths.get(filePath3)));
        try {
            // 解压缩 GZIP 数据
            String result = decompress(base64GzipData);
            // 输出
            fileWriter.write(result);
            System.out.println("解压缩后的数据: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String decompress(String compressedStr) throws IOException {
        if (compressedStr == null || compressedStr.isEmpty()) return compressedStr;

        byte[] compressedBytes = Base64.getDecoder().decode(compressedStr);
        try (ByteArrayInputStream bis = new ByteArrayInputStream(compressedBytes);
             GZIPInputStream gzip = new GZIPInputStream(bis);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {

            byte[] buffer = new byte[1024];
            int len;
            while ((len = gzip.read(buffer)) != -1) {
                bos.write(buffer, 0, len);
            }
            return new String(bos.toByteArray(), "UTF-8");
        }
    }

    public static String compress(String inputStr) throws IOException {
        if (inputStr == null || inputStr.isEmpty()) return inputStr;

        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             GZIPOutputStream gzip = new GZIPOutputStream(bos)) {

            gzip.write(inputStr.getBytes("UTF-8"));
            gzip.finish();
            return Base64.getEncoder().encodeToString(bos.toByteArray());
        }
    }

}
