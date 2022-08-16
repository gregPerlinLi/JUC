package com.gregperlinli.juc.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-12
 */
public class DeadLockDemo {
    public static void main(String[] args) {
        final Object objectA = new Object();
        final Object objectB = new Object();
        new Thread(() -> {
            synchronized ( objectA ) {
                System.out.println("====> " + Thread.currentThread().getName() + " Lock a is already held, and try to acquire lock b...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized ( objectB ) {
                    System.out.println("====> " + Thread.currentThread().getName() + " Lock b is already held");
                }
            }
        }, "A").start();
        new Thread(() -> {
            synchronized ( objectB ) {
                System.out.println("====> " + Thread.currentThread().getName() + " Lock b is already held, and try to acquire lock a...");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                synchronized ( objectA ) {
                    System.out.println("====> " + Thread.currentThread().getName() + " Lock a is already held");
                }
            }
        }, "B").start();
    }
}
