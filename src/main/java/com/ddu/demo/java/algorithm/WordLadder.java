package com.ddu.demo.java.algorithm;

import java.util.*;

/**
 * @author wxl24life
 */
public class WordLadder {
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {

        // All words have the same length
        int len = beginWord.length();
        // pre-processing: hit -> *it, h*t, hi*
        // key = *it, value = {hit, cit, dit}
        Map<String, List<String>> map = new HashMap<>();
        for (String word : wordList) {
            for (int i = 0; i < len; i++) {
                String gen = word.substring(0, i) + '*' + word.substring(i + 1); // i + 1 越界？
                if (!map.containsKey(gen)) {
                    map.put(gen, new ArrayList<>());
                }
                map.get(gen).add(word);
            }
        }

        // bfs
        Deque<Pair> queue = new LinkedList<>();
        queue.add(new Pair(beginWord, 1));
        Set<String> visited = new HashSet<>();
        while (!queue.isEmpty()) {
            Pair node = queue.poll();
            String currWord = node.word;
            int depth = node.depth;
            if (currWord.equals(endWord)) {
                return depth;
            }
            visited.add(currWord);

            // add the adjacent words of currWord to queue
            for (int i = 0; i < len; i++) {
                String gen = currWord.substring(0, i) + '*' + currWord.substring(i + 1);
                for (String word : map.getOrDefault(gen, Collections.emptyList())) {
                    if (visited.contains(word)) {
                        queue.add(new Pair(word, depth + 1));
                    }
                }
            }
        }

        return 0;
    }

    class Pair {
        String word;
        int depth;
        Pair(String word, int depth) {
            this.word = word;
            this.depth = depth;
        }
    }
}
