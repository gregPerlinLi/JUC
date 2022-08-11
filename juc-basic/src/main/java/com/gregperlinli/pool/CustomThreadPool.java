package com.gregperlinli.pool;

import java.util.concurrent.*;

/**
 * Create a custom thread pool
 *
 * @author gregPerlinLi
 * @date 2022-08-02
 */
public class CustomThreadPool {
    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                                                2,
                                                5,
                                                2L,
                                                TimeUnit.SECONDS,
                                                new ArrayBlockingQueue<>(3),
                                                Executors.defaultThreadFactory(),
                                                new ThreadPoolExecutor.AbortPolicy()
                                        );
        try {
            // 100 requests
            for (int i = 1; i <= 10; i++) {
                // Execute
                threadPool.execute(() -> {
                    System.out.println("====> " + Thread.currentThread().getName() + " is processing business");
                });
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            // Close thread pool
            threadPool.shutdown();
        }
    }
}
