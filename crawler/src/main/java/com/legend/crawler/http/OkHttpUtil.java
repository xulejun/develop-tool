package com.legend.crawler.http;

import com.alibaba.fastjson2.JSONObject;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class OkHttpUtil {
    private static final int TIMEOUT = 15;

    private static final ConnectionPool connectionPool = new ConnectionPool(10, 5, TimeUnit.MINUTES);

    private static final OkHttpClient OKHTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectionPool(connectionPool)
            .build();
    private static MediaType MEDIA_TYPE_JSON = MediaType.get("application/json; charset=utf-8");

    public static ResponseBody get(String url, Map<String, String> queryParams) throws IOException {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        queryParams.forEach(builder::setQueryParameter);
        Request request = new Request.Builder().url(builder.build()).get().build();
        ResponseBody body = OKHTTP_CLIENT.newCall(request).execute().body();
        return body;
    }

    /**
     * post 请求携带 请求体
     *
     * @param url
     * @param queryParams
     * @throws IOException
     */
    public static String postJson(String url, Map<String, String> queryParams, String requestBody) throws IOException {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        queryParams.forEach(builder::setQueryParameter);
        Request request = new Request.Builder().url(builder.build()).post(RequestBody.create(requestBody, MEDIA_TYPE_JSON)).build();
        return OKHTTP_CLIENT.newCall(request).execute().body().string();
    }

    public static String postJson(String url, String requestBody) throws IOException {
        Request request = new Request.Builder().url(url).post(RequestBody.create(requestBody, MEDIA_TYPE_JSON)).build();
        return OKHTTP_CLIENT.newCall(request).execute().body().string();
    }

    public static String postJson(String url) throws IOException {
        // 简单的 post 请求， 不携带任何请求体
        Request request = new Request.Builder().url(url).post(new FormBody.Builder().build()).build();
        return OKHTTP_CLIENT.newCall(request).execute().body().string();
    }
}
