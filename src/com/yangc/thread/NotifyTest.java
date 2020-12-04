package com.yangc.thread;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class NotifyTest{

    //等待列表, 用来记录等待的顺序
    private static List<String> waitList = new LinkedList<>();
    //唤醒列表, 用来唤醒的顺序
    private static List<String> notifyList = new LinkedList<>();

    private static Object lock = new Object();


    public static void main(String[] args) throws InterruptedException{

        //创建50个线程
        for(int i=0;i<50;i++){
            String threadName = Integer.toString(i);
            new Thread(() -> {
                synchronized (lock) {
                    String cthreadName = Thread.currentThread().getName();
                    System.out.println("线程 ["+cthreadName+"] 正在等待.");
                    waitList.add(cthreadName);
                    try {
                        // 将当前线程放入等待队列 并释放锁
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("线程 ["+cthreadName+"] 被唤醒了.");
                    notifyList.add(cthreadName);
                }
            },threadName).start();

            TimeUnit.MILLISECONDS.sleep(50);
        }

        TimeUnit.SECONDS.sleep(1);

        for(int i=0;i<50;i++){
            synchronized (lock) {
                // 唤醒等待队列的一个线程 实际上需要synchronized块执行完成才会释放锁
                lock.notify();
            }
            // 如果不加这句 那么主线程和唤醒的线程将竞争这把锁 唤醒的线程获取锁执行的顺序可能不一样（唤醒的顺序一样）
            TimeUnit.MILLISECONDS.sleep(10);
        }
        TimeUnit.SECONDS.sleep(1);
        System.out.println("wait顺序:"+waitList.toString());
        System.out.println("唤醒顺序:"+notifyList.toString());
    }
}
