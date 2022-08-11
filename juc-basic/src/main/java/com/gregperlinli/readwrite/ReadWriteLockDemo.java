package com.gregperlinli.readwrite;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-29
 */
public class ReadWriteLockDemo {
    public static void main(String[] args) throws InterruptedException {
        MyCache myCache = new MyCache();
        // Create a thread to write data
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                myCache.put(num + "", num + "");
            }, String.valueOf(i)).start();
        }
        TimeUnit.MILLISECONDS.sleep(300);
        // Create a thread to get data
        for (int i = 1; i <= 5; i++) {
            final int num = i;
            new Thread(() -> {
                Object value = myCache.get(num + "");
            }, String.valueOf(i)).start();
        }
    }
}

/**
 * Resource class
 */
class MyCache {
    /**
     * Create a Map set
     */
    private volatile Map<String, Object> map = new HashMap<>(100);

    /**
     * Create {@link ReentrantReadWriteLock}
     */
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();

    /**
     * Store data
     */
    public void put(String key, Object value) {
        // Lock
        rwLock.writeLock().lock();
        try {
            System.out.println(">====> " + Thread.currentThread().getName() + " Writing operation in progress: " + key + " ...");
            // Pause for a moment
            TimeUnit.MICROSECONDS.sleep(300);
            // Store data
            map.put(key, value);
            System.out.println("====== " + Thread.currentThread().getName() + " Write complete: " + key + " ...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Unlock
            rwLock.writeLock().unlock();
        }
    }

    /**
     * Read data
     */
    public Object get(String key) {
        // Lock
        rwLock.readLock().lock();
        Object result = null;
        try {
            System.out.println("<====< " + Thread.currentThread().getName() + " Getting operation in progress: " + key + " ...");
            // Pause for a moment
            TimeUnit.MICROSECONDS.sleep(300);
            // Get data
            result = map.get(key);
            System.out.println("====== " + Thread.currentThread().getName() + " Get complete: " + result + " ...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            // Unlock
            rwLock.readLock().unlock();
        }
        return result;
    }
}