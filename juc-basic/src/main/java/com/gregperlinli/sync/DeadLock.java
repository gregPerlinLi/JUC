package com.gregperlinli.sync;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-07-27
 */
public class DeadLock {

    /**
     * Create two object
     */
    static Object a = new Object();
    static Object b = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " Lock a is already held, and try to acquire lock b");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " Lock b is already held");
                }
            }
        }, "A").start();
        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " Lock b is already held, and try to acquire lock a");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " Lock a is already held");
                }
            }
        }, "B").start();
    }
}
