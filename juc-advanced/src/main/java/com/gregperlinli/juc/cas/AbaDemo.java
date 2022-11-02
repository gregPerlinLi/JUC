package com.gregperlinli.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class AbaDemo {
    static AtomicInteger atomicInteger = new AtomicInteger(100);

    public static void main(String[] args) {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            // Pause the thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(10); } catch (InterruptedException e ) { e.printStackTrace(); }
            atomicInteger.compareAndSet(101, 100);
        }, "t1").start();
        new Thread(() -> {
            // Pause the thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e ) { e.printStackTrace(); }
            System.out.println("====> " + atomicInteger.compareAndSet(100, 2022) + "\t ----> " + atomicInteger.get());
        }, "t2").start();
    }
}
