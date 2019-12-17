package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author wxl24life
 */
public class LC763 {
    public static List<Integer> partitionLabels(String s) {
        int[] tmp = new int[10]; // 创建大小为 10 的整型数组，初始值为 0
        Arrays.fill(tmp, -1); // 将所有的数组值重置为 -1
        tmp[0] = 1;
        int[][] intervalArray = new int[26][2];
        Arrays.fill(intervalArray, new int[]{-1, -1});
        for (int i = 0; i < s.length(); i++) {
            int letterIdx = s.charAt(i) - 'a';
            if (intervalArray[letterIdx][0] == -1) {
                intervalArray[letterIdx][0] = i;
            }
            intervalArray[letterIdx][1] = i;
        }

        intervalArray[0][0] = -2;

        for (int i = 0; i < 26; i++) {
            Arrays.fill(intervalArray[i], -1);
        }

        for (int[] row : intervalArray) {
            Arrays.fill(row, -1);
        }

        Arrays.sort(intervalArray, (a, b) -> (a[0] - b[0]));
        List<int[]> list = new ArrayList<>();
        for (int i = 0; i < 26; i++) {
            if (intervalArray[i][0] != -1) {
                list.add(intervalArray[i]);
            }
        }

        // merge intervals
        List<Integer> ans = new ArrayList<>();
        int[] prev = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            int[] current = list.get(i);
            if (prev[1] < current[0]) {
                ans.add(prev[1] - prev[0] + 1);
                prev = current;
            } else {
                // merge
                prev[0] = Math.min(prev[0], current[0]);
                prev[1] = Math.max(prev[1], current[1]);
            }
        }
        ans.add(prev[1] - prev[0] + 1);
        return ans;
    }

    public static void main(String[] args) {
        List<Integer> list = partitionLabels("ababcbacadefegdehijhklij");
        System.out.println(Arrays.toString(list.toArray()));
    }
}
