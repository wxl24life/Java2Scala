package com.ddu.demo.java.algorithm.eip;

/**
 *
 * Design an efficent algorithm that takes a sorted array of distinct integers,
 * returns an index i such that the element at index i equals i.
 *
 * For example, with the input [-2,0,2,3,6,7,9], the algorithm should return 2 or 3.
 *
 * Follow up: what if A is sorted but contains duplicates
 * @author wxl24life
 */
public class Ch12P2 {
    // A is a sorted array with no duplicate entries
    // find if there exists an index i where A[i] == i

    public static int findEquals(int[] A) {
        // binary search
        int len = A.length;
        int lo = 0, hi = len - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] > mid) {
                hi = mid - 1;
            } else if (A[mid] < mid) {
                lo = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    // follow up
    // when A[i] != i, it's possible that A[i] are duplicated values,
    // so we check element at index A[i] to see if A[A[i]] == A[i]
    public static int findEqualsWithDuplicates(int[] A) {
        // binary search
        int len = A.length;
        int lo = 0, hi = len - 1;
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (A[mid] == mid) {
                return mid;
            }

            // check A[A[mid]]
            if (A[mid] >= 0 && A[mid] < len && A[A[mid]] == A[mid]) {
                return A[mid];
            }
            if (A[mid] > mid) {
                hi = mid - 1;
            } else if (A[mid] < mid) {
                lo = mid + 1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] A = new int[] {-2, 0, 1, 3, 5, 6, 7};
        System.out.println(findEquals(A)); // 3

        A = new int[] {2, 2, 2};
        System.out.println(findEqualsWithDuplicates(A)); // 2

        A = new int[] {0, 0, 0};
        System.out.println(findEqualsWithDuplicates(A)); // 0

        A = new int[] {1};
        System.out.println(findEqualsWithDuplicates(A)); // -1
    }
}
