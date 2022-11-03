package com.gregperlinli.juc.atomic;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

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
                        // bankAccount.add();
                        bankAccount.transMoney(bankAccount);
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
    /**
     * The properties of the update object must be modified with {@code public volatile}.
     */
    public volatile int money = 0;

    public synchronized void add() {
        money++;
    }

    /**
     * Because the attribute method type atom class of an object is an abstract class, you must use the static method newUpdater() to create an updater every time you use it, and you need to set the properties you want to update.
     */
    AtomicIntegerFieldUpdater<BankAccount> fieldUpdater = AtomicIntegerFieldUpdater.newUpdater(BankAccount.class, "money");

    /**
     * No synchronized is added to ensure high-performance atomic properties.
     */
    public void transMoney(BankAccount bankAccount) {
        fieldUpdater.getAndIncrement(bankAccount);
    }
}