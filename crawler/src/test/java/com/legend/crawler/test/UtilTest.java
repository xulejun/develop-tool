package com.legend.crawler.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 602
 * @date 2025/4/28
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class UtilTest {
    @Autowired
    private RestTemplate restTemplate;

    @Test
    public void methodTest1() throws Exception {
        String targetUrl = "";
        String username1 = "Qwe#12341";
        String functionIndex1 = "";
        long startTime1 = 1745787893000L;
        long endTime1 = 1745823893015L;
        Map<String, Object> requestParam = new HashMap<>();
        String encodeUserName = StringUtils.isEmpty(username1) ? null : URLEncoder.encode(username1, "UTF-8");
        Optional.ofNullable(encodeUserName).ifPresent(userName -> requestParam.put("userName", userName));
        Optional.ofNullable(functionIndex1).ifPresent(functionIndex -> requestParam.put("functionIndex", functionIndex));
        Optional.ofNullable(startTime1).ifPresent(startTime -> requestParam.put("startTime", startTime));
        Optional.ofNullable(endTime1).ifPresent(endTime -> requestParam.put("endTime", endTime));
        URI uri = UriComponentsBuilder.fromUriString(targetUrl + "?" + getParam(requestParam))
                .build()
                .encode()
                .toUri();
        ResponseEntity<Map> responseEntity = restTemplate.getForEntity(uri, Map.class);
        Map<String, Object> body = responseEntity.getBody();
        System.out.println(body);
    }

    public String getParam(Map<String, Object> requestParam) throws Exception {
        return requestParam.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((str, str2) -> str + "&" + str2)
                .orElseThrow(() -> new Exception("参数为空"));
    }
}
