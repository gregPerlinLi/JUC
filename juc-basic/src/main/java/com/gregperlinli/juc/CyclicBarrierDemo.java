package com.gregperlinli.juc;

import java.util.concurrent.CyclicBarrier;

/**
 * Demonstrate {@link java.util.concurrent.CyclicBarrier}
 *
 * @author gregPerlinLi
 * @date 2022-07-29
 */
public class CyclicBarrierDemo {
    /**
     * Create fixed value
     */
    public static final int NUMBER = 10;

    /**
     * Collect seals
     */
    public static void main(String[] args) {
        // Create CyclicBarrier
        CyclicBarrier cyclicBarrier = new CyclicBarrier(NUMBER, () -> {
                                            System.out.println("===== Collect ten seals and win prizes =====");
                                        });

        // Process of collecting seals
        for (int i = 1; i <= NUMBER; i++) {
            new Thread(() -> {
                try {
                    System.out.println("No." + Thread.currentThread().getName() + " seal have been collected ...");
                    // Waiting
                    cyclicBarrier.await();
                    System.out.println("End of collection of seal " + Thread.currentThread().getName());
                } catch ( Exception e ) {
                    e.printStackTrace();
                }

            }, String.valueOf(i)).start();
        }
    }
}
