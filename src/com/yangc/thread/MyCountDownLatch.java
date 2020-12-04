package com.yangc.thread;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;


public class MyCountDownLatch {
    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(1);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 将当前线程加入阻塞队列
                    countDownLatch.await();
                    //和await()类似，只不过等待一定的时间后count值还没变为0的话就会继续执行
//                    countDownLatch.await(3000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是读，写完了才能读");
            }
        }).start();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("我是写");
                // 调用一次就减一
                countDownLatch.countDown();
            }
        }).start();

        try {
            System.out.println("输入字符并回车结束程序：");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


