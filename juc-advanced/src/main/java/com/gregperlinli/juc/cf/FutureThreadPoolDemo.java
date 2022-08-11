package com.gregperlinli.juc.cf;

import java.util.concurrent.*;

/**
 * @author gregPerlinLi
 * @date 2022-08-04
 */
public class FutureThreadPoolDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // There are three tasks. At present, there is only one thread main processing, time-consuming: 1114 ms
        long startTime = System.currentTimeMillis();
        // Pause the thread for a few seconds
        try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e ) { e.printStackTrace(); }
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e ) { e.printStackTrace(); }
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e ) { e.printStackTrace(); }
        long endTime = System.currentTimeMillis();
        System.out.println("====> Cost time: " + (endTime - startTime) + " millisecond <====");
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> END ...");
        // Three threads. Currently, multiple asynchronous task threads are enabled to process, time-consuming: 308 ms (Do not return results), 508 ms (Return results)
        startTime = System.currentTimeMillis();
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e ) { e.printStackTrace(); }
            return "====> Task1 over ...";
        });
        threadPool.submit(futureTask1);
        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e ) { e.printStackTrace(); }
            return "====> Task2 over ...";
        });
        threadPool.submit(futureTask2);
        try { TimeUnit.MILLISECONDS.sleep(300); } catch (InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> " + futureTask1.get());
        System.out.println("====> " + futureTask2.get());
        threadPool.shutdown();
        endTime = System.currentTimeMillis();
        System.out.println("====> Cost time: " + (endTime - startTime) + " millisecond <====");
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> END ...");
    }
}
