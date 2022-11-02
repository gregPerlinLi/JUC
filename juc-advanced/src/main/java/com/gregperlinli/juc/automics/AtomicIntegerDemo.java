package com.gregperlinli.juc.automics;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class AtomicIntegerDemo {
    public static final int SIZE = 50;

    public static void main(String[] args) throws InterruptedException {
        MyNumber myNumber = new MyNumber();
        CountDownLatch countDownLatch = new CountDownLatch(SIZE);
        for (int i = 1; i <= SIZE; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        myNumber.addPlusPlus();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, "t" + i).start();
        }
        // Wait until all the threads above are calculated before getting the final value.
        countDownLatch.await();
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Result: " + myNumber.atomicInteger.get());
    }
}

class MyNumber {
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addPlusPlus() {
        atomicInteger.getAndIncrement();
    }
}