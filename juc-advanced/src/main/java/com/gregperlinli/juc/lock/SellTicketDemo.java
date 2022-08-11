package com.gregperlinli.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Sell ticket demo
 *
 * @author gregPerlinLi
 * @date 2022-08-11
 */
public class SellTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> { for (int i = 0; i < 55; i++) ticket.sell(); }, "a").start();
        new Thread(() -> { for (int i = 0; i < 55; i++) ticket.sell(); }, "b").start();
        new Thread(() -> { for (int i = 0; i < 55; i++) ticket.sell(); }, "c").start();
    }
}

class Ticket {
    private int number = 50;
    Lock lock = new ReentrantLock(true);

    public void sell() {
        lock.lock();
        try {
            if ( number > 0 ) {
                System.out.println("====> " + Thread.currentThread().getName() + " have sold №: \t" + (number--) + "\t, and remain: \t" + number );
            }
        } finally {
            lock.unlock();
        }
    }
}
