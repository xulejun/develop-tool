package com.legend.redis.jedis;

import cn.hutool.core.util.StrUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * jedis连接Redis哨兵模式
 *
 * @author xulejun
 * @date 2021/9/23
 */
public class JedisConnectDemo {

    private static final int DEFAULT_TIMEOUT = 15000;

    /**
     * 密码不能为空串，否则也报错
     */
    private static final String PASSWORD = null;

    public static void main(String[] args) throws Exception {
        // jedis 单节点
        Jedis jedis = getSingleJedis();

        jedis.close();

        // jedis 连接池
        JedisPool jedisPool = getJedisPool(null);
        Jedis jedisPoolResource = jedisPool.getResource();

        jedisPoolResource.close();

        // jedis 哨兵模式
        JedisSentinelPool sentinelPool = getJedisSentinelPool();
        Jedis sentinelPoolResource = sentinelPool.getResource();

        sentinelPoolResource.close();
    }

    /**
     * jedis 单机连接
     *
     * @return
     */
    public static Jedis getSingleJedis() {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        if (StrUtil.isNotBlank(PASSWORD)) {
            jedis.auth(PASSWORD);
        }
        return jedis;
    }

    /**
     * jedis 连接池（可连接多个节点）
     *
     * @param address
     * @return
     */
    public static JedisPool getJedisPool(String address) {
        JedisPool jedisPool = null;
        // 配置参数
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(10);
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(5);
        // 最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(5);
        // 最大等待时间
        jedisPoolConfig.setMaxWait(Duration.ofMillis(30000));

        String[] split = address.split(":");
        if (StrUtil.isNotBlank(address)) {
            String ip = split[0];
            int port = Integer.parseInt(split[1]);
            String password = split.length > 2 ? split[2] : null;
            jedisPool = new JedisPool(jedisPoolConfig, ip, port, DEFAULT_TIMEOUT, password);
        } else {
            jedisPool = new JedisPool(jedisPoolConfig, "localhost", 6379, DEFAULT_TIMEOUT, PASSWORD);
        }
        return jedisPool;
    }

    /**
     * jedis 哨兵模式（主从模式加强版），需要先搭建 redis 集群
     *
     * @return
     */
    public static JedisSentinelPool getJedisSentinelPool() {
        String masterName = "myMaster";
        String password = "123456";

        // 配置参数
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(10);
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(5);
        // 最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(5);
        // 最大等待时间
        jedisPoolConfig.setMaxWait(Duration.ofMillis(30000));

        // 哨兵信息，注意填写哨兵的地址
        Set<String> sentinels = new HashSet<>();
        sentinels.add("127.0.0.1:6390");
        sentinels.add("127.0.0.1:6391");
        // 创建连接池，当master宕机后，哨兵模式自动切换到slave上
        return new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, password);
    }

}
