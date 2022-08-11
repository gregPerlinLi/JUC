package com.gregperlinli.queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author gregPerlinLi
 * @date 2022-08-02
 */
public class BlockingQueueDemo {
    public static void main(String[] args) throws InterruptedException {
        // Create BlockingQueue
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);
        // 1. Throw exception
        System.out.println("====> " + blockingQueue.add("a1"));
        System.out.println("====> " + blockingQueue.add("b1"));
        System.out.println("====> " + blockingQueue.add("c1"));
        // System.out.println(blockingQueue.add("====> " + "d1"));
        System.out.println("===== " + blockingQueue.element());
        System.out.println("<==== " + blockingQueue.remove());
        System.out.println("<==== " + blockingQueue.remove());
        System.out.println("<==== " + blockingQueue.remove());
        // System.out.println("<==== " + blockingQueue.remove());
        // 2. Special value
        System.out.println("====> " + blockingQueue.offer("a2"));
        System.out.println("====> " + blockingQueue.offer("b2"));
        System.out.println("====> " + blockingQueue.offer("c2"));
        // System.out.println("====> " + blockingQueue.offer("d2"));
        System.out.println("===== " + blockingQueue.peek());
        System.out.println("<==== " + blockingQueue.poll());
        System.out.println("<==== " + blockingQueue.poll());
        System.out.println("<==== " + blockingQueue.poll());
        // System.out.println("====> " + blockingQueue.poll());
        // 3. Blocking
        blockingQueue.put("a3");
        blockingQueue.put("b3");
        blockingQueue.put("c3");
        // blockingQueue.put("d3");
        System.out.println("<==== " + blockingQueue.take());
        System.out.println("<==== " + blockingQueue.take());
        System.out.println("<==== " + blockingQueue.take());
        // System.out.println("<==== " + blockingQueue.take());
        // 4. Timeout
        System.out.println("====> " + blockingQueue.offer("a4"));
        System.out.println("====> " + blockingQueue.offer("b4"));
        System.out.println("====> " + blockingQueue.offer("c4"));
        System.out.println("====> " + blockingQueue.offer("d4", 3L, TimeUnit.SECONDS));
        System.out.println("===== " + blockingQueue.peek());
        System.out.println("<==== " + blockingQueue.poll());
        System.out.println("<==== " + blockingQueue.poll());
        System.out.println("<==== " + blockingQueue.poll());
        System.out.println("====> " + blockingQueue.poll(3L, TimeUnit.SECONDS));
    }
}
