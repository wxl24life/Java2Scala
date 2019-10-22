package com.ddu.demo.java.algorithm;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 * Given 2 lists a and b. Each element is a pair of integers where the first integer represents the unique id
 * and the second integer represents a value. Your task is to find an element from a and an element form b such that
 * the sum of their values is less or equal to target and as close to target as possible.
 * Return a list of ids of selected elements. If no pair is possible, return an empty list.
 *
 * Input:
 * a = [[1, 3], [2, 5], [3, 7], [4, 10]]
 * b = [[1, 2], [2, 3], [3, 4], [4, 5]]
 * target = 10
 *
 * Output: [[2, 4], [3, 2]]
 *
 * 2019-10-19
 *
 *
 *  time complexity analyze:
 *
 *  the sorting part complexity is O(MlogM + NlogN)
 *  two-pointer traversal is O(M + N),
 *
 *  the final complexity can be regarded as O(KlogK) where K is the longest input array."
 *
 * @author wxl24life
 */
public class OptimalUtilization {

    static List<List<Integer>> initialBruteForceMethod(int[][] a, int[][] b, int t) {
        if (a.length == 0 || b.length == 0) return Collections.emptyList();
        int optimal = Integer.MIN_VALUE;
        List<List<Integer>> ans = new ArrayList<>();
        int m = a.length;
        int n = b.length;
        for (int i = 0; i < m; i++) {
            int currentI = a[i][1];
            for (int j = 0; j < n; j++) {
                int sum = currentI + b[j][1];
                if (sum <= t && sum >= optimal) {
                    if (sum > optimal) {
                        optimal = sum;
                        ans.clear();
                    }
                    ans.add(Arrays.asList(i, j));
                }
            }
        }
        return ans;
    }

    static List<int[]> findOptimal(List<int[]> a, List<int[]> b, int t) {

        // the result list
        List<int[]> ans = new ArrayList<>();
        if (a.size() == 0 || b.size() == 0) return ans;

        // sort a and b in ascending order
        Collections.sort(a, (i, j) -> (i[1] - j[1]));
        Collections.sort(b, (i, j) -> (i[1] - j[1]));

        // the max among all values that are smaller or equal to target
        int maxValue = Integer.MIN_VALUE;

        // two pointers: the 'i' point to a from left to right, the 'j' point to b from right to left
        int i = 0, j = b.size() - 1;
        while (i < a.size() && j >= 0) {
            // current sum
            int sum = a.get(i)[1] + b.get(j)[1];
            if (sum > t) {
                j--;
            } else {
                // only consider sum value that is smaller than target and greater than maxValue
                if (sum >= maxValue) {
                    if (sum > maxValue) {
                        // found a greater sum, update max and clear previous result list
                        maxValue = sum;
                        ans.clear();
                    }
                    // check for duplicates which should also be added to the result list
                    for (int k = j; k >= 0 && b.get(k)[1] == b.get(j)[1]; k--) {
                        ans.add(new int[]{a.get(i)[0], b.get(k)[0]});
                    }
                }
                i++;
            }
        }
        return ans;
    }

    static void print(List<int[]> res) {
        for (int[] i : res) {
            System.out.print(i[0] + ", " + i[1] + "#");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        List<int[]> a = Arrays.asList(new int[]{1,3}, new int[]{2,5}, new int[]{3,7}, new int[]{4,10});
        List<int[]> b = Arrays.asList(new int[]{1,2}, new int[]{2,3}, new int[]{3,4}, new int[]{4,5});

        List<int[]> res = findOptimal(a, b, 10);
        print(res);

        res = findOptimal(a, b, 11);
        print(res);

        res = findOptimal(a, b, 16);
        print(res);

        res = findOptimal(a, b, 5);
        print(res);

        res = findOptimal(a, b, 7);
        print(res);


        System.out.println("=========");
        a = Arrays.asList(new int[]{1,5}, new int[]{2,5}, new int[]{3,5}, new int[]{4,5});
        b = Arrays.asList(new int[]{1,5}, new int[]{2,5}, new int[]{3,5}, new int[]{4,5});

        res = findOptimal(a, b, 10);
        print(res);
    }
}
