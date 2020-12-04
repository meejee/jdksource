package com.yangc.sort;

import java.util.Arrays;

public class BubbleSort {
    private static void sort(int array[]) {
        int tmp = 0;
        int count = 0;
        for(int i = 0; i < array.length; i++) {
            for(int j = 0; j < array.length - i -1; j++) {
                if (array[j] > array[j+1]) {
                    tmp = array[j];
                    array[j] = array[j+1];
                    array[j+1] = tmp;
                    count++;
                }
            }
        }
        System.out.println("count:" + count);
    }

    public static void main(String[] args) {
        int array[] = {3, 4, 2, 0, 1, 7, 9, 5, 8};
        sort(array);
        System.out.println(Arrays.toString(array));
    }

}
