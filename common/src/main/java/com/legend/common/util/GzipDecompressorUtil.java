package com.legend.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.zip.GZIPInputStream;

// 解码 base64，gzip解压
public class GzipDecompressorUtil {
    public static void main(String[] args) throws Exception{
        String filePath3 = "/Users/xuxing12/IdeaProjects/develop-tool/common/src/test/resources/test3.txt";
        String outJsonPath = "/Users/xuxing12/IdeaProjects/develop-tool/common/src/main/java/com/legend/common/format/JsonFormat2.json";
        FileWriter fileWriter = new FileWriter(outJsonPath);
        String base64GzipData = new String(Files.readAllBytes(Paths.get(filePath3)));
        try {
            // 解码 Base64 数据
            byte[] compressedData = Base64.getDecoder().decode(base64GzipData);
            // 解压缩 GZIP 数据
            byte[] decompressedData = decompressGzip(compressedData);
            // 将解压缩后的数据转换为字符串
            String result = new String(decompressedData, "UTF-8");
            // 输出
            fileWriter.write(result);
            System.out.println("解压缩后的数据: " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] decompressGzip(byte[] compressedData) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(compressedData);
        GZIPInputStream gzipInputStream = new GZIPInputStream(byteArrayInputStream);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        byte[] buffer = new byte[1024];
        int len;
        while ((len = gzipInputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
        }

        gzipInputStream.close();
        byteArrayInputStream.close();
        byteArrayOutputStream.close();

        return byteArrayOutputStream.toByteArray();
    }
}
