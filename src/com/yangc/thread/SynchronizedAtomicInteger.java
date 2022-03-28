package com.yangc.thread;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName SyncronizedAtomicInteger
 * @Description TODO
 * @Author yc
 * @Date 2022/2/24 10:04
 * @Version 1.0
 */
public class SynchronizedAtomicInteger {
    // 初始化ConcurrentHashMap锁载体
    private static final ConcurrentHashMap<Integer, Integer> lockMap = new ConcurrentHashMap<>();

    public static Integer getAtomicLock(Integer key) {
        if (lockMap.get(key) == null) {// 当实体ID锁资源为空,初始化锁
            lockMap.putIfAbsent(key, key);// 初始化一个竞争数为0的原子资源
        }
//        int count = lockMap.get(key).incrementAndGet();// 线程得到该资源,原子性+1
        System.out.println("获取锁 size:" + lockMap.size() );
        return lockMap.get(key);// 返回该ID资源锁
    }

//    public  synchronized static void releaseAtomicLock(String key) {
//        if (lockMap.get(key) != null) {// 当实体ID资源不为空,才可以操作锁,防止抛出空指针异常
//            int source = lockMap.get(key).decrementAndGet();// 线程释放该资源,原子性-1
//            if (source <= 0) {// 当资源没有线程竞争的时候，就删除掉该锁,防止内存溢出
//                lockMap.remove(key);
//                System.out.println("资源ID为:" + key + "移除成功");
//            }
//            System.out.println("资源ID为:" + key + ",还有争抢线程数:" + source);
//        }
//    }

    public static void main(String[] args){
        SynchronizedMethods methods = new SynchronizedMethods();
        for (int i=0; i<20; i++) {
            int finalI = i;
            new Thread(()->{
                if (finalI % 3 == 0) {
                    methods.methodA();
                }
                if (finalI % 3 == 1) {
                    methods.methodB();
                }

                if (finalI % 3 == 2) {
                    methods.methodC();
                }
            }).start();
        }
    }
}
