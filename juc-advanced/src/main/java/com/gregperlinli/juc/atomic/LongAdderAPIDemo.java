package com.gregperlinli.juc.atomic;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @author gregPerlinLi
 * @date 2022-11-03
 */
public class LongAdderAPIDemo {
    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();
        longAdder.increment();
        longAdder.increment();
        longAdder.increment();
        System.out.println("====> " + longAdder.sum());

        LongAccumulator longAccumulator = new LongAccumulator(Long::sum, 0);
        longAccumulator.accumulate(1);
        longAccumulator.accumulate(3);
        System.out.println("====> " + longAccumulator.get());
    }
}
