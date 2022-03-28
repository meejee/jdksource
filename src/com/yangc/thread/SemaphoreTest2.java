package com.yangc.thread;

import java.util.concurrent.*;
// Semaphore的作用：限制线程并发的数量
public class SemaphoreTest2 {
    public static void main(String[] args) {
        // 主机如果是4核8线程 返回8
        int NUMBER_OF_CORES=Runtime.getRuntime().availableProcessors();
        System.out.println("NUMBER_OF_CORES:" + NUMBER_OF_CORES);
        // 一个线程 SynchronousQueue 如果任务提交过去 线程没空处理 线程池会拒绝处理 产生异常
//        ExecutorService executor = new ThreadPoolExecutor(1, 1,
//                20L, TimeUnit.SECONDS,
//                new SynchronousQueue<>());
        // keepAliveTime=20 超过核心线程数的线程处于空闲状态20s后将被回收
        ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE,
        20L, TimeUnit.SECONDS,
        new SynchronousQueue<>());

        final Semaphore semaphore = new Semaphore(3);

        for (int i=0; i<NUMBER_OF_CORES; ++i) {

            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    try {
                        semaphore.acquire();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() +
                    "进入，当前已有" + (3-semaphore.availablePermits()) + "个并发");

                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                    semaphore.release();
                    // 下面代码有时候执行不准确，因为其没有和semaphore.release()合成原子单元
                    System.out.println("线程" + Thread.currentThread().getName() +
                    "已离开，当前还有" + (3-semaphore.availablePermits()) + "个并发");
                }
            };

            executor.execute(runnable);
        }
        executor.shutdown();
        System.out.println("end...............");
    }
}
