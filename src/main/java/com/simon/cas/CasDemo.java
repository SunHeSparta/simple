package com.simon.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by SunHe on 2020/2/11
 */
public class CasDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);
        System.out.println(atomicInteger.compareAndSet(5, 2019));
        System.out.println(atomicInteger.compareAndSet(5, 2020));
    }

}
