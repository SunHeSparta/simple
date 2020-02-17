package com.simon.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Create by SunHe on 2020/2/13
 */
public class ReadWriteLockDemo {

    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.put(String.valueOf(finalI), finalI);
            }, String.valueOf(i)).start();
        }

        for (int i = 0; i < 5; i++) {
            int finalI = i;
            new Thread(() -> {
                myCache.get(String.valueOf(finalI));
            }, String.valueOf(i)).start();
        }
    }

}

class MyCache {

    private ReentrantReadWriteLock rtwLock = new ReentrantReadWriteLock();
    private Map<String, Object> map = new HashMap<>();

    public void put(String key, Object value) {

        rtwLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在写入数据");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + "\t 写入完成");
            map.put(key, value);
        } finally {
            rtwLock.writeLock().unlock();
        }
    }

    public Object get(String key) {
        rtwLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "\t 正在读取数据");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + "\t 读取数据完成:" + o);
            return o;
        } finally {
            rtwLock.readLock().unlock();
        }
    }
}
