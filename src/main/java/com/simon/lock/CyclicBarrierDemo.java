package com.simon.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Create by SunHe on 2020/3/19
 */
public class CyclicBarrierDemo {

    private static CyclicBarrier cyclicBarrier;

    static class CyclicBarrierThread extends Thread {
        @Override
        public void run() {
            System.out.println("运动员:" + Thread.currentThread().getName() + "已经准备就绪");
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        cyclicBarrier = new CyclicBarrier(5,
                () -> System.out.println("所有运动员都已经到场,比赛开始"));
        for (int i = 0; i < 5; i++) {
            new CyclicBarrierThread().start();
        }
    }

}
