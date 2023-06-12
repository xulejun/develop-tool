package com.legend.common.juc;

/**
 * 线程死锁
 * 产生死锁的四个条件：互斥、请求和保持、不剥夺、循环等待
 *
 * @author lejunxu
 */
public class DeadLockDemo {
    private static String ALock = "a";
    private static String BLock = "b";

    public static void main(String[] args) throws Exception {
        new DeadLockDemo().deadLockT();
    }

    /**
     * 模拟死锁
     */
    private void deadLockT() throws Exception {
        new Thread(() -> {
            synchronized (ALock) {
                // 让线程先睡眠2s
                try {
                    Thread.sleep(1000);
                    // 尝试获取B对象的锁
                    synchronized (BLock) {
                        System.out.println("线程A获取到B对象");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-1").start();

        new Thread(() -> {
            // 占有B对象
            synchronized (BLock) {
                // 尝试获取B对象的锁
                synchronized (ALock) {
                    System.out.println("线程B获取到A对象");
                }
            }
        }, "Thread-2").start();
    }
}

