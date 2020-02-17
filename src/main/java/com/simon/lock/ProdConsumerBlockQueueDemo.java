package com.simon.lock;

import org.apache.commons.lang3.StringUtils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Create by SunHe on 2020/2/17
 */
public class ProdConsumerBlockQueueDemo {

    public static void main(String[] args) {
        MyBlockQueue myBlockQueue = new MyBlockQueue(new ArrayBlockingQueue<>(10));

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t生产者启动");
            System.out.println();
            System.out.println();
            System.out.println();
            myBlockQueue.prod();
        }, "prod").start();

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t消费者启动");
            System.out.println();
            System.out.println();
            System.out.println();
            myBlockQueue.consumer();
        }, "consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();

        System.out.println(Thread.currentThread().getName() + "\t5秒之后停止");
        myBlockQueue.stop();
    }

}

class MyBlockQueue {
    private volatile boolean flag = true;
    private AtomicInteger atomicInteger = new AtomicInteger();
    private BlockingQueue<String> blockingQueue = null;

    public MyBlockQueue(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
    }

    public void prod() {
        while (flag) {
            String i = null;
            boolean offer;
            try {
                i = atomicInteger.incrementAndGet() + "";
                offer = blockingQueue.offer(i, 2, TimeUnit.SECONDS);
                if (offer) {
                    System.out.println(Thread.currentThread().getName() + "\t蛋糕生产成功:" + i);
                } else {
                    System.out.println(Thread.currentThread().getName() + "\t蛋糕生产失败:" + i);
                }
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + "\t老板叫停了生产");
    }

    public void consumer() {
        while (flag) {
            String result;
            try {
                result = blockingQueue.poll(2, TimeUnit.SECONDS);
                if (StringUtils.isBlank(result)) {
                    flag = false;
                    System.out.println(Thread.currentThread().getName() + "\t超过两秒钟没有取到蛋糕");
                    return;
                }
                System.out.println(Thread.currentThread().getName() + "\t取到蛋糕:" + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop() {
        this.flag = false;
    }
}
