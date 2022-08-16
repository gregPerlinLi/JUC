package com.gregperlinli.juc.interrupt;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-16
 */
public class InterruptDemo {
    static volatile boolean isStop = false;

    public static void main(String[] args) {
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
