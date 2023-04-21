package com.legend.crawler.http;

import cn.hutool.json.JSONObject;
import okhttp3.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * okhttp 的使用
 *
 * @author legend xu
 * @date 2023/3/23
 */
public class OkHttpUtil {
    private static final int TIMEOUT = 15;

    /**
     * 默认创建连接池最多可容纳5个空闲连接，这些连接将在不活动5分钟后被收回
     */
    private static final OkHttpClient OKHTTP_CLIENT = new OkHttpClient.Builder()
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    private static HashMap<String, List<Cookie>> cookieStore = new HashMap<>();


    private static OkHttpClient OKHTTP_COOKIE_CLIENT = new OkHttpClient.Builder()
            .cookieJar(new CookieJar() {

                @Override
                public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                    cookieStore.put(url.host(), cookies);
                }

                @Override
                public List<Cookie> loadForRequest(HttpUrl url) {
                    List<Cookie> cookies = cookieStore.get(url.host());
                    return cookies != null ? cookies : new ArrayList<>();
                }
            })
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .build();

    /**
     * get 请求携带 Map 类型的 查询参数
     *
     * @param url
     * @param queryParams
     * @throws IOException
     */
    public static ResponseBody get(String url, Map<String, String> queryParams) throws IOException {
        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        queryParams.forEach(builder::setQueryParameter);

        Request request = new Request.Builder().url(builder.build()).get().build();

        // 携带请求头的完整 url
        // Request request = new Request.Builder().url(url).addHeader("Auth","xxx").build();

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
    public static ResponseBody post(String url, Map<String, String> queryParams, String requestBody) throws IOException {
        // HttpServletRequest 获取请求体
        // String data = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
        queryParams.forEach(builder::setQueryParameter);

        Request request = new Request.Builder().url(builder.build()).post(RequestBody.create(MediaType.parse("application/json"), requestBody)).build();

        // 简单的 post 请求， 不携带任何请求体
        // Request request = new Request.Builder().url(url).post(new FormBody.Builder().build()).build();

        return OKHTTP_CLIENT.newCall(request).execute().body();
    }

    public static void main(String[] args) throws Exception {
//        String url = "http://oct-portal.hotel.ctripcorp.com/portal/login/login";
//        HttpUrl.Builder builder = HttpUrl.get(url).newBuilder();
//        FormBody.Builder formBody = new FormBody.Builder();
//        formBody.add("username", "octopus");
//        formBody.add("password", "Octopus123");
//
//        Request request = new Request.Builder().url(builder.build()).post(formBody.build()).build();
//        Response response = OKHTTP_COOKIE_CLIENT.newCall(request).execute();
//        System.out.println("第一次响应：" + response.body().string());

//        String url1 = "http://oct-portal.hotel.ctripcorp.com/portal/account-distribute-config/queryStrategyConfig";
//        HttpUrl.Builder builder1 = HttpUrl.get(url1).newBuilder();
//        Request request1 = new Request.Builder().addHeader("cookie","username=admin").url(builder1.build()).get().build();
//        Response response1 = OKHTTP_COOKIE_CLIENT.newCall(request1).execute();

        String url2 = "http://oct-portal.hotel.ctripcorp.com/portal/server-config-manager/1/12/add";
        HttpUrl.Builder builder2 = HttpUrl.get(url2).newBuilder();
        JSONObject jsonObject = new JSONObject();
        jsonObject.set("key", "legendxu");
        jsonObject.set("value", "legendxu");
        builder2.setQueryParameter("config", jsonObject.toString());

        Request request2 = new Request.Builder().addHeader("cookie", "username=admin").url(builder2.build())
                .post(new FormBody.Builder().build()).build();
        Response response2 = OKHTTP_COOKIE_CLIENT.newCall(request2).execute();
        System.out.println("第二次响应：" + response2.body().string());

    }

}
