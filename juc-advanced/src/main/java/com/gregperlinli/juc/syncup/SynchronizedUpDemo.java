package com.gregperlinli.juc.syncup;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gregperlinli
 * @date 2021/11/26 16:36
 * VM Options: -XX:-UseBiasedLocking
 */
public class SynchronizedUpDemo {
    private static Object objectLock = new Object();
    public static void main(String[] args) {
        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(ClassLayout.parseInstance(objectLock).toPrintable());
            }
        }, "t1").start();

        System.out.println("===================================================\n");

        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(ClassLayout.parseInstance(objectLock).toPrintable());
            }
        }, "t2").start();
    }

    private static void casLock() {
        Object o = new Object();
        new Thread(() -> {
            synchronized (o) {
                System.out.println(ClassLayout.parseInstance(o).toPrintable());
            }
        }, "t1").start();
    }

    private static void biasedLock() {
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
