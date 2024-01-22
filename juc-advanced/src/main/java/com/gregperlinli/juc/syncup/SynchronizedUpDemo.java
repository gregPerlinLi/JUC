package com.gregperlinli.juc.syncup;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author gregperlinli
 * @date 2021/11/26 16:36
 * VM Options: -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class SynchronizedUpDemo {

    public static void main(String[] args) {
        Object o = new Object();

        synchronized (o) {
            o.hashCode();
            System.out.println("==> Bias locking process encounters a request for consistency hash computation, immediately revoke the bias lock, inflate to heavyweight lock");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void test1() {
        Object o = new Object();
        System.out.println("==> It's supposed to be bias lock ");
        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        o.hashCode();

        synchronized (o) {
            System.out.println("==> It's supposed to be bias lock, but because of the identity hash code, it will be directly upgraded to a lightweight lock ");
            System.out.println(ClassLayout.parseInstance(o).toPrintable());
        }
    }

    private static void heavyweightLock() {
        Object objectLock = new Object();
        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(ClassLayout.parseInstance(objectLock).toPrintable());
            }
        }, "t1").start();
        new Thread(() -> {
            synchronized (objectLock){
                System.out.println(ClassLayout.parseInstance(objectLock).toPrintable());
            }
        }, "t2").start();
    }

    private static void lightweightLock() {
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
