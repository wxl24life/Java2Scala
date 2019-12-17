package com.ddu.demo.java.algorithm.recursion;

import java.util.Arrays;

/**
 * @author wxl24life
 */
public class MergeSort {

    public int[] mergeSort(int[] A) {
        // use temporary array for merging stage
        int[] ans = new int[A.length];
        sort(A, 0, A.length - 1, ans);
        return ans;
    }

    private void sort(int[] A, int start, int end, int[] ans) {
        if (start >= end) return;
        int mid = start + (end - start) / 2;
        sort(A, start, mid, ans);
        sort(A, mid + 1, end, ans);
        merge(A, start, end, mid, ans);
    }

    // merge two sub array, move to ans with index range [start, end]
    private void merge(int[] A, int start, int end, int mid, int[] ans) {
        int i = start, j = mid + 1, k = start;
        System.out.println(String.format("%s,%s,%s", i, j, k));
        while (i <= mid && j <= end) {
            if (A[i] <= A[j]) {
                ans[k++] = A[i++];
            } else {
                ans[k++] = A[j++];
            }
        }
        while (i <= mid) {
            ans[k++] = A[i++];
        }
        while (j <= end) {
            ans[k++] = A[j++];
        }
        // assign back to array A
        for (int m = start; m <= end; m++) {
            A[m] = ans[m];
        }
    }

    public static void main(String[] args) {
        int[] A = new int[] {3,2,1,5,4,6};
        int[] ans = new MergeSort().mergeSort(A);
        System.out.println(Arrays.toString(ans));
    }
}
