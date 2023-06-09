package com.legend.redis.skill;

import com.legend.common.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RSemaphore;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 商品秒杀关注的几个问题：
 * 1. 服务单一职责，独立部署：
 * 秒杀做成独立服务，即使自己抗不住压力，也不影响其他业务
 * 2. 秒杀链接加密：
 * 防止恶意攻击，模拟秒杀请求；防止链接暴露，工作人员提前秒杀商品
 * 3. 库存预热，快速扣减：
 * 秒杀读多写少，提前将库存放入缓存中，用信号量来控制秒杀的请求
 * 4. 动静分离：
 * nginx做好动静分离，保证动态请求才打到后端的服务集群上，使用CDN网络，分担集群的压力
 * 5. 恶意请求拦截：
 * 识别非法攻击请求并拦截，在网关层进行处理
 * 6. 流量错峰：
 * 使用各种手段，将流量分担到最大宽度的时间点（也就是将秒杀操作时间拉长），输入验证码，加入购物车……
 * 7. 限流、熔断、降级
 * 前端限流（时间范围内不能多次点击）+后端限流（限制总量，快速失败返回，不阻塞，熔断隔离防止雪崩）
 * 8. 队列削峰
 * 秒杀的请求进入队列，慢慢去减库存
 *
 * @author xlj
 * @date 2021/5/22
 */
@Slf4j
@Controller
public class SkillProductController {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    @GetMapping("/skillProduct")
    public String skillProductList(Model model) {
        BoundHashOperations hashOps = redisTemplate.boundHashOps("skill:product:");
        List<Product> redisProduct = hashOps.multiGet(hashOps.keys());
        model.addAttribute("list", redisProduct);
        return "skillProduct";
    }

    @GetMapping("/skill")
    @ResponseBody
    public String skillProduct(@RequestParam Integer id, @RequestParam String code) {
        // 这里省略了各种校验：随机码校验、场次时间校验、商品id校验
        String key = "skill:semaphore:" + code;
        if (redisTemplate.hasKey(key)) {
            RSemaphore semaphore = redissonClient.getSemaphore(key);
            try {
                boolean b = semaphore.tryAcquire(1, 100, TimeUnit.MILLISECONDS);
                if (b) {
                    return "秒杀成功";

                } else {
                    return "秒杀失败";
                }
                // 信号量获取到了就说明秒杀成功了，通过商品id，可进行后续的下单操作
            } catch (InterruptedException e) {
                log.warn("秒杀失败");
            }
        }
        return null;
    }
}
