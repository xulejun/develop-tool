package com.legend.common.juc;

import cn.hutool.core.thread.ThreadFactoryBuilder;

import java.util.concurrent.*;

/**
 * 线程池样例
 * ThreadPoolExecutor的7大参数、4大拒绝策略
 * hippo4j 可实现对线程池资源的监控
 *
 * @author XLJ
 * @date 2020/10/24
 */
public class MyThreadPoolDemo {

    public static void main(String[] args) throws InterruptedException {
//        initPool();
        // 获取当前电脑CPU逻辑处理器数+2为maximumPoolSize
        System.out.println(Runtime.getRuntime().availableProcessors());

        // 线程池创建------7大参数，4个拒绝策略
        ExecutorService threadPoolExecutor = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                // ThreadFactoryBuilder.create().build(),    // hutool、Appache-common、guava 都可创建

                // new ThreadPoolExecutor.AbortPolicy();   // 默认拒绝策略——丢弃任务并抛出 RejectedExecutionException 异常
                // new ThreadPoolExecutor.CallerRunsPolicy()  //  由调用线程执行任务
                // new ThreadPoolExecutor.DiscardPolicy()     //  丢弃任务，但是不抛出异常
                new ThreadPoolExecutor.DiscardOldestPolicy());      // 丢弃队列最早的未处理任务，然后重新尝试执行任务
        for (int i = 1; i <= 9; i++) {
            threadPoolExecutor.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t办理业务");
            });
        }
        threadPoolExecutor.shutdown();
    }

    public static void initPool() throws InterruptedException {
//     一个线程池固定5个线程数
//        ExecutorService executorService = Executors.newFixedThreadPool(5);
//     一个线程池单个线程数
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//     一个线程池动态线程数
        ExecutorService executorService = Executors.newCachedThreadPool();

        for (int i = 1; i <= 10; i++) {
            executorService.execute(() -> {
                System.out.println(Thread.currentThread().getName() + "\t办理业务");
            });
            TimeUnit.SECONDS.sleep(1);
        }
        // 关闭线程池
        executorService.shutdown();
    }
}
