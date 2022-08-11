package com.gregperlinli.sync;

/**
 * @author gregPerlinLi
 * @date 2022-07-26
 */
public class ThreadDemo1 {

    /**
     * Create multiple threads and call methods in the resource class
     */
    public static void main(String[] args) {
        Share share = new Share();
        // Create two threads
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1
                    share.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1
                    share.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // +1
                    share.increment();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    // -1
                    share.decrement();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "DD").start();
    }
}


/**
 * 1. Create resource classes, define attributes and operation methods
 */
class Share {
    /**
     * Init value
     */
    private int number = 0;

    /**
     * +1 method
     */
    public synchronized void increment() throws InterruptedException {
        // 2. Judgment, execution, notification
        // if number is not 0, wait
        while ( number != 0 ) {
            this.wait();
        }
        // if number is 0, execute
        number++;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        // Notify other threads
        this.notifyAll();
    }

    /**
     * -1 method
     */
    public synchronized void decrement() throws InterruptedException {
        // 2. Judgment, execution, notification
        // if number is not 1, wait
        while ( number != 1 ) {
            this.wait();
        }
        // if number is 1, execute
        number--;
        System.out.println(Thread.currentThread().getName() + " :: " + number);
        // Notify other threads
        this.notifyAll();
    }
}