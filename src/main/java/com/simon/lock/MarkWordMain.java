package com.simon.lock;

import com.simon.entity.User;
import org.openjdk.jol.info.ClassLayout;

/**
 * Create by SunHe on 2020/3/21
 */
public class MarkWordMain {

    private static final String SPLITE_STR = "===========================================";
    private static User USER = new User();

    private static void printf() {
        System.out.println(SPLITE_STR);
        System.out.println(ClassLayout.parseInstance(USER).toPrintable());
        System.out.println(SPLITE_STR);
    }

    private static Runnable RUNNABLE = () -> {
        while (!Thread.interrupted()) {
            synchronized (USER) {
                printf();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            new Thread(RUNNABLE).start();
            Thread.sleep(1000);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }
}
