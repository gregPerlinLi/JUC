package com.gregperlinli.juc.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gregperlinli
 * @date 2021/1/12 15:28
 */
public class ReentrantReadWriteLockDemo {
    public static void main(String[] args) {
        MyResource myResource = new MyResource();
        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write( finalI + "", finalI + "");
            }, ("w" + i)).start();
        }

        for (int i = 1; i <= 10; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read( finalI + "");
            }, ("r" + i)).start();
        }

        // Pause thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.write( finalI + "", finalI + "");
            }, ("nw" + i)).start();
        }

        for (int i = 1; i <= 3; i++) {
            int finalI = i;
            new Thread(() -> {
                myResource.read( finalI + "");
            }, ("nr" + i)).start();
        }
    }
}

/**
 * Resource Class, a simple memory
 */
class MyResource {
    Map<String, String> map = new HashMap<>();

    Lock lock = new ReentrantLock();

    ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public void write(String key, String value) {
        rwLock.writeLock().lock();
        try {
            System.out.println("==> " + Thread.currentThread().getName() + "\t " + "Writing ...");
            map.put(key, value);
            // Pause thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("==> " + Thread.currentThread().getName() + "\t " + "Finish Writing");
        } finally {
            rwLock.writeLock().unlock();
        }
    }

    public void read(String key) {
        rwLock.readLock().lock();
        try {
            System.out.println("==> " + Thread.currentThread().getName() + "\t " + "Writing ...");
            String result = map.get(key);
            // Pause thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
            System.out.println("==> " + Thread.currentThread().getName() + "\t " + "Finish Writing\t ==> " + result);
        } finally {
            rwLock.readLock().unlock();
        }
    }

}