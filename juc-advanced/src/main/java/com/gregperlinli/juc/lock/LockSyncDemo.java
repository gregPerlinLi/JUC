package com.gregperlinli.juc.lock;

/**
 * @author gregPerlinLi
 * @date 2022-08-11
 */
public class LockSyncDemo {
    Object object = new Object();
    Book book = new Book();

    public static void main(String[] args) {

    }

    public void m1() {
        synchronized ( book ) {
            System.out.println("====> Hello synchronized code block ...");
        }
    }

    public synchronized void m2() {
        System.out.println("====> Hello synchronized m2 ...");
    }

    public synchronized void m3() {
        System.out.println("====> Hello static synchronized m3 ...");
    }
}

class Book {

}