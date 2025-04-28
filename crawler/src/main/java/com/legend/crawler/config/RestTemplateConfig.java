package com.legend.crawler.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author 602
 * @date 2025/4/28
 */
@Configuration
@EnableScheduling
public class RestTemplateConfig {

    @Bean
    public PoolingHttpClientConnectionManager poolingConnectionManager() {
        PoolingHttpClientConnectionManager manager = new PoolingHttpClientConnectionManager();
        manager.setMaxTotal(200);
        manager.setDefaultMaxPerRoute(50);
        return manager;
    }

    @Bean
    public HttpClient httpClient(PoolingHttpClientConnectionManager manager) {
        return HttpClientBuilder.create()
                .setConnectionManager(manager)
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(10000)
                        .setConnectionRequestTimeout(2000)
                        .build())
                .build();
    }

    @Bean
    public RestTemplate restTemplate(HttpClient httpClient) {
        return new RestTemplate(new HttpComponentsClientHttpRequestFactory(httpClient));
    }

    @Scheduled(fixedDelay = 30000)
    public void closeIdleConnections() {
        poolingConnectionManager().closeIdleConnections(30, TimeUnit.SECONDS);
    }
}