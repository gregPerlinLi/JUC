package com.gregperlinli.juc.tl;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author gregPerlinLi
 * @date 2022-11-09
 */
public class ThreadLocalDemo {
    public static void main(String[] args) {
        House house = new House();
        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5) + 1;
                // System.out.println("====> " + size);
                for (int j = 0; j < size; j++) {
                    house.saleHouse();
                    house.saleVolumeByThreadLocal();
                }
                System.out.println("====> No. " + Thread.currentThread().getName() + " sold " + house.saleVolume.get() + " houses ...");
            }, "t" + i).start();

            // Pause the thread for a few milliseconds
            try { TimeUnit.MILLISECONDS.sleep(300); } catch ( InterruptedException e ) { e.printStackTrace(); }
        }
        System.out.println("====> " + Thread.currentThread().getName() + "\t----> Total sell: " + house.saleCount);
    }
}

class House {
    int saleCount = 0;

    public synchronized void saleHouse() {
        ++saleCount;
    }

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);
    AtomicLong atomicLong = new AtomicLong();
    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}