package com.gregperlinli.juc;

import java.util.concurrent.CountDownLatch;

/**
 * Demonstrate {@link java.util.concurrent.CountDownLatch}
 *
 * @author gregPerlinLi
 * @date 2022-07-29
 */
public class CountDownLatchDemo {
    /**
     * After 6 students left the classroom one after another, the monitor locked the door
     */
    public static void main(String[] args) throws InterruptedException {
        // Create CountDownLatch object and set initial value
        CountDownLatch countDownLatch = new CountDownLatch(6);

        // 6 students left the classroom
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                System.out.println("No." + Thread.currentThread().getName() + " student left the classroom ...");

                // Count -1
                countDownLatch.countDown();
            }, String.valueOf(i)).start();
        }

        // Waiting
        countDownLatch.await();
        System.out.println(Thread.currentThread().getName() + " The monitor locked the door and left.");
    }
}
