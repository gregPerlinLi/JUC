package com.gregperlinli.juc.cas;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class AbaDemo {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
        // ABA happen
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
        // ABA resolved
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Version1: " + stamp);
            // Pause the thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(500); } catch ( InterruptedException e ) { e.printStackTrace(); }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Version2: " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Version3: " + atomicStampedReference.getStamp());
        }, "t3").start();
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Version1: " + stamp);
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
            boolean b = atomicStampedReference.compareAndSet(100, 2022, stamp, stamp + 1);
            System.out.println("====> " + b + "\t ----> " + atomicStampedReference.getReference() + "\t ----> Version3: " + atomicStampedReference.getStamp());
        }, "t4").start();
    }
}
