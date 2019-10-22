package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author wxl24life
 */
public class MaxFlightMovieCombination {
    int[] maxMovieDuration(int[] nums, int d) {
        int[] ans = new int[2];
        int target = d - 30;
        if (target <= 0) return ans;

        // use a dynamic array list
        List<Pair> sorted = new ArrayList<>();
        for (int i = 0; i < nums.length; i++) {
            sorted.add(new Pair(i, nums[i]));
        }
        Collections.sort(sorted, (i, j) -> (i.val - j.val));

        int closestSum = 0;

        // two pointers
        int left = 0, right = nums.length - 1;
        while (left < right) {
            Pair leftPair = sorted.get(left);
            Pair rightPair = sorted.get(right);
            int sum = leftPair.val + rightPair.val;
            if (sum > target) {
                right--;
            } else {
                // found duplicates
                if (sum == closestSum) {
                    int currentLongestMovie = nums[ans[1]];
                    if (rightPair.val > currentLongestMovie) {
                        // found longer movie
                        ans[0] = leftPair.index;
                        ans[1] = rightPair.index;
                    }
                } else if (sum > closestSum) {
                    closestSum = sum;
                    ans[0] = leftPair.index;
                    ans[1] = rightPair.index;
                }
                left++;
            }
        }
        return ans;
    }

    static void print(int[] res) {
        for (int i : res) {
            System.out.println(i);
        }
    }

    // others' solution
    public static int [] test(int [] nums, int d) {
        int [] result = new int [2];
        int max = Integer.MIN_VALUE;
        int start = 0;
        int end = nums.length-1;
        Arrays.sort(nums);
        int i = -1;
        int j = -1;
        ArrayList<Integer> a = new ArrayList<Integer>();
        while(start <= end) {
            if ((nums[start] + nums[end]) == d - 30) {
                if (max < nums[start]) max = nums[start];
                if (max < nums[end]) max = nums[end];
                if (max == nums[start] || max == nums[end]) {
                    i = nums[start];
                    j = nums[end];
                }
                start++;
                end--;
            } else if (max < (nums[start] + nums[end])) {
                start++;
            } else {
                end--;
            }
            result[0] = i;
            result[1] = j;
        }
        return result;
    }

    public static void main(String[] args) {
        int[] a = new int[] {120,85,75,60,90,150,125};
        MaxFlightMovieCombination app = new MaxFlightMovieCombination();
        print(app.maxMovieDuration(a, 250));

        a = new int[] {1, 10, 25, 35, 60};
        print(app.maxMovieDuration(a, 90));


        a = new int[] {20, 50, 40, 25, 30, 10};
        print(app.maxMovieDuration(a, 90));


        //...
        System.out.println("----------");
        print(test(a, 90));
    }

    class Pair {
        int index;
        int val;
        Pair(int index ,int val) {
            this.index = index;
            this.val = val;
        }
    }
}
