package com.legend.redis.test;

import com.legend.redis.RedisApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

/**
 * @author xlj
 * @date 2021/7/1
 */
@Slf4j
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = RedisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void testLock() throws Exception{
        RLock lock = redissonClient.getLock("my_lock");
        lock.lock();
        TimeUnit.SECONDS.sleep(40);
    }

    @Test
    public void test() {
        String a = null;
        Optional<String> hello = Optional.ofNullable(a);
        System.out.println(hello);
        if (hello.isPresent()) {
            System.out.println("存在");
        } else {
            System.out.println("不存在");
        }
    }
}
