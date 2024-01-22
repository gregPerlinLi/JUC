package com.gregperlinli.juc.syncup;

/**
 * @author gregperlinli
 * @date 2020/12/21 16:36
 */
public class LockBigDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            // Before optimization
            synchronized (objectLock) {
                System.out.println("==> 111111");
            }
            synchronized (objectLock) {
                System.out.println("==> 222222");
            }
            synchronized (objectLock) {
                System.out.println("==> 333333");
            }
            synchronized (objectLock) {
                System.out.println("==> 444444");
            }

            // Optimization by JIT
            synchronized (objectLock) {
                System.out.println("==> 111111");
                System.out.println("==> 222222");
                System.out.println("==> 333333");
                System.out.println("==> 444444");
            }
        }, "t1").start();
    }
}
