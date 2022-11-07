package com.gregperlinli.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;

/**
 * Requirements: 50 threads, 1 million requests per thread, statistics of total likes
 *
 * @author gregPerlinLi
 * @date 2022-11-03
 */
public class AccumulatorCompareDemo {

    public static final int TIMES = 1000000;
    public static final int THREAD_NUMBER = 50;

    public static void main(String[] args) throws InterruptedException {
        ClickNumber clickNumber = new ClickNumber();
        long startTime, endTime;
        CountDownLatch countDownLatch1 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch2 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch3 = new CountDownLatch(THREAD_NUMBER);
        CountDownLatch countDownLatch4 = new CountDownLatch(THREAD_NUMBER);
        // synchronized
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < TIMES; j++) {
                        clickNumber.clickBySynchronized();
                    }
                } finally {
                    countDownLatch1.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch1.await();
        endTime = System.currentTimeMillis();
        System.out.println("====> Time consumption: " + (endTime - startTime) + " millisecond\t----> clickBySynchronized: " + clickNumber.number);

        // AtomicLong
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < TIMES; j++) {
                        clickNumber.clickByAtomicLong();
                    }
                } finally {
                    countDownLatch2.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch2.await();
        endTime = System.currentTimeMillis();
        System.out.println("====> Time consumption: " + (endTime - startTime) + " millisecond\t----> AtomicLong: " + clickNumber.atomicLong.get());

        // LongAdder
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < TIMES; j++) {
                        clickNumber.clickByLongAdder();
                    }
                } finally {
                    countDownLatch3.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch3.await();
        endTime = System.currentTimeMillis();
        System.out.println("====> Time consumption: " + (endTime - startTime) + " millisecond\t----> LongAdder: " + clickNumber.longAdder.sum());

        // LongAccumulator
        startTime = System.currentTimeMillis();
        for (int i = 1; i <= THREAD_NUMBER; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < TIMES; j++) {
                        clickNumber.clickByLongAccumulator();
                    }
                } finally {
                    countDownLatch4.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch4.await();
        endTime = System.currentTimeMillis();
        System.out.println("====> Time consumption: " + (endTime - startTime) + " millisecond\t----> LongAccumulator: " + clickNumber.longAccumulator.get());
    }
}

class ClickNumber {
    int number = 0;

    public synchronized void clickBySynchronized() {
        number++;
    }

    AtomicLong atomicLong = new AtomicLong(0);
    public void clickByAtomicLong() {
        atomicLong.getAndIncrement();
    }

    LongAdder longAdder = new LongAdder();
    public void clickByLongAdder() {
        longAdder.increment();
    }

    LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
    public void clickByLongAccumulator() {
        longAccumulator.accumulate(1);
    }
}
