package com.gregperlinli.juc.syncup;

/**
 * @author gregperlinli
 * @date 2020/11/25 10:36
 */
public class LockClearUpDemo {
    static Object objectLock = new Object();

    public static void main(String[] args) {
        LockClearUpDemo lockClearUpDemo = new LockClearUpDemo();
        for (int i = 1; i <= 10; i++) {
            new Thread(lockClearUpDemo::m1, ("t" + i)).start();
        }
    }

    public void m1() {
//        synchronized (objectLock) {
//            System.out.println("==> hello ==> Lock Clear Up Demo ==> " + Thread.currentThread().getName());
//        }
        // Lock elimination issues, it will simply ignored by JIT
        Object o = new Object();
        synchronized (o) {
            System.out.println("==> hello ==> Lock Clear Up Demo ==> " + Thread.currentThread().getName() +
                                            "\t Object HashCode ==> " + o.hashCode() +
                                            "\t Object Lock HashCode ==> " + objectLock.hashCode());
        }
    }
}
