package com.yangc.normal;

public class RetryTest {

    private static void breakRetry() {
        int i = 0;
        retry:
        for (; ; ) {
            System.out.println("start");
            for (; ; ) {
                i++;
                if (i == 4)
                    break retry;
            }
        }
        //start 进入外层循环
        //4
        System.out.println(i);
    }

    private static void continueRetry() {
        int i=0;
        retry:
        for(;;) {
            System.out.println("start");
            for(;;) {
                i++;
                if (i == 3)
                    continue retry;
                System.out.println("end");
                if (i == 4)
                    break retry;
            }
        }
        //start 第一次进入外层循环
        //end i=1输出
        //end i=2输出
        //start 再次进入外层循环
        //end i=4输出
        //4 最后输出
        System.out.println(i);
    }

    public static void main(String[] args) {
        breakRetry();
        continueRetry();
    }
}
