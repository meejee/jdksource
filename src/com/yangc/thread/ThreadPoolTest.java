package com.yangc.thread;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolTest {
    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 3,
            TimeUnit.SECONDS, new LinkedBlockingQueue<>());

    public static void main(String[] args) {
//        ThreadPoolTest.executor.awaitTermination()
    }
}
