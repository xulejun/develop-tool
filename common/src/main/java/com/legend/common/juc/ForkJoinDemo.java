package com.legend.common.juc;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoin分支合并框架
 * ForkJoinPool、ForkJoinTask、Recursive（递归）Task
 * 核心思想：通过递归将一个线程不断fork拆分多个子线程，最后join合并，继承RecursiveTask抽象类，实现computer方法
 * 类似于单机版的 map-reduce，都是采用分治算法，同时采取工作窃取算法（避免工作线程由于拆分了任务之后的join等待过程）
 * ForkJoinPool中内置了一个WorkQueue数组，一个WorkQueue对应着一个线程。当一个线程执行完其对应的WorkQueue中的任务中时，会窃取其他WorkQueue中的任务来执行
 * 其中WorkQueue是一个双端队列，当前线程会从一端获取和添加任务，而其他线程会从队列的另一端来窃取任务，进一步降低了冲突
 *
 * @author xlj
 * @date 2020/10/27 22:44
 */

class MyTask extends RecursiveTask<Integer> {
    private static final Integer LIMIT_VALUE = 10;
    // 计算初始值
    private int start;
    // 计算结束值
    private int end;
    // 结果
    private int result;

    public MyTask(int start, int end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // 当任务较小，无需拆分
        if ((end - start) <= LIMIT_VALUE) {
            for (int i = start; i <= end; i++) {
                result += i;
            }
        } else {
            int middle = (end + start) / 2;
            // 递归fork拆分
            MyTask myTask1 = new MyTask(start, middle);
            MyTask myTask2 = new MyTask(middle + 1, end);
            myTask1.fork();
            myTask2.fork();
            result = myTask1.join() + myTask2.join();
        }
        return result;
    }
}

public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 资源类
        MyTask myTask = new MyTask(50, 100);
        // 线程池
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        // 线程-操作-资源类
        ForkJoinTask<Integer> task = forkJoinPool.submit(myTask);
        // 通过get方法获取返回值
        System.out.println("hello，ForkJoin");
        System.out.println(task.get());

        forkJoinPool.shutdown();
    }
}
