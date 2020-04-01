package com.simon.guava;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFutureTask;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Create by SunHe on 2020/3/24
 */
public class FutureDemo {

    static ExecutorService executorService = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {
        ListenableFutureTask<String> listenableFutureTask = ListenableFutureTask.create(() -> {
            TimeUnit.SECONDS.sleep(2);
            return "小王@小刘";
        });
        Futures.addCallback(listenableFutureTask, new FutureCallback<String>() {
            @Override
            public void onSuccess(@Nullable String result) {
                System.out.println("处理返回结果:" + result);
            }

            @Override
            public void onFailure(Throwable throwable) {
                System.out.println("处理异常");
            }
        }, Executors.newSingleThreadScheduledExecutor());

        executorService.submit(listenableFutureTask);
    }

}
