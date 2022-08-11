package com.gregperlinli.juc.base;

/**
 * @author gregPerlinLi
 * @date 2022-08-03
 */
public class ThreadBaseDemo {
    public static void main(String[] args) {
        new Thread(() -> {

        }, "t1").start();
    }
}
