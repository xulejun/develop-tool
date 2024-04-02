package com.legend.crawler.test;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONPath;
import com.alibaba.fastjson2.JSONReader;
import com.google.common.collect.Maps;
import com.legend.crawler.http.OkHttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Random;

/**
 * @author 602
 * @date 2024/4/2
 */
@Slf4j
public class HttpTest {

    public static void main(String[] args) throws Exception {
        String productUrl = "https://www.cto1drq49p.com/pt/productPangolinTask";
        String appKey = "61C4A";
        String appSecret = "B7F65F4A858348EFACD10159409A82A7";
        String account = "x12test";
        String version = "5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36";
        String mobileType = "MacIntel";
        String cityName = "南昌市";
        String functionIndex = "opgnHt7SwvNdY58vV1vbl77cUuD0YiSx";
        String ts = getTimestamp();
        String nonce = getNonce();
        String sign = getSign(productUrl, appKey, appSecret, ts, nonce);
        HashMap<@Nullable String, @Nullable String> params = Maps.newHashMap();
        params.put("appkey", appKey);
        params.put("ts", ts);
        params.put("nonce", nonce);
        params.put("sign", sign);
        JSONObject body = new JSONObject();
        body.put("account", account);
        body.put("cityName", cityName);
        body.put("functionIndex", functionIndex);
        body.put("mobileType", mobileType);
        body.put("version", version);
        String response = OkHttpUtil.postJson(productUrl, params, body.toString());
        log.info("提交任务请求响应：{}", response);

        JSONObject jsonObject = JSON.parseObject(response);
        String transactionId = jsonObject.getString("transactionId");
        String functionName = jsonObject.getJSONArray("functionNames").getString(0);
        String consumerUrl = "https://www.cto1drq49p.com/pt/consumePangolinTask";
        String sign2 = getSign(consumerUrl, appKey, appSecret, ts, nonce);
        HashMap<@Nullable String, @Nullable String> params2 = Maps.newHashMap();
        params2.put("appkey", appKey);
        params2.put("ts", ts);
        params2.put("nonce", nonce);
        params2.put("sign", sign2);
        JSONObject body2 = new JSONObject();
        body2.put("transactionId", transactionId);
        body2.put("account", account);
        body2.put("version", version);
        body2.put("mobileType", mobileType);
        body2.put("functionName", functionName);
        body2.put("cityName", cityName);
        String response2 = OkHttpUtil.postJson(consumerUrl, params2, body2.toString());
        log.info("获取任务：{}", response2);
    }

    public static String getTimestamp() {
        return Long.toString(System.currentTimeMillis());
    }

    public static String getNonce() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 10; i++) {
            int number = random.nextInt(62);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }

    public static String getSign(String url, String appKey, String appSecret, String ts, String nonce) {
        StringBuffer sb = new StringBuffer(url);
        sb.append("&").append(appKey)
                .append("&").append(appSecret)
                .append("&").append(ts)
                .append("&").append(nonce);
        String str = sb.toString();

        final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

        byte[] digest = null;
        try {
            MessageDigest md5 = MessageDigest.getInstance("md5");
            digest = md5.digest(str.getBytes("utf-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        int len = digest.length;
        char[] out = new char[len << 1];//len*2
        for (int i = 0, j = 0; i < len; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & digest[i]) >>> 4];// 高位
            out[j++] = DIGITS_LOWER[0x0F & digest[i]];// 低位
        }
        return new String(out);
    }
}
