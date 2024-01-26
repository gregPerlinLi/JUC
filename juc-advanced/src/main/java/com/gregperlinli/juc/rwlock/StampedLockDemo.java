package com.gregperlinli.juc.rwlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.StampedLock;

/**
 * @author gregperlinli
 * @date 2019/5/28 15:26
 */
public class StampedLockDemo {

    static int number = 48;
    static StampedLock stampedLock = new StampedLock();

    public static void main(String[] args) {
        StampedLockDemo resource = new StampedLockDemo();

        new Thread(() -> {
            resource.optimisticRead();
        }, "readThread").start();

        // Pause thread for a few seconds
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            System.out.println("==> Write Thread come in ...");
            resource.write();
        }, "writeThread").start();
    }

    private static void traditionalVersion() {
        StampedLockDemo resource = new StampedLockDemo();
        new Thread(() -> {
            resource.read();
        }, "readThread").start();

        // Pause thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }

        new Thread(() -> {
            System.out.println("==> Write Thread come in ...");
            resource.write();
        }, "writeThread").start();

        // Pause thread for a few seconds
        try { TimeUnit.SECONDS.sleep(4); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("==> " + Thread.currentThread().getName() + "\t\t ==> " + "number: " + number);
    }

    /**
     * Write
     */
    public void write() {
        long stamp = stampedLock.writeLock();
        System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Write Thread Ready to Update. ");
        try {
            number++;
            System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Write Thread Updating ...");
        } finally {
            stampedLock.unlockWrite(stamp);
        }
        System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Write Thread Update Finished.");
    }

    /**
     * Pessimistic Read
     */
    public void read() {
        long stamp = stampedLock.readLock();
        System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Read Thread Ready to Read. ");
        try {
            for (int i = 0; i < 4; i++) {
                // Pause thread for a few seconds
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Read Thread Reading ... ");
            }
            int result = number;
            System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Read Thread Read Result: " + result);
        } finally {
            stampedLock.unlockRead(stamp);
        }
        System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Read Thread Read Finished.");
    }

    /**
     * Optimistic Read
     */
    public void optimisticRead() {
        long stamp = stampedLock.tryOptimisticRead();
        try{
            int result = number;
            System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Validate status 4s ago: " + stampedLock.validate(stamp));
            for (int i = 1; i <= 4; i++) {
                // Pause thread for a few seconds
                try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                System.out.println("==> " + Thread.currentThread().getName() +
                                    "\t ==> " + "Read Thread Reading ... " +
                                    i + "s ==> Validate status: " + stampedLock.validate(stamp));
            }
            if ( !stampedLock.validate(stamp) ) {
                System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "The data have been modified!!!");
                stamp = stampedLock.readLock();
                try {
                    System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "From Optimistic Read Lock Upgrade to Pessimistic Read Lock ...");
                    result = number;
                    System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "New Result: " + result);
                } finally {
                    stampedLock.unlockRead(stamp);
                }
            }
            System.out.println("==> " + Thread.currentThread().getName() + "\t ==> " + "Read Thread Read Finished. ==> Final Result: " + result);
        } finally {
            stampedLock.tryUnlockRead();
        }
    }
}
