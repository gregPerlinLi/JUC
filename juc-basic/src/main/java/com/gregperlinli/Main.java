package com.gregperlinli;

/**
 * @author gregPerlinLi
 * @date 2022-07-25
 */
public class Main {
    public static void main(String[] args) {
        Thread aa = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "::" + Thread.currentThread().isDaemon());
            while (true) {

            }
        }, "aa");
        // Set daemon thread
        aa.setDaemon(true);
        aa.start();

        System.out.println(Thread.currentThread().getName() + " is over ...");
    }
}
