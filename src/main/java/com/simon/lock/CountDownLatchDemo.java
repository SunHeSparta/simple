package com.simon.lock;

import java.util.concurrent.CountDownLatch;

/**
 * Create by SunHe on 2020/3/19
 */
public class CountDownLatchDemo {

    public static long timeTasks(int nThreads, final Runnable task) throws InterruptedException {

        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + "准备执行");
                    startGate.await();
                    try {
                        System.out.println(Thread.currentThread().getName() + "开始执行");
                        task.run();
                    } finally {
                        endGate.countDown();
                        System.out.println(Thread.currentThread().getName() + "执行结束");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
            t.start();
        }

        Thread.sleep(100);
        System.out.println("所有线程还未执行");

        long start = System.nanoTime();
        startGate.countDown();
        System.out.println("所有线程准备执行");
        Thread.sleep(100);
        endGate.await();
        long end = System.nanoTime();
        System.out.println("所有线程执行完毕,耗时:" + (end - start));
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(timeTasks(10, new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName() + "-------work");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }));
    }

}
