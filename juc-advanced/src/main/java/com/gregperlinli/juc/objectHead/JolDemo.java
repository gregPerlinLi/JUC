package com.gregperlinli.juc.objectHead;

import org.openjdk.jol.vm.VM;

/**
 * @author gregPerlinLi
 * @date 2022-11-17
 */
public class JolDemo {
    public static void main(String[] args) {
        System.out.println("====> " + VM.current().details());
        System.out.println("====> " + VM.current().objectAlignment());
    }
}
class Customer {
    int id;
    boolean flag = false;
}
