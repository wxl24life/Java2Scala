package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * 2019-10-29
 *
 * https://leetcode.com/discuss/interview-question/267985/
 *
 * Google | Onsite | Construct a word using dice
 *
 * Given a word of length n and n six-sided dice with a character in each side. Find out if this word can be constructed by the set of given dice.

 Example 1:

 Input:
 word = "hello"
 dice = [[a, l, c, d, e, f], [a, b, c, d, e, f], [a, b, c, h, e, f], [a, b, c, d, o, f], [a, b, c, l, e, f]]
 Output: true
 Explanation: dice[2][3] + dice[1][4] + dice[0][1] + dice[4][3] + dice[3][4]
 Example 2:

 Input:
 word = "hello"
 dice = [[a, b, c, d, e, f], [a, b, c, d, e, f], [a, b, c, d, e, f], [a, b, c, d, e, f], [a, b, c, d, e, f]]
 Output: false
 Example 3:

 Input:
 word = "aaaa"
 dice = [[a, a, a, a, a, a], [b, b, b, b, b, b], [a, b, c, d, e, f], [a, b, c, d, e, f]]
 Output: false
 *
 * @author wxl24life
 */
public class ConstructAWordUsingDices {

    static boolean matchDices(String target, char[][] dices) {
        int len = target.length();
        Map<Character, List<int[]>> map = new HashMap<>();
        // build the map using dices
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 6; j++) {
                List<int[]> curList = map.getOrDefault(dices[i][j], new ArrayList<>());
                curList.add(new int[]{i,j});
                map.put(dices[i][j], curList);
            }
        }

        return backtracking(target, new boolean[len], map);
    }

    static boolean backtracking(String target, boolean[] visited, Map<Character, List<int[]>> map) {
        if (target.length() == 0) {
            return true;
        }
        List<int[]> list = map.get(target.charAt(0));
        if (list == null) {
            return false;
        }
        for (int[] pair : list) {
            int diceIndex = pair[0];
            if (visited[diceIndex]) {
                continue;
            }
            visited[diceIndex] = true;
            boolean ret = backtracking(target.substring(1), visited, map);
            visited[diceIndex] = false;
            if (ret) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        String word = "hello";
        char[][] dices = new char[][] {
                {'a', 'l', 'c', 'd', 'e', 'f'},
                {'a', 'b', 'c', 'd', 'e', 'f'},
                {'a', 'b', 'c', 'h', 'e', 'f'},
                {'a', 'b', 'c', 'd', 'o', 'f'},
                {'a', 'b', 'c', 'l', 'e', 'f'}};

        System.out.println(matchDices(word, dices));

        dices = new char[][] {
                { 'a', 'b', 'c', 'd', 'e', 'f' },
                { 'a', 'b', 'c', 'd', 'e', 'f' },
                { 'a', 'b', 'c', 'd', 'e', 'f' },
                { 'a', 'b', 'c', 'd', 'e', 'f' },
                { 'a', 'b', 'c', 'd', 'e', 'f' }};
        System.out.println(matchDices(word, dices));

        dices = new char[][] {
                { 'a', 'a', 'a', 'a', 'a', 'a' },
                { 'b', 'b', 'b', 'b', 'b', 'b' },
                { 'a', 'b', 'c', 'd', 'e', 'f' },
                { 'a', 'b', 'c', 'd', 'e', 'f' }};

        System.out.println(matchDices("aaaa", dices));
        System.out.println(matchDices("aaab", dices));
        System.out.println(matchDices("abef", dices));
    }
}
