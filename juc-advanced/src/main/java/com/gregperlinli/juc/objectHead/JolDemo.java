package com.gregperlinli.juc.objectHead;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @author gregPerlinLi
 * @date 2022-11-17
 */
public class JolDemo {
    public static void main(String[] args) {
        Object o = new Object();
        System.out.println("====> " + ClassLayout.parseInstance(o).toPrintable());
        Customer customer = new Customer();
        System.out.println("====> " + ClassLayout.parseInstance(customer).toPrintable());
    }
}
class Customer {
    int id;
    boolean flag = false;
}
