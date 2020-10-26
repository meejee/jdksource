package com.yangc;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class AppMain {
    public static void main(String[] args) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("hello");
        arrayList.add("world");
        System.out.println(arrayList);
    }
}
