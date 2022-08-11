package com.gregperlinli.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-26
 */
public class ThreadDemo3 {
    /**
     * Create multiple threads and call methods in the resource class
     */
    public static void main(String[] args) {
        ShareResource resource = new ShareResource();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    resource.print5(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    resource.print10(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 1; i <= 10; i++) {
                try {
                    resource.print15(i);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
    }
}

/**
 * 1. Create resource classes, define attributes and operation methods
 */
class ShareResource {
    /**
     * Define flag <br/>
     * 1: AA <br/>
     * 2: BB <br/>
     * 3: CC <br/>
     */
    private int flag = 1;

    /**
     * Create a reentrant lock
     */
    private ReentrantLock lock = new ReentrantLock();
    /**
     * Create three conditions
     */
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * Print five times
     * @param loop Which round
     */
    public void print5(int loop) throws InterruptedException {
        // lock
        lock.lock();
        try {
            // Judgement
            while ( flag != 1 ) {
                // Waiting
                condition1.await();
            }
            // Execute
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :Loop: " + loop);
            }
            // Modify the flag first, and then notify
            flag = 2;
            condition2.signal();
        } finally {
            // unlock
            lock.unlock();
        }
    }
    /**
     * Print ten times
     * @param loop Which round
     */
    public void print10(int loop) throws InterruptedException {
        // lock
        lock.lock();
        try {
            // Judgement
            while ( flag != 2 ) {
                // Waiting
                condition2.await();
            }
            // Execute
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :Loop: " + loop);
            }
            // Modify the flag first, and then notify
            flag = 3;
            condition3.signal();
        } finally {
            // unlock
            lock.unlock();
        }
    }
    /**
     * Print fifteen times
     * @param loop Which round
     */
    public void print15(int loop) throws InterruptedException {
        // lock
        lock.lock();
        try {
            // Judgement
            while ( flag != 3 ) {
                // Waiting
                condition3.await();
            }
            // Execute
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName() + " :: " + i + " :Loop: " + loop);
            }
            // Modify the flag first, and then notify
            flag = 1;
            condition1.signal();
        } finally {
            // unlock
            lock.unlock();
        }
    }
}
