package com.gregperlinli.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicMarkableReference;

/**
 * @author gregPerlinLi
 * @date 2022-11-03
 */
public class AtomicMarkableReferenceDemo {

    static AtomicMarkableReference markableReference = new AtomicMarkableReference(100, false);

    public static void main(String[] args) {
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Default Mark: " + marked);
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }
            markableReference.compareAndSet(100, 1000, marked, !marked);
        }, "t1").start();
        new Thread(() -> {
            boolean marked = markableReference.isMarked();
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Default Mark: " + marked);
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e ) { e.printStackTrace(); }
            boolean b = markableReference.compareAndSet(100, 2000, marked, !marked);
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Thread T2 CAS Result: " + b);
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Final Mark: " + markableReference.isMarked());
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Result: " + markableReference.getReference());
        }, "t1").start();
    }
}
