package com.gregperlinli.completable;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author gregPerlinLi
 * @date 2022-08-03
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Asynchronous call (no return value)
        CompletableFuture<Void> completableFuture1 = CompletableFuture.runAsync(() -> {
            System.out.println("===> " + Thread.currentThread().getName() + " completableFuture1");
        });
        completableFuture1.get();
        // Asynchronous call (with return value)
        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("===> " + Thread.currentThread().getName() + " completableFuture2");
            // Simulate exception
            // int i = 1 / 0;
            return 1024;
        });
        completableFuture2.whenComplete((t, u) -> {
            // Return value of method
            System.out.println("====> t = " + t);
            // Exception information
            System.out.println("====> u = " + u);
        }).get();
    }
}
