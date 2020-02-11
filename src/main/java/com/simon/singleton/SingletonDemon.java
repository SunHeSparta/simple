package com.simon.singleton;

/**
 * Create by SunHe on 2020/2/11
 */
public class SingletonDemon {

    private static volatile SingletonDemon singletonDemon = null;

    private SingletonDemon() {
        System.out.println("SingletonDemo 构造方法,thread:-" + Thread.currentThread().getName());
    }

    public synchronized static SingletonDemon getInstance() {
        if (singletonDemon == null) {
            synchronized (SingletonDemon.class) {
                singletonDemon = new SingletonDemon();
            }
        }
        return singletonDemon;
    }

    public static void main(String[] args) {
        for (int i = 0; i <= 100; i++) {
            new Thread(SingletonDemon::getInstance, String.valueOf(i)).start();
        }
    }

}
