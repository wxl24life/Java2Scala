package com.ddu.demo.java.algorithm.leetcode;

import java.util.*;

/**
 * 2019-12-25 bfs
 *
 * @author wxl24life
 */
public class LC127WordLadder {
    public static int ladderLength(String begin, String end, List<String> list) {
        // check valid
        if (!list.contains(end)) return 0;

        // preprocessing
        Map<String, Set<String>> wordsMap = new HashMap<>();
        for (String word : list) {
            for (int i = 0; i < word.length(); i++) {
                // cat => *at, c*t, ca*
                // can't use string.replace(char,char) which will replace all occurrence of the first character
                String template = word.substring(0, i) + '*' + word.substring(i + 1);
                // String tmp = word.replace(word.charAt(i), '*');
                Set<String> matches = wordsMap.getOrDefault(template, new HashSet<String>());
                matches.add(word);
                wordsMap.put(template, matches);
            }
        }

        return findPath(begin, end, wordsMap);
    }

    // bfs
    static int findPath(String begin, String end, Map<String, Set<String>> wordsMap) {
        Set<String> visited = new HashSet<>();
        Deque<String> queue = new LinkedList<>();
        queue.addLast(begin);
        visited.add(begin);

        int steps = 1;
        while(!queue.isEmpty()) {
            steps++;
            int size = queue.size();
            while (size-- > 0) {
                String word = queue.removeFirst();

                // find all words that are one letter different with each other
                Set<String> adjacents = new HashSet<>();
                for (int i = 0; i < word.length(); i++) {
                    // Set<String> tmp = wordsMap.get(word.replace(word.charAt(i), '*'));
                    String templateWord = word.substring(0, i) + '*' + word.substring(i + 1);
                    Set<String> tmpSet = wordsMap.get(templateWord);
                    if (tmpSet != null) {
                        adjacents.addAll(tmpSet);
                    }
                }

                for (String key : adjacents) {
                    if (visited.contains(key)) continue;

                    // found the target
                    if (end.equals(key)) return steps;

                    // else
                    queue.addLast(key);
                    visited.add(key);
                }
            }
        }

        return 0; // didn't found a path
    }

    public static void main(String[] args) {
        String beginWord = "lost";
        String endWord = "miss";
        String[] words = new String[] {"most","mist","miss","lost","fist","fish"};

        System.out.println(ladderLength(beginWord, endWord, Arrays.asList(words)));
    }
}
