package com.legend.web.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @author legend xu
 * @Async 同时可以作用在定时任务调度上
 * @date 2023/4/24
 */
@Slf4j
//@Component
@EnableAsync    // 需要开启异步
@EnableScheduling
public class AsyncTaskJob {

    @Async("myThreadPool")
    @Scheduled(fixedDelay = 10000)
    public void startJob() {
        log.info("异步定时任务调度开始执行……");
    }
}
