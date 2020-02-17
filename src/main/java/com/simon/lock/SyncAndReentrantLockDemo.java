package com.simon.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Create by SunHe on 2020/2/17
 */
public class SyncAndReentrantLockDemo {

    public static void main(String[] args) {
        SourceData sourceData = new SourceData();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sourceData.print5();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "AA").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sourceData.print10();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "BB").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    sourceData.print15();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, "CC").start();
    }

}

class SourceData {

    private Lock lock = new ReentrantLock();
    private Condition c1 = lock.newCondition();
    private Condition c2 = lock.newCondition();
    private Condition c3 = lock.newCondition();
    private int number = 1;//1:A,2:B,3:C

    void print5() throws Exception {

        try {
            if (lock.tryLock()) {
                //1,判断
                while (number != 1) {
                    c1.await();
                }
                //2,干活
                for (int i = 1; i <= 5; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //3,通知
                number = 2;
                c2.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void print10() throws Exception {

        try {
            if (lock.tryLock()) {
                //1,判断
                while (number != 2) {
                    c2.await();
                }
                //2,干活
                for (int i = 1; i <= 10; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //3,通知
                number = 3;
                c3.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void print15() throws Exception {

        try {
            if (lock.tryLock()) {
                //1,判断
                while (number != 3) {
                    c3.await();
                }
                //2,干活
                for (int i = 1; i <= 15; i++) {
                    System.out.println(Thread.currentThread().getName() + "\t" + i);
                }
                //3,通知
                number = 1;
                c1.signal();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}