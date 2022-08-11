package com.gregperlinli.juc;

import java.util.Random;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Demonstrate {@link SemaphoreDemo}
 *
 * @author gregPerlinLi
 * @date 2022-07-29
 */
public class SemaphoreDemo {
    /**
     * 6 cars seize 3 parking spaces
     */
    public static void main(String[] args) {
        // Create Semaphore object
        Semaphore semaphore = new Semaphore(3);
        // Simulate 6 cars
        for (int i = 1; i <= 6; i++) {
            new Thread(() -> {
                try {
                    // Seize parking lot
                    semaphore.acquire();
                    System.out.println("====> No." + Thread.currentThread().getName() + " car have seized the parking lot ...");
                    // Set random parking time
                    TimeUnit.SECONDS.sleep(new Random().nextInt(1,10));
                    // Leave parking lot
                    System.out.println("<==== No." + Thread.currentThread().getName() + " car have seized the parking lot ...");
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
                    semaphore.release();
                }
            }, String.valueOf(i)).start();
        }
    }
}
