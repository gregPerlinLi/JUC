package com.gregperlinli.juc.aqs;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQSDemo {
    public static void main(String[] args) {
        Lock lock = new ReentrantLock(true);

        //
        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("==> Come in A");
                // Pause thread for a few minutes
                try { TimeUnit.MINUTES.sleep(20); } catch (InterruptedException e) { e.printStackTrace(); }
            } finally {
                lock.unlock();
            }
        }, "A").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("==> Come in B");
            } finally {
                lock.unlock();
            }
        }, "B").start();

        new Thread(() -> {
            lock.lock();
            try {
                System.out.println("==> Come in C");
            } finally {
                lock.unlock();
            }
        }, "C").start();
    }
}
