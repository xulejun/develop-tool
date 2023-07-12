package com.legend.common.leetcode;

import java.util.Stack;

/**
 * 用栈实现一个线程安全、高效的队列
 *
 * 在这个示例中，我们使用了两个栈inStack和outStack来实现队列。enqueue方法将元素入队，直接将元素压入inStack中。
 * dequeue方法将元素出队，如果outStack为空，则将inStack中的所有元素依次弹出并压入outStack中，然后从outStack中弹出一个元素。isEmpty方法用于判断队列是否为空。
 *
 * 由于enqueue和dequeue方法都使用了synchronized关键字进行同步，因此这个队列是线程安全的。
 * 由于使用了两个栈来实现队列，因此这个队列的出队操作是O(1)的，入队操作的时间复杂度为O(n)。
 * @author legend xu
 * @date 2023/7/12
 */
public class ThreadSafeQueue<T> {
    private Stack<T> inStack = new Stack<>();
    private Stack<T> outStack = new Stack<>();

    public synchronized void enqueue(T item) {
        inStack.push(item);
    }

    public synchronized T dequeue() {
        if (outStack.isEmpty()) {
            while (!inStack.isEmpty()) {
                outStack.push(inStack.pop());
            }
        }
        return outStack.pop();
    }

    public synchronized boolean isEmpty() {
        return inStack.isEmpty() && outStack.isEmpty();
    }
}