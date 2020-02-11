package com.simon.volatilep;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by SunHe on 2020/2/11
 */
public class VolatileDemo {

    public static void main(String[] args) {
        MyData myData = new MyData();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    myData.addPlusPlus();
                }
            }, String.valueOf(i)).start();
        }

        while (Thread.activeCount() > 2) {
            Thread.yield();
        }
//        System.out.println(myData.i);
        System.out.println(myData.atomicInteger.get());
    }

}

class MyData {
    //    volatile int i = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    void addPlusPlus() {
//        i++;
        atomicInteger.getAndIncrement();
    }
}
