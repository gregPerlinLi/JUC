package com.gregperlinli.juc.cf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * E-commerce website price comparison cases
 *
 * @author gregPerlinLi
 * @date 2022-08-05
 */
public class CompletableFutureMallDemo {
    static List<NetMall> netMalls = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao"),
            new NetMall("pdd"),
            new NetMall("tmall")
    );

    /**
     * Step by step <br/>
     * {@code List<NetMall>} --> {@code Map} --> {@code List<String>}
     */
    public static List<String> getPrice(List<NetMall> netMalls, String productName) {
        return netMalls
                .stream()
                .map(netMall ->
                        String.format("====> %s in %s price is %.2f",
                                        productName, netMall.getNetMallName(),
                                        netMall.calcPrice(productName))
                ).collect(Collectors.toList()
                );
    }
    /**
     * All in <br/>
     * {@code List<NetMall>} --> {@code List<CompletableFuture<String>>} --> {@code List<String>}
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> netMalls, String productName) {
        return netMalls
                .stream()
                .map(netMall ->
                        CompletableFuture.supplyAsync(() ->
                                String.format("====> %s in %s price is %.2f",
                                        productName, netMall.getNetMallName(),
                                        netMall.calcPrice(productName)))
                ).toList()
                .stream()
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> mySQL = getPrice(netMalls, "MySQL");
        for (String element : mySQL) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("====> Cost time: " + (endTime - startTime) + " millisecond <====");
        System.out.println("=================================================");
        startTime = System.currentTimeMillis();
        List<String> mySQL2 = getPriceByCompletableFuture(netMalls, "MySQL");
        for (String element : mySQL2) {
            System.out.println(element);
        }
        endTime = System.currentTimeMillis();
        System.out.println("====> Cost time: " + (endTime - startTime) + " millisecond <====");
    }
}

class NetMall {
    @Getter
    private String netMallName;

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productNmae) {
        // Pause the thread for a few seconds
        try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e ) { e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productNmae.charAt(0);
    }
}