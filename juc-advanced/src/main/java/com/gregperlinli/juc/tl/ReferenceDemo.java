package com.gregperlinli.juc.tl;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-11-10
 */
public class ReferenceDemo {
    public static void main(String[] args) {

    }

    private static void weakReference() {
        WeakReference<MyObject> weakReference = new WeakReference<>(new MyObject());
        System.out.println("====> gc before with enough memory: " + weakReference.get());
        System.gc();
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> gc after with enough memory: " + weakReference.get());
    }

    private static void softReference() {
        SoftReference<MyObject> softReference = new SoftReference<>(new MyObject());
        System.out.println("====> Soft reference: " + softReference.get());
        System.gc();
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch ( InterruptedException e ) { e.printStackTrace(); }
        System.out.println("====> gc after with enough memory: " + softReference.get());
        try {
            // Create a 20MB Object
            byte[] bytes = new byte[20 * 1024 * 1024];
        } catch ( Exception e ) {
            e.printStackTrace();
        } finally {
            System.out.println("====> gc after with not enough memory: " + softReference.get());
        }
    }

    private static void strongReference() {
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