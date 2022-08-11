package com.gregperlinli.sync;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-27
 */
public class SyncLockDemo {
    public static void main(String[] args) {
        // synchronized
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(Thread.currentThread().getName() + ":\tOuter layer");
                synchronized (o) {
                    System.out.println(Thread.currentThread().getName() + ":\tMiddle layer");
                    synchronized (o) {
                        System.out.println(Thread.currentThread().getName() + ":\tInner layer");

                    }
                }
            }
        }, "t1").start();
        // Lock
        Lock lock = new ReentrantLock();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName() + ":\tOuter layer");
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + ":\tMiddle layer");
                    lock.lock();
                    try {
                        System.out.println(Thread.currentThread().getName() + ":\tInner layer");
                    } finally {
                        lock.unlock();
                    }
                } finally {
                    lock.unlock();
                }
            } finally {
                lock.unlock();
            }
        }, "t1").start();
    }
}
