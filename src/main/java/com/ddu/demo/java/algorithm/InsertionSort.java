package com.ddu.demo.java.algorithm;

import java.util.Arrays;

/**
 * @author wxl24life
 */
public class InsertionSort {

    private void sort(int[] arr, int length) {
        for (int p = 1; p < length; p++) {
            int tmp = arr[p];
            int j = p;
            while (j > 0 && arr[j - 1] > tmp) {
                arr[j] = arr[j - 1];
                j--;
            }
            if (j != p) {
                arr[j] = tmp;
            }
        }
    }

    private void print(int[] arr) {
        for (int element : arr) {
            System.out.print(element + " ");
        }
    }

    public static void main(String[] args) {
        InsertionSort insertionSort = new InsertionSort();
        int[] arr = new int[]{3, 5, 1, 2, 4};
        insertionSort.sort(arr, 5);
        insertionSort.print(arr);

        System.out.println();
        arr = new int[]{1};
        insertionSort.sort(arr, 1);
        insertionSort.print(arr);
    }
}
