package com.legend.redis.jedis;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

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

    public static void main(String[] args) throws Exception {
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        jedis.auth("123456");
        System.out.println(jedis.get("hello"));
    }

    @Test
    public void testSentinel() {
        String masterName = "mymaster";
        String password = "123456";
        // 设置参数
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        // 最大连接数, 默认8个
        jedisPoolConfig.setMaxTotal(10);
        // 最大空闲连接数, 默认8个
        jedisPoolConfig.setMaxIdle(5);
        // 最小空闲连接数, 默认0
        jedisPoolConfig.setMinIdle(5);
        // 哨兵信息，注意填写哨兵的地址
        Set<String> sentinels = new HashSet<>();
        sentinels.add("127.0.0.1:6390");
        sentinels.add("127.0.0.1:6391");
        // 创建连接池，当master宕机后，哨兵模式自动切换到slave上
//        JedisSentinelPool pool = new JedisSentinelPool(masterName, sentinels, jedisPoolConfig, password);
//        if ("6381".equals(String.valueOf(pool.getCurrentHostMaster().getPort()))) {
//            pool = new JedisSentinelPool(masterName, sentinels, jedisPoolConfig);
//        }
        JedisPool pool = new JedisPool("10.128.188.44", 6379);
        // 获取客户端
        try (Jedis jedis = pool.getResource()) {
            jedis.set("username", "legendxu");
            String value = jedis.get("username");
            System.out.println(value);
        } catch (Exception e) {
            System.out.println("jedis异常：" + e);
        }
    }

    @Test
    public void testPassword() {
        int DEFAULT_TIMEOUT = 120000;
//        String addresses = "10.128.188.44:6379";
        String addresses = "127.0.0.1:6379";
        JedisPool[] pools;
        String[] addrs;
//        String password = "Octopus123@";
        String password = "123456";

        JedisPoolConfig config = new JedisPoolConfig();
        config.setTestOnBorrow(true);
        config.setMaxTotal(200);
        config.setMaxIdle(100);
        config.setMaxWaitMillis(10000);
        if (addresses == null) {
            pools = new JedisPool[]{new JedisPool(config, "localhost", 6379, DEFAULT_TIMEOUT, password)};
            return;
        }
        ArrayList<JedisPool> pools_ = new ArrayList<>();
        ArrayList<String> addrs_ = new ArrayList<>();
        for (String address : addresses.split("[,;]")) {
            String[] ss = address.split("[:/]");
            if (ss.length >= 2) {
                int port = Integer.parseInt(ss[1]);
                if (port == 0) {
                    continue;
                }
                addrs_.add(ss[0] + ':' + ss[1]);
                pools_.add(new JedisPool(config, ss[0], port, DEFAULT_TIMEOUT, password));
            }
        }
        if (pools_.isEmpty()) {
            pools = new JedisPool[]{new JedisPool(config, "localhost", 6379, DEFAULT_TIMEOUT, password)};
            addrs = new String[]{"localhost"};
        } else {
            pools = pools_.toArray(new JedisPool[0]);
            addrs = addrs_.toArray(new String[0]);
        }

        String hello = pools[0].getResource().get("hello");
        System.out.println("从 redis 获取值:" + hello);
    }

}
