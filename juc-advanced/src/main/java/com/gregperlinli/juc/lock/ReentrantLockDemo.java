package com.gregperlinli.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-08-12
 */
public class ReentrantLockDemo {
    static Lock lock = new ReentrantLock();

    public static void main(String[] args) {
        ReentrantLockDemo reentrantLockDemo = new ReentrantLockDemo();
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in outer call...");
                lock.lock();
                try {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in middle call...");
                    lock.lock();
                    try {
                        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in inner call...");
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

    public synchronized void m1() {
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in m1...");
        m2();
        System.out.println("<==== " + Thread.currentThread().getName() + "\t <---- m1 END");
    }

    public synchronized void m2() {
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in m2...");
        m3();
        System.out.println("<==== " + Thread.currentThread().getName() + "\t <---- m2 END");
    }

    public synchronized void m3() {
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in m3...");
        System.out.println("<==== " + Thread.currentThread().getName() + "\t <---- m3 END");
    }

    private static void reentrantM1() {
        final Object object = new Object();
        new Thread(() -> {
            synchronized (object) {
                System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Outer call...");
                synchronized (object) {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Middle call ...");
                    synchronized (object) {
                        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Inner call ...");
                    }
                }
            }
        }, "t1").start();
    }
}
