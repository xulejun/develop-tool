package com.legend.redis.jedis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author legend xu
 * @date 2023/5/7
 */
@Configuration
@PropertySource("classpath:jedis.properties")
public class MyJedisPoolConfig {
    @Value("${spring.redis.jedis.pool.max-idle}")
    private int maxIdle;

    @Value("${jedis.host}")
    private String host;

    @Value("${jedis.port}")
    private int port;

    @Value("${jedis.timeout}")
    private int timeout;

    @Bean("myJedisPool")
    public JedisPool jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        return new JedisPool(jedisPoolConfig, host, port, timeout);
    }
}
