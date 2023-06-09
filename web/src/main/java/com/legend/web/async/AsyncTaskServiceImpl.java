package com.legend.web.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 异步任务执行
 *
 * @author xlj
 * @date 2021/7/8
 */
@Slf4j
@Service
public class AsyncTaskServiceImpl implements AsyncTaskService {
    @Async("myThreadPool")      // 使用自定义的线程池执行
    @Override
    public void asyncTask1(Integer x) {
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("异步任务方法1执行了：{}", x);
    }

    @Async("myThreadPool")
    @Override
    public void asyncTask2(Integer y) {
        log.info("异步任务方法2执行了：{}", y);
    }
}
