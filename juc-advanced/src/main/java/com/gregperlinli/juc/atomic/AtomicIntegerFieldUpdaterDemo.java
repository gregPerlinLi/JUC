package com.gregperlinli.juc.atomic;

import java.util.concurrent.CountDownLatch;

/**
 * Operate some fields of non-threaded security objects in a thread-safe way.<br/>
 * Requirements:<br/>
 * 10 threads, transfer 1000 per thread, do not use synchronized, try to use AutomaticIntegerFieldUpdater to implement
 *
 * @author gregPerlinLi
 * @date 2022-11-03
 */
public class AtomicIntegerFieldUpdaterDemo {
    public static void main(String[] args) throws InterruptedException {
        BankAccount bankAccount = new BankAccount();
        CountDownLatch countDownLatch = new CountDownLatch(10);

        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    for (int j = 0; j < 1000; j++) {
                        bankAccount.add();
                    }
                } finally {
                    countDownLatch.countDown();
                }
            }, "t" + i).start();
        }
        countDownLatch.await();
        System.out.println("====> " + Thread.currentThread().getName() + "\t ----> Result: " + bankAccount.money);
    }
}

class BankAccount {
    String bankName = "CCB";
    int money = 0;

    public synchronized void add() {
        money++;
    }
}