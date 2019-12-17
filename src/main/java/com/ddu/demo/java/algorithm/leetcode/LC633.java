package com.ddu.demo.java.algorithm.leetcode;

/**
 * @author wxl24life
 */
public class LC633 {
    public static void main(String[] args) {
        System.out.println(judgeSquareSum(5));
        System.out.println(judgeSquareSum(14));
        System.out.println(judgeSquareSum(3));
        System.out.println(judgeSquareSum(1));
        System.out.println(judgeSquareSum(2));
    }

    private static boolean judgeSquareSum(int n) {
        if (n == 1 || n == 2 || n == 4) return true;
        for (int i = 0; i <= n/2; i++) {
            for (int j = 0; j <= n/2; j++) {
                long a = i * i;
                long b = j * j;
                if ((a + b) == n) return true;
            }
        }
        return false;
    }
}
