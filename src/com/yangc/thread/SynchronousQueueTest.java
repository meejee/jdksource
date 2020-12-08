package com.yangc.thread;

import java.util.Random;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

public class SynchronousQueueTest {
    public static void main(String[] args) throws InterruptedException {
        SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        new Product(queue).start();
        new Customer(queue).start();
    }
    static class Product extends Thread{
        SynchronousQueue<Integer> queue;
        public Product(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }
        @Override
        public void run(){
            while(true){
                int rand = new Random().nextInt(1000);
                System.out.println("生产了一个产品："+rand);
                System.out.println("等待二秒后运送出去...");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                // offer() 往queue里放一个element后立即返回，
                // 如果碰巧这个element被另一个thread取走了，
                // offer方法返回true，认为offer成功；否则返回false。
                // System.out.println(queue.offer(rand));
                try {
                    //往queue放进去一个element以后就一直wait直到有其他thread进来把这个element取走
                    queue.put(rand);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("queue is empty->" + queue.isEmpty());
            }
        }
    }
    static class Customer extends Thread{
        SynchronousQueue<Integer> queue;
        public Customer(SynchronousQueue<Integer> queue){
            this.queue = queue;
        }
        @Override
        public void run(){
            while(true){
                try {
//                    System.out.println("等待三秒后进行消费...");
//                    try {
//                        TimeUnit.SECONDS.sleep(3);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    System.out.println("消费了一个产品:"+queue.take());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("------------------------------------------");
            }
        }
    }

}
