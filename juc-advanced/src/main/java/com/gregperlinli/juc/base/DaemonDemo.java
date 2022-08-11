package com.gregperlinli.juc.base;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-04
 */
public class DaemonDemo {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t start running, " +
                    (Thread.currentThread().isDaemon() ? "this is daemon thread" : "this is user thread"));
            while (true) {

            }
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> MAIN THREAD END ...");
    }
}
