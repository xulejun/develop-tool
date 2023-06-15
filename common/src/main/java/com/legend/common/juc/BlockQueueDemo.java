package com.legend.common.juc;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 阻塞队列样例
 * 方法类型    抛出异常    特殊值     阻塞      超时
 * 插入         add      offer     put      offer
 * 移除       remove     poll      take     poll
 * 检查      element     peek     不可用    不可用
 *
 * ArrayBlockingQueue 和 LinkedBlockingQueue 区别
 * - ArrayBlockingQueue是有界的，初始化必须指定大小；LinkedBlockingQueue可以是有界的也可以是无界的(Integer.MAX_VALUE)，无界可能会内存溢出
 * - ArrayBlockingQueue采用的是数组存储，LinkedBlockingQueue采用的则是以Node节点作为连接对象的链表
 * - ArrayBlockingQueue实现的队列中的锁是没有分离的，LinkedBlockingQueue实现的队列中的锁是分离的（takeLock/putLock 提高队列吞吐量、并发性能）
 *
 * @author XLJ
 * @date 2020/10/24
 */
public class BlockQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // 容量大小为3的数组有界阻塞队列
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        /*// 1.抛出异常
        System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
        // 抛出异常————java.lang.IllegalStateException: Queue full
//        System.out.println(blockingQueue.add("a"));
        // remove----先进先出
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        // 抛出异常-----java.util.NoSuchElementException
//        System.out.println(blockingQueue.remove());
        // element----检查当前队列存在的元素
        System.out.println(blockingQueue.element());*/

        /*// 2.特殊值----返回的是boolean值
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        // 超出队列容量，返回false
//        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        // 队列为null，返回为null
        System.out.println(blockingQueue.poll());*/

        // 3.堵塞
        // put----没有任何返回值，无响应
        blockingQueue.put("a");
        blockingQueue.put("a");
        blockingQueue.put("a");
        // take----获取队列的元素，未获取到持续等待
//        System.out.println(blockingQueue.take());
        // poll----过时不候，无需继续等待
        System.out.println(blockingQueue.poll(3L, TimeUnit.SECONDS));
        System.out.println(blockingQueue);
    }
}
