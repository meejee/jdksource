package com.yangc.thread;

/**
 * @ClassName SynchronizedMethods
 * @Description TODO
 * @Author yc
 * @Date 2022/2/24 10:11
 * @Version 1.0
 */
public class SynchronizedMethods {

    public void methodA() {
        Integer id = Integer.valueOf(222);
        synchronized (SynchronizedAtomicInteger.getAtomicLock(id)) {
            System.out.println("methodA--begin--");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("methodA--end--");

        }
//        SynchronizedAtomicInteger.releaseAtomicLock("222");
    }


    public void methodB() {
        Integer id = Integer.valueOf(222);
        synchronized (SynchronizedAtomicInteger.getAtomicLock(id)) {
            System.out.println("methodB--begin--");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("methodB--end--");
        }
//        SynchronizedAtomicInteger.releaseAtomicLock("222");
    }


    public void methodC() {
        Integer id = Integer.valueOf(222);
        synchronized (SynchronizedAtomicInteger.getAtomicLock(id)) {
            System.out.println("methodC--begin--");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("methodC--end--");
        }
//        SynchronizedAtomicInteger.releaseAtomicLock("222");
    }
}
