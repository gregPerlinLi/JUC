package com.gregperlinli.juc.cf;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author gregPerlinLi
 * @date 2022-08-04
 */
public class FutureAPIDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<String>(() -> {
            System.out.println("====> " + Thread.currentThread().getName() + " is come in...");
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e ) { e.printStackTrace(); }
            return "====> Task over";
        });
        new Thread(futureTask, "t1").start();
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Is doing other tasks ...");
        while ( true ) {
            if ( futureTask.isDone() ) {
                System.out.println(futureTask.get());
                break;
            } else {
                // Pause the thread for a few seconds
                try { TimeUnit.MILLISECONDS.sleep(500); } catch ( InterruptedException e ) { e.printStackTrace(); }
                System.out.println("====> Waiting for results ...");
            }
        }
    }
}
