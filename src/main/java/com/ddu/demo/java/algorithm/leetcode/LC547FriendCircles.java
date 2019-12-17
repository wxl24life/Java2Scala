package com.ddu.demo.java.algorithm.leetcode;

/**
 * @author wxl24life
 */
public class LC547FriendCircles {

    public int findCircleNum(int[][] M) {
        if (M == null || M.length == 0) return 0;

        int N = M.length;

        int ans = 0;
        boolean[] visited = new boolean[N];
        for (int i = 0; i < N; ++i) {
            if (!visited[i]) {
                helper(M, N, i, visited);
                ans++;
            }
        }

        return ans;
    }
    
    private void helper(int[][] M, int N, int i, boolean[] visited) {

        for (int j = 0; j < N; ++j) {
            if (M[i][j] == 1 && !visited[j]) {
                visited[j] = true;
                helper(M, N, j, visited);
            }
        }
    }
}
