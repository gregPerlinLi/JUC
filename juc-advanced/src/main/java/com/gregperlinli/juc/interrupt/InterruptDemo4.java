package com.gregperlinli.juc.interrupt;

/**
 * @author gregPerlinLi
 * @date 2022-08-16
 */
public class InterruptDemo4 {

    public static void main(String[] args) {
        // Test whether the current thread is interrupted (check the interrupt flag), return a boolean and clear the interrupt status
        // The interrupt status has been cleared during the second call, and a false will be returned

        System.out.println("====> " + Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("====> " + Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("===============> 1 <===============");
        // Set the interrupt flag bit to true
        Thread.currentThread().interrupt();
        System.out.println("===============> 2 <===============");
        System.out.println("====> " + Thread.currentThread().getName() + "\t" + Thread.interrupted());
        System.out.println("====> " + Thread.currentThread().getName() + "\t" + Thread.interrupted());
    }
}
