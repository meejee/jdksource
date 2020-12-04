package com.yangc.thread;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class CopyOnWriteArrayListTest {
    public static void main(String[] args) throws InterruptedException {
        // Iterator遍历时报错java.util.ConcurrentModificationException for循环没报错
        // List<Integer> list = new ArrayList<>();
        List<Integer> list = new CopyOnWriteArrayList<Integer>();
        for(int i = 0; i < 10; i++){
            list.add(i);
        }

        ThreadTest t1 = new ThreadTest(list);
        new Thread(t1).start();

        Iterator<Integer> iterator = list.iterator();
        while(iterator.hasNext()){
            System.out.print(iterator.next() + " ");
            Thread.sleep(1000);
        }

//        for (int i=0; i<list.size(); ++i) {
//            System.out.println(list.get(i) + " ");
//            Thread.sleep(1000);
//        }
    }
}

class ThreadTest implements Runnable{

    private List<Integer> list;
    public ThreadTest(List<Integer> list) {
        this.list = list;
    }
    @Override
    public void run() {
        while(true){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.list.add(10);
        }
    }
}
