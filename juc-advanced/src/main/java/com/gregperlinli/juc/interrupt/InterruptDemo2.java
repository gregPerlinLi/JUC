package com.gregperlinli.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-16
 */
public class InterruptDemo2 {
    public static void main(String[] args) {
        // The instance method interrupt() only sets the thread interrupt status bit to true and does not stop the thread
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 300; i++) {
                System.out.println("====> " + i);
            }
            System.out.println("====> 2) Interrupt flag after T1 thread calling interrupt() method: " + Thread.currentThread().isInterrupted());
        }, "t1");
        t1.start();

        System.out.println("====> Default interrupt flag of T1 thread: " + t1.isInterrupted());

        // Pause the thread for a few seconds
        try { TimeUnit.MILLISECONDS.sleep(2); } catch (InterruptedException e ) { e.printStackTrace(); }

        t1.interrupt();

        System.out.println("====> 1) Interrupt flag after T1 thread calling interrupt() method: " + t1.isInterrupted());

    }
}
