package com.gregperlinli.juc.interrupt;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author gregPerlinLi
 * @date 2022-08-16
 */
public class InterruptDemo {
    static volatile boolean isStop = false;
    static AtomicBoolean atomicBoolean = new AtomicBoolean(false);

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while ( true ) {
                if ( Thread.currentThread().isInterrupted() ) {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t isInterrupted have modified to true, program stop");
                    break;
                }
                System.out.println("====> Hello interrupt API...");
            }
        }, "t1");
        t1.start();

        // Pause the thread for a few seconds
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            t1.interrupt();
        }, "t2").start();
    }

    private static void m2AtomicBoolean() {
        new Thread(() -> {
            while ( true ) {
                if ( atomicBoolean.get() ) {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t atomicBoolean have modified to true, program stop");
                    break;
                }
                System.out.println("====> Hello AtomicBoolean...");
            }
        }, "t1").start();

        // Pause the thread for a few seconds
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            atomicBoolean.set(true);
        }, "t2").start();
    }

    private static void m1Volatile() {
        new Thread(() -> {
            while ( true ) {
                if ( isStop ) {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t isStop have modified to true, program stop");
                    break;
                }
                System.out.println("====> Hello volatile...");
            }
        }, "t1").start();

        // Pause the thread for a few seconds
        try { TimeUnit.MILLISECONDS.sleep(200); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            isStop = true;
        }, "t2").start();
    }
}
