package com.gregperlinli.juc.rwlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gregperlinli
 * @date 2020/11/26 16:36
 */
public class LockDowngradingDemo {
    public static void main(String[] args) {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        Lock readLock = readWriteLock.readLock();
        Lock writeLock = readWriteLock.writeLock();

        // Thread A
        readLock.lock();
        try {
            System.out.println("==> Write");
        } finally {
            readLock.unlock();
        }

        // Thread B
        readLock.lock();
        try {
            System.out.println("==> Read");
        } finally {
            readLock.unlock();
        }

        // All in one
        writeLock.lock();
        try {
            System.out.println("==> Write");
            /*
             * ...
             */
            readLock.lock();
            try {
                System.out.println("==> Read");
                /*
                 * ...
                 */
            } finally {
                writeLock.unlock();
            }
        } finally {
            readLock.unlock();
        }
    }
}
