package com.yangc.thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockTest {

    public static synchronized void func(String name) {
        System.out.println(name);
        while (true) {
            try {
                System.out.println(name + "正在执行while");
                // 同一个线程 synchronized 可重入 不能中断
                func2("func2");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static synchronized void func2(String name) {
        System.out.println(name);
        while (true) {
            try {
                System.out.println(name + "正在执行while");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        // 不是同一个线程synchronized 不可重入
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ReentrantLockTest.func("thread1");
//            }
//        }).start();
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                ReentrantLockTest.func("thread2");
//            }
//        }).start();


        Zero zero = new Zero();
        zero.start();

        First first = new First();
        Second second = new Second();
        Third third = new Third();
        Forth forth = new Forth();
        first.start();
        second.start();
        third.start();
        forth.start();
    }



    // 同一个线程可重入 不同线程不可重入
    private static Lock lock = new ReentrantLock();

    static class Zero extends Thread {
        public synchronized void func() {
            lock.lock();
            System.out.println("同一个线程可重入");
            lock.unlock();
        }

        @Override
        public void run() {
            lock.lock();
            System.out.println("我是第0个线程");
            func();
            lock.unlock();
        }
    }



    static class First extends Thread {
        @Override
        public void run() {
            lock.lock();
            int i= 0;
            while (++i < 5) {
                System.out.println("我是第1个线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lock.unlock();
        }
    }

    static class Second extends Thread {
        @Override
        public void run() {
            int i= 0;
            while (++i < 5) {
                lock.lock();
                System.out.println("我是第2个线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }
    }

    static class Third extends Thread {
        @Override
        public void run() {
            int i= 0;
            while (++i < 5) {
                lock.lock();
                System.out.println("我是第3个线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }
    }

    static class Forth extends Thread {
        @Override
        public void run() {
            int i= 0;
            while (++i < 5) {
                lock.lock();
                System.out.println("我是第4个线程");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
            }
        }
    }


}
