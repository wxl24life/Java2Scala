package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wxl24life
 */
public class Bag03DP {
    static int[] wt = {1, 3, 4, 5};
    static int[] val = {1, 4, 5, 7};
    static int w = 7;

    public static void main(String[] args) {
        // sort wt? what about val?
        int n = wt.length;
        int[][] t = new int[n][w + 1];
        for (int i = 0; i < w + 1; i++) {
            t[0][i] = wt[0] > i ? 0 : val[0];
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < w + 1; j++) {
                if (wt[i] > j) {
                    t[i][j] = t[i - 1][j];
                } else {
                    int include = val[i] + t[i - 1][j - wt[i]];
                    int exclude = t[i - 1][j];
                    t[i][j] = Math.max(include, exclude);
                }
            }
        }

        System.out.println(t[n - 1][w]);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < w + 1; j++) {
                System.out.print(t[i][j] + " ");
            }
            System.out.print("\n");
        }

        List<Integer> list = new ArrayList<>();
        path(t, n - 1, w, list);
        for (int i = list.size() - 1; i >= 0; i--) {
            System.out.print(list.get(i) + " ");
        }
    }

    static void path(int[][] t, int i, int j, List<Integer> list) {
        if (i > 0) {
            if (t[i][j] > t[i - 1][j]) {
                list.add(wt[i]);
                int prev = j - wt[i];
                path(t, i - 1, prev, list);
            } else {
                path(t, i - 1, j, list);
            }
        } else {
            // i == 0
            if (t[0][j] == val[0]) {
                list.add(wt[0]);
            }
        }
    }
}


