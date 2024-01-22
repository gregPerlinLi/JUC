package com.gregperlinli.juc.syncup;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gregperlinli
 * @date 2021/11/26 16:36
 * VM Options: -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class SynchronizedUpDemo {
    public static void main(String[] args) {
        // Biased
        Object o = new Object();
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        System.out.println("===================================================\n");

        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();
    }

    private static void noLock() {
        Object o = new Object();
        System.out.println("Decimal: " + o.hashCode());
        System.out.println("Hexadecimal: " + Integer.toHexString(o.hashCode()));
        System.out.println("Binary: " + Integer.toBinaryString(o.hashCode()));
        System.out.println(ClassLayout.parseInstance(o).toPrintable());
    }
}
