package com.ddu.demo.java.algorithm.eip;

/**
 * A cyclically sorted array, e.x. [7,8,9,10,1,2]
 *
 * return the index of the minumum element
 *
 * @author wxl24life
 */
public class Ch12P3 {

    /**
     * Binary search: compare A[mid] with A[lo] (or A[mid] with A[hi])
     * @param A
     * @return
     */
    public static int minOfACyclicallySortedArray(int[] A) {
        int len = A.length;
        int lo = 0, hi = len - 1;

        // A.length == 1 or this is a ascending sorted array
        if (lo == hi || A[lo] < A[hi]) return lo;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            // compare A[mid] with A[lo]
            if (A[mid] > A[lo]) {
                lo = mid + 1;
            } else if (A[mid] < A[lo]){
                // A[mid] < A[lo]
                hi = mid;
            } else {
                // A[mid] == A[lo] (mid == lo)
                return hi;
            }
        }

        return -1;
    }

    public static void main(String[] args) {
        System.out.println(minOfACyclicallySortedArray(new int[] {1})); // 0
        System.out.println(minOfACyclicallySortedArray(new int[] {2,1})); // 1
        System.out.println(minOfACyclicallySortedArray(new int[] {2,3,1})); // 2
        System.out.println(minOfACyclicallySortedArray(new int[] {3,1,2})); // 1
        System.out.println(minOfACyclicallySortedArray(new int[] {3,4,1,2})); // 3
    }
}
