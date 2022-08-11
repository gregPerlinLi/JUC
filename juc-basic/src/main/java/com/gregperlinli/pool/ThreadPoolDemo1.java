package com.gregperlinli.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Three common thread pools
 *
 * @author gregPerlinLi
 * @date 2022-08-02
 */
public class ThreadPoolDemo1 {
    public static void main(String[] args) {
        // A pool of N threads, N = 5
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
        // A pool of one threads
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();
        // A pool of scalable threads
        ExecutorService threadPool3 = Executors.newCachedThreadPool();
        try {
            // 10 requests
            for (int i = 1; i <= 100; i++) {
                // Execute
                threadPool3.execute(() -> {
                    System.out.println("====> " + Thread.currentThread().getName() + " is processing business");
                });
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            // Close thread pool
            threadPool3.shutdown();
        }
    }
}
