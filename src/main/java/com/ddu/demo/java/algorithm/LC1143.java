package com.ddu.demo.java.algorithm;

/**
 * 寻找两个字符串的最长公共子序列
 *
 * @author wxl24life
 */
public class LC1143 {

    public static int longestCommonSubsequence(String s1, String s2) {
        int m = s1.length();
        int n = s2.length();
        int[][] dp = new int[m + 1][n + 1];
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1] + 1;
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
                }
            }
        }

        printDP(dp);
        printLCS(dp, m, n, s1, s2);
        return dp[m][n];
    }

    // 打印 DP 表
    private static void printDP(int[][] dp) {
        int rows = dp.length;
        int cols = dp[0].length;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(dp[i][j] + " ");
            }
            System.out.print("\n");
        }
    }

    // 打印其中一个 LCS
    private static void printLCS(int[][] dp, int i, int j, String s1, String s2) {
        if (i == 0 || j == 0) {
            return;
        }
        if (dp[i][j] == dp[i - 1][j - 1] + 1 && s1.charAt(i - 1) == s2.charAt(j - 1)) {
            printLCS(dp, i - 1, j - 1, s1, s2);
            System.out.println(s1.charAt(i - 1));
        } else if (dp[i][j] == dp[i][j - 1]) {
            printLCS(dp, i, j - 1, s1, s2);
        } else {
            printLCS(dp, i - 1, j, s1, s2);
        }
    }

    public static void main(String[] args) {
        String s1 = "abcabdaed";
        String s2 = "wewerabacaabsba";
        longestCommonSubsequence(s1, s2);
    }
}
