package com.gregperlinli.sync;

/**
 * @author gregPerlinLi
 * @date 2022-07-25
 */
public class SellTicket {
    /**
     * Create multiple threads and call methods in the resource class
     */
    public static void main(String[] args) {
        // Create Ticket object
        Ticket ticket = new Ticket();
        // Create 3 Threads
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "AA").start();
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "BB").start();
        new Thread(() -> {
            // Call ticket selling method
            for (int i = 0; i < 40; i++) {
                ticket.sell();
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }, "CC").start();
    }
}

/**
 * 1. Create resource classes, define attributes and operation methods
 */
class Ticket{
    /**
     * Ticket num
     */
    private int number = 30;

    /**
     * Operate: sell ticket
     */
    public synchronized void sell() {
        // Judge: whether there are tickets
        if ( number > 0 ) {
            System.out.println(Thread.currentThread().getName() + " : Sold ticket: " + number-- + " \tRemaining: " + number);
        }
    }
}
