package com.gregperlinli.juc.volatiles;

/**
 * @author gregPerlinLi
 * @date 2022-11-01
 */
public class SafeDoubleCheckSingleton {
    private static SafeDoubleCheckSingleton singleton;

    /**
     * Privatization construction method
     */
    private SafeDoubleCheckSingleton() {

    }

    /**
     * Double lock design
     */
    public static SafeDoubleCheckSingleton getInstance() {
        if ( singleton == null ) {
            // 1. When multi-threaded concurrently creates objects, locking ensures that only one thread can create objects.
            synchronized ( SafeDoubleCheckSingleton.class ) {
                if ( singleton == null ) {
                    // TODO: Hazards: In a multi-threaded environment, due to reordering, the object may be read by other threads before initialization.
                    singleton = new SafeDoubleCheckSingleton();
                }
            }
        }
        // 2. After the object is created, the execution of getInstance() will not need to get the lock, but directly return to the created object.
        return singleton;
    }
}
