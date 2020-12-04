package com.yangc.thread;

import java.util.concurrent.TimeUnit;

public class SyncMethodsTest {
    // 对 SyncMethodsTest.class 加锁
    public static synchronized void methodOne() {
        System.out.println("methodOne");
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // 对 SyncMethodsTest.class 加锁
    public synchronized static void methodTwo() {
        System.out.println("methodTwo");
    }
}


class AppMain {
    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    SyncMethodsTest.methodOne();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    SyncMethodsTest.methodTwo();
                }
            }
        }).start();
    }
}
