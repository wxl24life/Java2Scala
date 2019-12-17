package com.ddu.demo.java.algorithm.recursion;

import java.util.Arrays;
import java.util.Random;

/**
 * @author wxl24life
 */
public class QuickSort {
    Random random = new Random();
    // 16:22
    public void quickSort(int[] A) {
        sort(A, 0, A.length - 1);
    }

    private void sort(int[] A, int start, int end) {
        int part = partition2(A, start, end);
        sort(A, start, part - 1);
        sort(A, part + 1, end);
    }

    private int partition(int[] A, int start, int end) {
        if (start >= end) return end;
        int pivot = start + random.nextInt(end - start);
        // swap A[pivot] with A[end]
        swap(A, pivot, end);

        int p = start, q = end - 1;
        while (p <= q) {
            while (p <= q && A[p] < A[end]) {
                p++;
            }
            while (p <= q && A[q] > A[end]) {
                q--;
            }
            if (p > q) break;
            swap(A, p, q);
        }
        swap(A, end, q);
        return q;
    }

    private int partition2(int[] A, int start, int end) {
        if (start >= end) return end;
        int pivot = start + random.nextInt(end - start);
        // swap A[pivot] with A[end]
        swap(A, pivot, end);

        int p = start, q = start;
        while (p < end) {
            while (p < end && A[p] >= A[end]) {
                p++;
            }
            if (p == end) break;
            swap(A, p++, q++);
        }
        swap(A, end, q);
        return q;
    }


    private void swap(int[] A, int i, int j) {
        int tmp = A[i];
        A[i] = A[j];
        A[j] = tmp;
    }

    public static void main(String[] args) {
        int[] A = new int[] {1,5,3,4,5,4,6};
        new QuickSort().quickSort(A);
        System.out.println(Arrays.toString(A));
    }
}
