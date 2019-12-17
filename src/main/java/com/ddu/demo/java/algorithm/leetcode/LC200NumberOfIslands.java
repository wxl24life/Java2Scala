package com.ddu.demo.java.algorithm.leetcode;

/**
 * 2019-12-17
 *
 * @author wxl24life
 */
public class LC200NumberOfIslands {

    static int[][] directions = new int[][] {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    // 8:36
    public static int numIslands(int[][] grid) {
        int R = grid.length;
        int C = grid[0].length;

        int ans = 0;
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                if (grid[i][j] == 1) {
                    // mark grid[i][j] as visited
                    grid[i][j] = 0;
                    helper(grid, R, C, i, j);
                    ans++;
                }
            }
        }

        return ans;
    }

    // dfs search,
    // mark all grid[i,j] to 0 if it it is connected with grid[r0][c0]
    private static void helper(int[][] grid, int R, int C, int r0, int c0) {
        for (int[] dir : directions) {
            int r = r0 + dir[0];
            int c = c0 + dir[1];
            if (r >= R || r < 0 || c >= C || c < 0 || grid[r][c] == 0) {
                continue;
            }
            // mark as visited
            grid[r][c] = 0;
            // dfs search
            helper(grid, R, C, r, c);
        }
    }

    public static void main(String[] args) {
        int[][] grid = new int[][] {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };

        System.out.println(numIslands(grid)); // 2
    }
}
