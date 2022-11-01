package com.gregperlinli.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-11-01
 */
public class VolatileDemo {

    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Come in ...");
            while ( flag ) {

            }
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> The flag is set to false and the program stops ...");
        }, "t1").start();
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(2); } catch ( InterruptedException e ) { e.printStackTrace(); }
        flag = false;
        System.out.println("====> Main thread modification completed ...");
    }
}
