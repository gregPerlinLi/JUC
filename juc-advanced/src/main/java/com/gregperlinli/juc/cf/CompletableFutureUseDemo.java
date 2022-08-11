package com.gregperlinli.juc.cf;

import java.util.concurrent.*;

/**
 * @author gregPerlinLi
 * @date 2022-08-04
 */
public class CompletableFutureUseDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println("====> " + Thread.currentThread().getName() + " ----> Come in ...");
                int result = ThreadLocalRandom.current().nextInt(10);
                // Pause the thread for a few seconds
                try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
                System.out.println("====> The result will appear in a second, result is: " + result);
                if ( result >= 5 ) {
                    throw new ArithmeticException("Value greater than 5");
                }
                return result;
            }, threadPool).whenComplete((v, e) -> {
                if ( e == null ) {
                    System.out.println("====> Calculate complete, and update the system value: " + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("====> There is an exception: " + e.getCause() + "\t" + e.getMessage());
                return null;
            });
            System.out.println("====> Thread " + Thread.currentThread().getName() + " is start completing other tasks");
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }
    }

    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + " ----> Come in ...");
            int result = ThreadLocalRandom.current().nextInt(10);
            // Pause the thread for a few seconds
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("====> The result will appear in a second, result is: " + result);
            return result;
        });
        System.out.println("====> Thread " + Thread.currentThread().getName() + " is start completing other tasks");
        System.out.println(completableFuture.get());
    }
}
