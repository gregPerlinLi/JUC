package com.gregperlinli.juc.lockSupport;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-19
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Object objectLock = new Object();
        new Thread(() -> {
            synchronized (objectLock) {
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in ...");
                try {
                    objectLock.wait();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Awakened ...");
            }
        }, "t1").start();

        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            synchronized (objectLock) {
                objectLock.notify();
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Send notify ...");
            }
        }, "t2").start();
    }
}
