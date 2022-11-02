package com.gregperlinli.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class SpinLockDemo {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
        new Thread(() -> {
            spinLockDemo.lock();
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e ) { e.printStackTrace(); }
            spinLockDemo.unlock();
        }, "A").start();
        // Pause the thread for 500 milliseconds
        try { TimeUnit.MILLISECONDS.sleep(500); } catch ( InterruptedException e ) { e.printStackTrace(); }
        new Thread(() -> {
            spinLockDemo.lock();
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e ) { e.printStackTrace(); }
            spinLockDemo.unlock();
        }, "B").start();
    }

    public void lock() {
        Thread thread = Thread.currentThread();
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> come in ...");
        while (!atomicReference.compareAndSet(null, thread)) {

        }
    }


    public void unlock() {
        Thread thread = Thread.currentThread();
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> task over, unlock ...");
        while (!atomicReference.compareAndSet(thread, null)) {

        }
    }
}
