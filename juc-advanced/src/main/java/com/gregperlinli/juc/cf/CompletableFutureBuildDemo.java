package com.gregperlinli.juc.cf;

import java.util.concurrent.*;

/**
 * @author gregPerlinLi
 * @date 2022-08-04
 */
public class CompletableFutureBuildDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println("===> " + Thread.currentThread().getName());
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }
        }, threadPool);
        System.out.println("====> " + completableFuture.get());*/
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println("====> " + Thread.currentThread().getName());
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }
            return "====> Hello supplyAsync() ...";
        }, threadPool);
        System.out.println(completableFuture.get());
        threadPool.shutdown();
    }
}
