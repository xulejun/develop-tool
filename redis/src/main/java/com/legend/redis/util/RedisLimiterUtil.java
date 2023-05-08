package com.legend.redis.util;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import redis.clients.jedis.Jedis;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * redis限流器
 *
 * @author lejunxu
 */
public class RedisLimiterUtil {

    private static final Long SUCCESS_FLAG = 1L;

    /**
     * 判断是否允许访问（redisTemplate 用法）
     *
     * @param id       这次获取令牌桶的id
     * @param rate     每秒填充速率
     * @param capacity 令牌桶最大容量
     * @param tokens   每次访问消耗几个令牌
     * @return true 允许访问 false 不允许访问
     */
    public boolean isAllowedByTemplate(String id, String rate, String capacity, String tokens, RedisTemplate redisTemplate) {
        RedisScript<Long> redisScript = new DefaultRedisScript<>(SCRIPT, Long.class);
        Object result = redisTemplate.execute(redisScript, getKey(id), rate, capacity, String.valueOf(Instant.now().getEpochSecond()), tokens);
        return SUCCESS_FLAG.equals(result);
    }

    /**
     * 判断是否允许访问（Jedis 用法）
     *
     * @param id       这次获取令牌桶的id
     * @param rate     每秒填充速率
     * @param capacity 令牌桶最大容量
     * @param tokens   每次访问消耗几个令牌
     * @return true 允许访问 false 不允许访问
     */
    public boolean isAllowedByJedis(String id, String rate, String capacity, String tokens, Jedis jedis) {
        String scriptLoad = jedis.scriptLoad(SCRIPT);
        Object result = jedis.evalsha(scriptLoad, getKey(id), Arrays.asList(rate, capacity, String.valueOf(Instant.now().getEpochSecond()), tokens));
        return SUCCESS_FLAG.equals(result);
    }

    private List<String> getKey(String id) {
        String prefix = "limiter:" + id;
        String tokenKey = prefix + ":tokens";
        String timestampKey = prefix + ":timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }

    private static final String SCRIPT =
            // 获取到限流资源令牌数的key和响应时间戳的key
            "local tokens_key = KEYS[1]\n" +
                    "local timestamp_key = KEYS[2]\n" +
                    // 分别获取填充速率、令牌桶容量、当前时间戳、消耗令牌数
                    "local rate = tonumber(ARGV[1])\n" +
                    "local capacity = tonumber(ARGV[2])\n" +
                    "local now = tonumber(ARGV[3])\n" +
                    "local requested = tonumber(ARGV[4])\n" +
                    // 计算出失效时间，大概是令牌桶填满时间的两倍
                    "local fill_time = capacity/rate\n" +
                    "local ttl = math.floor(fill_time*2)\n" +
                    // 获取到最近一次的剩余令牌数，如果不存在说明令牌桶是满的
                    "local last_tokens = tonumber(redis.call('get', tokens_key))\n" +
                    "if last_tokens == nil then\n" +
                    "  last_tokens = capacity\n" +
                    "end\n" +
                    // 上次消耗令牌的时间戳，不存在视为0
                    "local last_refreshed = tonumber(redis.call('get', timestamp_key))\n" +
                    "if last_refreshed == nil then\n" +
                    "  last_refreshed = 0\n" +
                    "end\n" +
                    // 计算出间隔时间
                    "local diff_time = math.max(0, now-last_refreshed)\n" +
                    // 剩余令牌数量 =  “令牌桶容量” 和 “最后令牌数+（填充速率*时间间隔）”之间的最小值
                    "local filled_tokens = math.min(capacity, last_tokens+(diff_time*rate))\n" +
                    // 如果剩余令牌数量大于等于消耗令牌的数量则流量通过，否则不通过
                    "local allowed = filled_tokens >= requested\n" +
                    "local new_tokens = filled_tokens\n" +
                    "local allowed_num = 0\n" +
                    "if allowed then\n" +
                    "  new_tokens = filled_tokens - requested\n" +
                    "  allowed_num = 1\n" +
                    "end\n" +
                    // 最后保存数据现场
                    "if ttl > 0 then\n" +
                    "  redis.call('setex', tokens_key, ttl, new_tokens)\n" +
                    "  redis.call('setex', timestamp_key, ttl, now)\n" +
                    "end\n" +
                    "return allowed_num\n";
}

