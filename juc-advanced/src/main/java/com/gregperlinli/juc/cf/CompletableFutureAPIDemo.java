package com.gregperlinli.juc.cf;

import java.util.concurrent.*;

/**
 * @author gregPerlinLi
 * @date 2022-08-05
 */
public class CompletableFutureAPIDemo {
    public static void main(String[] args) {
        CompletableFuture<Integer> result = CompletableFuture.supplyAsync(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Start ...");
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
            return 10;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Start ...");
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
            return 20;
        }), (x, y) -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Start result merging ...");
            return x + y;
        }).thenCombine(CompletableFuture.supplyAsync(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Start ...");
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
            return 30;
        }), (a, b) -> {
            System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Start result merging ...");
            return a + b;
        });
        System.out.println("====> Main thread end ...");
        System.out.println("====> FINAL RESULT: " + result.join());
    }
}
