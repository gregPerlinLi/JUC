package com.gregperlinli.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-11-01
 */
public class VolatileNoAtomicDemo {
    public static void main(String[] args) {
        MyNumber myNumber = new MyNumber();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    myNumber.addPlusPlus();
                }
            }, "t" + (i + 1)).start();
        }
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> Final result: " + myNumber.number);
    }
}

class MyNumber {
    volatile int number;

    public /*synchronized*/ void addPlusPlus() {
        number++;
    }
}
