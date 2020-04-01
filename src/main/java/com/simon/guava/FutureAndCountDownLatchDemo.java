package com.simon.guava;

import java.util.List;
import java.util.concurrent.*;

/**
 * Create by SunHe on 2020/3/24
 */
public class FutureAndCountDownLatchDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch countDownLatch = new CountDownLatch(2);

        Future<String> orderAirplane = executorService.submit(() -> {
            System.out.println("查询航班");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购航班");
            countDownLatch.countDown();
            return "航班号";
        });

        Future<String> orderHotel = executorService.submit(() -> {
            System.out.println("查询酒店");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("订购酒店");
            countDownLatch.countDown();
            return "酒店";
        });

        countDownLatch.await();

        Future<String> orderCar = executorService.submit(() -> {
            System.out.println("根据航班和酒店订购租车服务");
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "租车信息";
        });

        System.out.println(orderCar.get());

        executorService.shutdownNow();

        System.out.println("任务结束");
    }

}
