package com.gregperlinli.juc.volatiles;

import java.util.concurrent.TimeUnit;

/**
 * Use: As a Boolean status flag, it is used to indicate that an important event has occurred, such as initialization completion or task completion
 * Reason: The status flag does not depend on any other state in the program, and usually has only one state conversion
 * Example: Judge whether the business is closed
 *
 * @author gregPerlinLi
 * @date 2022-11-01
 */
public class UseVolatileDemo {
    private volatile static boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            while ( flag ) {
                // Do something...
            }
        }, "t1").start();

        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(2L); } catch (InterruptedException e ) { e.printStackTrace(); }

        new Thread(() -> {
            flag = false;
        }, "t2").start();
    }

    /**
     * Use: When reading is much more than writing, a combination of internal lock and volatile variables is used to reduce the overhead of synchronization.
     * Reason: Use volatile to ensure the visibility of the read operation; use synchronized to ensure the atomicity of the operation.
     */
    public class Counter {
        private volatile int value;

        public int getValue() {
            return value;   // Use volatile to ensure the visibility of read operations.
        }
        public synchronized int increment() {
            return value++; // Use synchronized to ensure the atomicity of compound operations.
        }
    }
}
