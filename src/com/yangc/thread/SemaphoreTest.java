package com.yangc.thread;

import java.util.concurrent.Semaphore;

public class SemaphoreTest {
    private static Semaphore sem1;
    private static Semaphore sem2;
    private static Semaphore sem3;
    private static Semaphore sem4;

    static class FirstThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    sem1.acquire();
                    System.out.println("1");
                    sem2.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class SecondThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    sem2.acquire();
                    System.out.println("2");
                    sem3.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ThirdThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    sem3.acquire();
                    System.out.println("3");
                    sem4.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class ForthThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    sem4.acquire();
                    System.out.println("4");
                    sem1.release();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        sem1 = new Semaphore(1);
        sem2 = new Semaphore(1);
        sem3 = new Semaphore(1);
        sem4 = new Semaphore(1);
        try {
            // 不要有sem1.acquire()
            sem2.acquire();
            sem3.acquire();
            sem4.acquire();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new FirstThread().start();
        new SecondThread().start();
        new ThirdThread().start();
        new ForthThread().start();

    }

}
