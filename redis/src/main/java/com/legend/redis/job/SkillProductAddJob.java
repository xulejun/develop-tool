package com.legend.redis.job;

import cn.hutool.core.util.IdUtil;
import com.google.common.collect.Lists;
import com.legend.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 秒杀商品定时上架
 *
 * @author xlj
 * @date 2021/5/22
 */
@Slf4j
@Component
public class SkillProductAddJob {
    private static List<Product> products = Lists.newArrayList();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 10; i++) {
            products.add(Product.builder().id(i).name("product" + i).price((double) i).build());
        }
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private final String SKILL_PRODUCT_PREFIX = "skill:product:";

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void addProduct() {
        log.info("秒杀商品开始上架");

        // 分布式锁解决幂等性：在多个服务同时上架时会出现商品重复上架
        RLock lock = redissonClient.getLock("skill-product-lock");
        lock.lock(10, TimeUnit.SECONDS);
        try {
            // 查询秒杀的商品（价格1000-2000，替换数据库操作）
            // List<Product> skillProducts = productService.selectSkillProduct();
            List<Product> skillProducts = products;
            // 绑定一个hash操作
            BoundHashOperations hashOps = redisTemplate.boundHashOps(SKILL_PRODUCT_PREFIX);
            // 商品添加
            skillProducts.forEach(product -> {
                if (!hashOps.hasKey(product.getId().toString())) {
                    // 还可以将场次信息添加到缓存里（K：开始时间-结束时间，V：商品数量）
                    // 引入分布式信号量，秒杀商品关键，（K：随机码，V：商品库存）
                    String uuid = IdUtil.simpleUUID();
                    String token = "skill:semaphore:" + uuid;
                    RSemaphore semaphore = redissonClient.getSemaphore(token);
                    semaphore.trySetPermits(product.getStock());
                    product.setSkillCode(uuid);

                    // 将商品存入到缓存中（hash：{K：商品Id，V：商品}）
                    hashOps.put(product.getId().toString(), product.toString());
                }
            });
        } finally {
            lock.unlock();
        }

    }
}
