package com.gregperlinli.readwrite;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author gregPerlinLi
 * @date 2022-07-29
 */
public class Demo1 {
    public static void main(String[] args) {
        ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
        ReentrantReadWriteLock.ReadLock readLock = rwLock.readLock();
        ReentrantReadWriteLock.WriteLock writeLock = rwLock.writeLock();

        writeLock.lock();
        System.out.println("This is an output");
        readLock.lock();
        System.out.println("<====< Read");
        writeLock.unlock();
        readLock.unlock();
    }
}
