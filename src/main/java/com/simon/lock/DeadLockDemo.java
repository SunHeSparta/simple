package com.simon.lock;

import java.util.concurrent.TimeUnit;

/**
 * Create by SunHe on 2020/2/18
 */
public class DeadLockDemo {

    public static void main(String[] args) {
        String lockA = "lockA";
        String lockB = "lockB";
        new Thread(new DeadLockData(lockA, lockB), "ThreadA").start();
        new Thread(new DeadLockData(lockB, lockA), "ThreadB").start();
    }

}


class DeadLockData implements Runnable {
    private String lockA;
    private String lockB;

    public DeadLockData(String lockA, String lockB) {
        this.lockA = lockA;
        this.lockB = lockB;
    }

    @Override
    public void run() {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + "\t持有线程\t" + lockA);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + "\t持有线程\t" + lockB);
            }
        }
    }
}