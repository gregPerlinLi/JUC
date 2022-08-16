package com.gregperlinli.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-16
 */
public class InterruptDemo3 {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            while (true) {
                if (Thread.currentThread().isInterrupted()) {
                    System.out.println("====> " + Thread.currentThread().getName() + "\t Interrupt flag bit: " + Thread.currentThread().isInterrupted() + " program stop ...");
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().isInterrupted());
                    throw new RuntimeException(e);
                }
                System.out.println("====> Hello InterruptDemo3");
            }
        }, "t1");
        t1.start();

        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> t1.interrupt() , "t2").start();
    }
}
