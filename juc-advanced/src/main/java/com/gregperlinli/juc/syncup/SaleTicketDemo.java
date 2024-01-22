package com.gregperlinli.juc.syncup;

/**
 * @author gregperlinli
 * @date 2020/12/21 16:35
 */
public class SaleTicketDemo {
    public static void main(String[] args) {
        Ticket ticket = new Ticket();
        new Thread(() -> { for ( int i = 0; i < 55; i++) ticket.sale(); }, "A").start();
        new Thread(() -> { for ( int i = 0; i < 55; i++) ticket.sale(); }, "B").start();
        new Thread(() -> { for ( int i = 0; i < 55; i++) ticket.sale(); }, "C").start();
    }
}

class Ticket {
    private int number = 50;
    Object lockObject = new Object();

    public void sale() {
        synchronized (lockObject) {
            if ( number > 0 ) {
                System.out.println("==> \t" + Thread.currentThread().getName() + " Have sold No. \t" + (number--) + "\t Remain: \t" + number);
            }
        }
    }
}