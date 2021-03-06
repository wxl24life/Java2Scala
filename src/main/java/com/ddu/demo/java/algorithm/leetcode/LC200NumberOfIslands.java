package com.ddu.demo.java.algorithm.leetcode;

import java.util.Deque;
import java.util.LinkedList;
/**
 * 2019-12-17
 *
 * @author wxl24life
 */
public class LC200NumberOfIslands {

    static int[][] directions = new int[][] {{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
    // 8:36
    public static int numIslands(int[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        // rows & columns
        int R = grid.length;
        int C = grid[0].length;

        int ans = 0;
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                if (grid[i][j] == 1) {
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
        if (r0 < R && r0 >= 0 && c0 < C && c0 >= 0 && grid[r0][c0] == 1) {
            // mark as visited
            grid[r0][c0] = 0;
            for (int[] dir : directions) {
                int r = r0 + dir[0];
                int c = c0 + dir[1];
                // dfs search
                helper(grid, R, C, r, c);
            }
        }
    }

    public static int numIslandsBfs(int[][] grid) {
        // edge cases
        if (grid == null || grid.length == 0 || grid[0].length == 0) return 0;

        // rows & columns
        int R = grid.length;
        int C = grid[0].length;

        Deque<int[]> queue = new LinkedList<>();
        int ans = 0;
        for (int i = 0; i < R; ++i) {
            for (int j = 0; j < C; ++j) {
                if (grid[i][j] == 1) {
                    queue.addLast(new int[] {i, j});
                    // mark as visited
                    grid[i][j] = 0;
                    bfsHelper(grid, R, C, queue);
                    ans++;
                }
            }
        }

        return ans;
    }

    // 11:53
    private static void bfsHelper(int[][] grid, int R, int C, Deque<int[]> queue) {

        while (!queue.isEmpty()) {
            int size = queue.size();
            while (size-- > 0) {
                int[] pos = queue.removeFirst();
                for (int[] dir : directions) {
                    int r = pos[0] + dir[0];
                    int c = pos[1] + dir[1];
                    if (r < R && r >= 0 && c < C && c >= 0 && grid[r][c] == 1) {
                        // found a valid target, add to queue
                        queue.addLast(new int[] {r, c});
                        // mark as visited
                        grid[r][c] = 0;
                    }
                }
            }
        }
        // queue is empty here
    }


    public static void main(String[] args) {
        int[][] grid = new int[][] {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };

        System.out.println(numIslands(grid)); // 2

        grid = new int[][] {
                {1,1,0},
                {1,1,0},
                {0,0,1}
        };
        System.out.println(numIslandsBfs(grid)); // 2
    }
}
