package com.gregperlinli.lock;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * List collection thread is unsafe
 *
 * @author gregPerlinLi
 * @date 2022-07-26
 */
public class ThreadDemo4 {
    public static void main(String[] args) {
        // Create ArrayList
        // List<String> list = new ArrayList<>();
        // Solve thread safety problems through Vector
        // List<String> list = new Vector<>();
        // Solve thread safety problems through Collections
        // List<String> list = Collections.synchronizedList(new ArrayList<>());
        // Solve thread safety problems through CopyOnWriteArrayList
        /*List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // Add content to the collection
                list.add(UUID.randomUUID().toString().substring(0, 8));
                // Get content from collection
                System.out.println(list);
            }, String.valueOf(i)).start();
        }*/

        // HashSet
        // Set<String> set = new HashSet<>();
        /*Set<String> set = new CopyOnWriteArraySet<>();
        for (int i = 0; i < 30; i++) {
            new Thread(() -> {
                // Add content to the collection
                set.add(UUID.randomUUID().toString().substring(0, 8));
                // Get content from collection
                System.out.println(set);
            }, String.valueOf(i)).start();
        }*/

        // HashMap
        // Map<String, String> map = new HashMap<>();
        Map<String, String> map = new ConcurrentHashMap<>(100);
        for (int i = 0; i < 30; i++) {
            String key = String.valueOf(i);
            new Thread(() -> {
                // Add content to the collection
                map.put(key, UUID.randomUUID().toString().substring(0, 8));
                // Get content from collection
                System.out.println(map);
            }, String.valueOf(i)).start();
        }
    }
}
