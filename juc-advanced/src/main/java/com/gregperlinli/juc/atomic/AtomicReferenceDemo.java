package com.gregperlinli.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.concurrent.atomic.AtomicReference;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> userAtomicReference = new AtomicReference<>();
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 28);
        userAtomicReference.set(z3);
        System.out.println("====> " + userAtomicReference.compareAndSet(z3, li4) + "\t Result: " + userAtomicReference.get().toString());
        System.out.println("====> " + userAtomicReference.compareAndSet(z3, li4) + "\t Result: " + userAtomicReference.get().toString());
    }
}

@Getter
@ToString
@AllArgsConstructor
class User {
    String username;
    int age;
}