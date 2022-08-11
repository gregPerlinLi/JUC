package com.gregperlinli.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

/**
 * @author gregPerlinLi
 * @date 2022-08-03
 */
public class ForkJoinDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // Create split task object
        MyTask myTask = new MyTask(0, 1000);
        // Create fork join pool object
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(myTask);
        // Get the results after the final merge
        Integer result = forkJoinTask.get();
        // Close pool object
        forkJoinPool.shutdown();
        System.out.println("------------------------------------------");
        System.out.println("====> FINAL RESULT: " + result);
    }
}

class MyTask extends RecursiveTask<Integer> {
    /**
     * The split difference cannot exceed 100
     */
    private static final int VALUE = 10;
    /**
     * Split start value
     */
    private int begin;
    /**
     * Split end value
     */
    private int end;
    /**
     * Return results
     */
    private int result;

    /**
     * Contain args constructors
     */
    public MyTask(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }

    /**
     * Split and merge process
     *
     * @return result
     */
    @Override
    protected Integer compute() {
        // Judge whether the two values are greater than 100
        if ( (end - begin) <= VALUE ) {
            // Addition operation
            for (int i = begin; i <= end; i++) {
                result = result + i;
            }
            System.out.println("====> " + Thread.currentThread().getName() + " is calculating: " + begin + " + " + end + " = " + result + " ...");
        } else {
            // Further split
            // Get intermediate value
            int intermediate = (begin + end) / 2;
            // Split left
            MyTask task1 = new MyTask(begin, intermediate);
            // Split right
            MyTask task2 = new MyTask(intermediate + 1, end);
            // Call method split
            task1.fork();
            task2.fork();
            // Consolidated results
            result = task1.join() + task2.join();
        }
        return result;
    }
}
