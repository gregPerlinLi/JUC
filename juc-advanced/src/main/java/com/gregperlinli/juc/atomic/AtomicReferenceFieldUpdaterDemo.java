package com.gregperlinli.juc.atomic;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Requirement:<br/>
 * Multi-threaded concurrently calls the initialization method of a class. If it has not been initialized, the initialization will be performed. It is required that it can only be initialized once, and only one thread operation is successful.
 *
 * @author gregPerlinLi
 * @date 2022-11-03
 */
public class AtomicReferenceFieldUpdaterDemo {
    public static void main(String[] args) {
        MyVar myVar = new MyVar();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                myVar.init(myVar);
            }, "t" + i).start();
        }
    }
}

class MyVar {
    public volatile Boolean isInit = Boolean.FALSE;

    AtomicReferenceFieldUpdater<MyVar, Boolean> referenceFieldUpdater = AtomicReferenceFieldUpdater.newUpdater(MyVar.class, Boolean.class, "isInit");

    public void init(MyVar myVar) {
        if ( referenceFieldUpdater.compareAndSet(myVar, Boolean.FALSE, Boolean.TRUE) ) {
            System.out.println("====> " + Thread.currentThread().getName() + "\t----> Start initialization and it will need 2 seconds ...");
            // Pause the thread for a few seconds
            try { TimeUnit.SECONDS.sleep(2); } catch (InterruptedException e ) { e.printStackTrace(); }
            System.out.println("====> " + Thread.currentThread().getName() + "\t----> Initialization Over ...");
        } else {
            System.out.println("====> " + Thread.currentThread().getName() + "\t----> There is already a thread working on initialization.");
        }
    }
}