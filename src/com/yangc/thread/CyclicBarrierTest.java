package com.yangc.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CountDownLatch和CyclicBarrier都有让多个线程等待同步然后再开始下一步动作的意思，
 * 但是CountDownLatch的下一步的动作实施者是主线程，具有不可重复性,
 * 而CyclicBarrier的下一步动作实施者还是“其他线程”本身，具有往复多次实施动作的特点。
 */
public class CyclicBarrierTest {

    public static void main(String[] args) {

        CyclicBarrier barrier = new CyclicBarrier(3);
        for(int i = 0; i < barrier.getParties(); i++){
            new Thread(new MyRunnable(barrier), "队友"+i).start();
        }
        System.out.println("main function is finished.");
    }

    private static class MyRunnable implements Runnable {
        private CyclicBarrier barrier;
        public MyRunnable(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        @Override
        public void run() {
            for (int i=0; i<3; ++i) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("await before i:" + i + " thead name:" + Thread.currentThread().getName());
                try {
                    this.barrier.await();
                } catch (InterruptedException e) {
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("await after i:" + i + " thead name:" + Thread.currentThread().getName());
            }
        }
    }

}
