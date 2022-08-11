package com.gregperlinli.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-26
 */
public class ThreadDemo2 {
    /**
     * Create multiple threads and call methods in the resource class
     */
    public static void main(String[] args) {
        Share share = new Share();
        // Create two threads
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1
                    share.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1
                    share.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1
                    share.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1
                    share.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "DD").start();
    }
}

/**
 * 1. Create resource classes, define attributes and operation methods
 */
class Share {
    /**
     * Init value
     */
    private int number = 0;

    /**
     * Create a reentrant lock
     */
    private ReentrantLock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    /**
     * +1 method
     */
    public void increment() throws InterruptedException {
        // lock
        lock.lock();
        try {
            // 2. Judgment, execution, notification
            // if number is not 0, wait
            while ( number != 0 ) {
                condition.await();
            }
            // if number is 0, execute
            number++;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // Notify other threads
            condition.signalAll();
        } finally {
            // unlock
            lock.unlock();
        }
    }

    /**
     * -1 method
     */
    public void decrement() throws InterruptedException {
        // lock
        lock.lock();
        try {
            // 2. Judgment, execution, notification
            // if number is not 0, wait
            while (number != 1) {
                condition.await();
            }
            // if number is 0, execute
            number--;
            System.out.println(Thread.currentThread().getName() + " :: " + number);
            // Notify other threads
            condition.signalAll();
        } finally {
            // unlock
            lock.unlock();
        }
    }
}