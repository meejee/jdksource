package com.yangc.thread;

/**
 * 一个线程不应该由其他线程来强制中断或停止，而是应该由线程自己自行停止。
 * 所以，Thread.stop, Thread.suspend, Thread.resume 都已经被废弃了。
 * 而 Thread.interrupt 的作用其实也不是中断线程，而是「通知线程应该中断了」，
 * 具体到底中断还是继续运行，应该由被通知的线程自己处理。
 * public void interrupt() 将调用者线程的中断状态设为true。
 * public boolean isInterrupted() 判断调用者线程的中断状态。
 * public static boolean interrupted() 只能通过Thread.interrupted()调用。
 * interrupted()会做两步操作：返回当前线程的中断状态；将当前线程的中断状态设为false；
 */
public class Interrupt {

    public static void main(String[] args) {

        Thread thread = new Thread(() -> {
            System.out.println("isInterrupted before while:" + Thread.currentThread().isInterrupted());
            // 当前线程没有被中断 则循环执行
            while (!Thread.interrupted()) {
                System.out.println("isInterrupted in while:" + Thread.currentThread().isInterrupted());
                System.out.println(Thread.currentThread().getName() + ":线程没有被中断");
            }
            // 退出循环 线程被中断了  interrupted()会将中断状态重置
            System.out.println("isInterrupted after while:" + Thread.currentThread().isInterrupted());
        });

        thread.start();
        try {
            Thread.sleep(50);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("interrupt begin");
        // 执行中断 Thread.interrupted()会返回true
        // 通过调用线程对象的interrupt方法将该线程的标识位设为true；可以在别的线程中调用，也可以在自己的线程中调用
        thread.interrupt();
        System.out.println("interrupt end");
        try {
            thread.join();
            System.out.println("main finished");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
