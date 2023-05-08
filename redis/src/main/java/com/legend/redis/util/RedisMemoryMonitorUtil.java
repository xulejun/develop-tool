package com.legend.redis.util;

import cn.hutool.extra.mail.Mail;
import lombok.extern.slf4j.Slf4j;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.io.StringReader;
import java.text.MessageFormat;
import java.util.Objects;
import java.util.Properties;

/**
 * Redis 内存监控
 *
 * @author legend xu
 * @date 2023/5/8
 */
@Slf4j
public class RedisMemoryMonitorUtil {

    public volatile static double REDIS_MEMORY_RATE = 0.85;

    private static Jedis jedis = new Jedis("127.0.0.1", 6379);

    public static void main(String[] args) {
        StringBuilder monitorContent = new StringBuilder();
        StringBuilder redisWarnInfo = new StringBuilder();
        getRedisMemory(monitorContent, redisWarnInfo, jedis);
        log.info(monitorContent.toString());
    }


    private static void getRedisMemory(StringBuilder monitorContent, StringBuilder redisWarnInfo, Jedis jedis) {
        // 获取 Redis 基本信息
        String info = jedis.info();
        Properties properties = new Properties();
        try {
            properties.load(new StringReader(info));
        } catch (IOException e) {
            log.warn("redis 内存占用检查——加载redis信息异常：", e);
        }
        // 使用内存 used_memory_human
        String usedMemory = (String) properties.get("used_memory");
        String usedMemoryHuman = (String) properties.get("used_memory_human");
        // 最大内存，默认 10G（10,737,418,240b）
        String maxMemory = Objects.isNull(properties.get("maxmemory")) || "0".equals(properties.get("maxmemory"))
                ? "10737418240" : (String) properties.get("maxmemory");
        double useRate = Double.parseDouble(usedMemory) / Double.parseDouble(maxMemory);
        // Redis ip和端口
        String host = jedis.getClient().getHost();
        String port = String.valueOf(jedis.getClient().getPort());
        // 超过阈值则发送告警邮件
        if (useRate > REDIS_MEMORY_RATE) {
            redisWarnInfo.append(MessageFormat.format("异常 Redis 地址：{0}:{1}；已使用内存：{2} b；最大内存：{3} b；使用率：{4} \n",
                    host, port, usedMemory, maxMemory, useRate));
        }
        // 统计 Redis 内存使用情况
        monitorContent.append(MessageFormat.format("Redis地址：{0}:{1}；已使用内存：{2}；内存使用率：{3} \n",
                host, port, usedMemoryHuman, useRate));
    }
}
