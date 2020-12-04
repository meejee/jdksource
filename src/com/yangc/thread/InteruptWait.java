package com.yangc.thread;

import java.sql.Time;
import java.util.Date;

public class InteruptWait {
    public static void main(String[] args) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("isInterrupted before:" + Thread.currentThread().isInterrupted());

                try {
                    // 当对一个线程，调用 interrupt() 时，
                    // 如果线程处于被阻塞状态（例如处于sleep, wait, join 等状态），
                    // 那么线程将立即退出被阻塞状态，并抛出一个InterruptedException异常。

                    Date date = new Date();
                    long start = date.getTime();
                    long end = start;
                    // 循环3s之后调用sleep 让interrupt先调用仍可以中断
                    System.out.println("循环3s start:" + start + " end:" + end);
                    while (end - start < 3000) {
                        end = new Date().getTime();
                    }
                    System.out.println("循环3s end:" + end + " start:" + start);

                    System.out.println("join begin");
//                        Thread.currentThread().join();
                    Thread.sleep(2000);
                    System.out.println("join end");

                } catch (InterruptedException e) {
                    System.out.println("InterruptedException:" + e);
                } catch (Exception e) {
                    System.out.println("Exception:" + e);
                } finally {
                    // 退出循环 线程被中断了  interrupted()会将中断状态重置
                    System.out.println("isInterrupted after:" + Thread.currentThread().isInterrupted());
                }
            }
        });

        thread.start();

        try {
            Thread.sleep(1500);
            // 调用中断
            System.out.println("interrupt begin-------------------");
            thread.interrupt();
            System.out.println("interrupt end-------------------");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
