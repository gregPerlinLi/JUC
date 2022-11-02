package com.gregperlinli.juc.atomic;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author gregPerlinLi
 * @date 2022-11-02
 */
public class AtomicStampedDemo {
    public static void main(String[] args) {
        Book javaBook = new Book(1, "javaBook");
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println("====> " + atomicStampedReference.getReference() + "\t ----> Version: " + atomicStampedReference.getStamp());
        Book mysqlBook = new Book(2, "mysqlBook");
        boolean b;
        b = atomicStampedReference.compareAndSet(javaBook, mysqlBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println("====> " + b + "\t" + atomicStampedReference.getReference() + "\t ----> Version: " + atomicStampedReference.getStamp());
    }
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class Book {
    private int id;
    private String bookName;
}