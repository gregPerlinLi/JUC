package com.gregperlinli.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-25
 */
public class LockSellTicket {
    /**
     * Create multiple threads and call methods in the resource class
     */
    public static void main(String[] args) {
        LockTicket ticket = new LockTicket();
        // Create 3 Threads
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "AA").start();
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "BB").start();
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
            }
        }, "CC").start();
    }
}

/**
 * 1. Create resource classes, define attributes and operation methods
 */
class LockTicket {
    /**
     * Ticket num
     */
    private int number = 30;

    /**
     * Create a reentrant lock
     */
    private final ReentrantLock lock = new ReentrantLock(true);

    /**
     * Operate: sell ticket
     */
    public void sell() {
        // Lock
        lock.lock();
        try {
            // Judge: whether there are tickets
            if ( number > 0 ) {
                System.out.println(Thread.currentThread().getName() + " : Sold ticket: " + number-- + " \tRemaining: " + number);
            }
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            // Unlock
            lock.unlock();
        }
    }
}