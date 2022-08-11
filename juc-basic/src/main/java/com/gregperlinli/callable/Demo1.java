package com.gregperlinli.callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Compare the differences between {@code Runnable} and {@code Callable} interfaces
 *
 * @author gregPerlinLi
 * @date 2022-07-27
 */
public class Demo1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Create a thread using Runnable
        new Thread(new MyThread1(), "AA").start();
        // Create a thread using Callable with FutureTask
        FutureTask<Integer> futureTask1 = new FutureTask<>(new MyThread2());
        FutureTask<Integer> futureTask2 = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + " Come in with callable");
            return 1024;
        });
        // Create a thread
        new Thread(futureTask1, "XiaoMing").start();
        new Thread(futureTask2, "XiaoHong").start();

        while ( !futureTask2.isDone() ) {
            System.out.println("Waiting ...");
        }
        // Get the returned results
        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());
        System.out.println(Thread.currentThread().getName() + " is over.");
    }
}

/**
 * Implement {@code Runnable} interface
 */
class MyThread1 implements Runnable {
    @Override
    public void run() {

    }
}

/**
 * Implement {@code Callable} interface
 */
class MyThread2 implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        System.out.println(Thread.currentThread().getName() + " Come in with callable");
        return 200;
    }
}
