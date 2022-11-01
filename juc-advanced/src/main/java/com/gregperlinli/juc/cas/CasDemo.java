package com.gregperlinli.juc.cas;

import sun.misc.Unsafe;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author gregPerlinLi
 * @date 2022-11-01
 */
public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println("====> " + atomicInteger.compareAndSet(5, 2022) + "\t Result: " + atomicInteger.get());
        System.out.println("====> " + atomicInteger.compareAndSet(5, 2022) + "\t Result: " + atomicInteger.get());
    }
}
