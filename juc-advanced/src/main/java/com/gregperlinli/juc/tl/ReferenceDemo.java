package com.gregperlinli.juc.tl;

import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-11-10
 */
public class ReferenceDemo {
    public static void main(String[] args) {
        MyObject myObject = new MyObject();
        System.out.println("====> gc before: " + myObject);
        myObject = null;
        // Manually open gc, generally not used.
        System.gc();
        // Pause the thread for a few milliseconds
        try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> gc after: " + myObject);
    }
}
class MyObject {

    /**
     * This method generally does not need to be rewritten, but only for teaching demonstration.
     *
     * @throws Throwable exception
     */
    @Override
    protected void finalize() throws Throwable {
        System.out.println("====> invoke finalize method ...");
    }
}