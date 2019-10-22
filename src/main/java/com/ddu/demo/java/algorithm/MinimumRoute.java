package com.ddu.demo.java.algorithm;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Treasure Island
 *
 * https://leetcode.com/discuss/interview-question/347457
 *
 * Input:
 * [['O', 'O', 'O', 'O'],
 * ['D', 'O', 'D', 'O'],
 * ['O', 'O', 'O', 'O'],
 * ['X', 'D', 'D', 'O']]
 *
 * Output: 5
 * Explanation: Route is (0, 0), (0, 1), (1, 1), (2, 1), (2, 0), (3, 0) The minimum route takes 5 steps.
 *
 * 2019-10-19
 * @author wxl24life
 */
public class MinimumRoute {

    int minimumRoute(char[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0) return -1;

        // mark all nodes that has been visited
        boolean[][] visited = new boolean[a.length][a[0].length];

        // for each queue element, it has a form of {row, col, depth}
        Deque<int[]> queue = new LinkedList<>();

        // add starting point to queue, with row = 0, col = 0, depth = 0
        queue.add(new int[] {0, 0, 0});
        visited[0][0] = true;

        while (!queue.isEmpty()) {
            int[] current = queue.poll();
            int row = current[0];
            int col = current[1];
            int depth = current[2];

            // found the target
            if (a[row][col] == 'X') {
                return depth;
            }
            // mark as visited
            visited[row][col] = true;
            addNextToQueue(a, row - 1, col, depth + 1, queue, visited);
            addNextToQueue(a, row + 1, col, depth + 1, queue, visited);
            addNextToQueue(a, row, col - 1, depth + 1, queue, visited);
            addNextToQueue(a, row, col + 1, depth + 1, queue, visited);
        }
        return -1;
    }

    /**
     * Three situation will result false
     * 1. It has been visited before 2. it's not in the zone 3. it is a 'D' block
     */
    void addNextToQueue(char[][] a, int i, int j, int depth, Deque<int[]> queue, boolean[][] visited) {
        if ((i >= a.length || i < 0) || (j >= a[0].length || j < 0)) return;
        if (visited[i][j] || a[i][j] == 'D') return;
        // found a valid situation
        queue.add(new int[] {i, j, depth});
    }


    /**
     * more standard bfs solution
     */
    int bfs(char[][] a) {
        if (a == null || a.length == 0 || a[0].length == 0) return -1;

        // for each queue element, it has a form of {row, col, depth}
        Deque<int[]> queue = new LinkedList<>();

        // add starting point to queue, with row = 0, col = 0, depth = 0
        queue.add(new int[] {0, 0, 0});
        // mark as visited
        a[0][0] = 'D';
        int steps = 0;
        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                int[] current = queue.poll();
                int row = current[0];
                int col = current[1];
                int depth = current[2];

                // found the target
                if (a[row][col] == 'X') {
                    return steps;
                }
                // mark as visited
                a[row][col] = 'D';
                addNextToQueueWithoutVisited(a, row - 1, col, depth + 1, queue);
                addNextToQueueWithoutVisited(a, row + 1, col, depth + 1, queue);
                addNextToQueueWithoutVisited(a, row, col - 1, depth + 1, queue);
                addNextToQueueWithoutVisited(a, row, col + 1, depth + 1, queue);
            }
            steps++;
        }
        return -1;
    }

    /**
     * Three situation will result false
     * 1. It has been visited before 2. it's not in the zone 3. it is a 'D' block
     */
    void addNextToQueueWithoutVisited(char[][] a, int i, int j, int depth, Deque<int[]> queue) {
        if ((i >= a.length || i < 0) || (j >= a[0].length || j < 0)) return;
        if (a[i][j] == 'D') return;
        // found a valid situation
        queue.add(new int[] {i, j, depth});
    }

    public static void main(String[] args) {
        char[][] a = new char[][] {{'O', 'O', 'O', 'O'},
                {'D', 'O', 'D', 'O'},
                {'O', 'O', 'O', 'O'},
                {'X', 'D', 'D', 'O'}};
        MinimumRoute mr = new MinimumRoute();
        System.out.println(mr.minimumRoute(a));
        System.out.println(mr.bfs(a));
    }
}
