package com.gregperlinli.juc.lockSupport;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-08-19
 */
public class LockSupportDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in ...");
            LockSupport.park();
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Awaken ...");
        }, "t1");
        t1.start();

        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            LockSupport.unpark(t1);
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Send notify ...");
        }, "t2").start();
    }

    private static void lockAwaitSignal() {
        Lock lock = new ReentrantLock();
        Condition condition = lock.newCondition();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("====> " + Thread.currentThread().getName() +"\t ----> Come in ...");
                condition.await();
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Awaken ...");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }, "t1").start();

        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            lock.lock();
            try {
                condition.signal();
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Send notify ...");
            } finally {
                lock.unlock();
            }
        }, "t2").start();
    }

    private static void syncWaitNotify() {
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
