package com.simon.guava;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by SunHe on 2020/3/24
 */
public class CompletableFutureCompleteDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> {
            System.out.println("cf任务执行开始");
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("cf任务执行结束");
            return "楼下小黑";
        });

        Executors.newSingleThreadExecutor().execute(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("主动设置cf任务结果");
            cf.complete("程序通事");
        });

        System.out.println("get:"+cf.get());
        TimeUnit.SECONDS.sleep(10);
        System.out.println("get:"+cf.get());
    }

}
