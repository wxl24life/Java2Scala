package com.ddu.demo.java.algorithm;

import java.util.*;

/**
 * @author wxl24life
 */
public class CostOfModules {
    // 22:09 22:29 22:38 22:46 22:50 22:54 23:02

    public static void main(String[] args) {
        String[] input = new String[]{"A,E,N,S", "S,H,N", "E,N", "H", "N"};
        List<String> list = costOfModules(input);
        for (String str : list) {
            System.out.println(str);
        }
    }

    // assert input: ["A,E,N,S", "S,H,N", "E,N", "H", "N"]
    static List<String> costOfModules(String[] input) {
        if (input == null || input.length == 0) return null;
        String[] allModules = new String[input.length];
        Map<String, Set<String>> map = revertMappings(input, allModules);
        // sort all modules
        Arrays.sort(allModules);
        Map<String, Boolean> visited = new HashMap<>();
        List<String> resultList = new ArrayList<>();
        for (String module : allModules) {
            // visited used to avoid cycle dependencies
            initVisited(visited, allModules);
            resultList.add(module + "," + dfs(module, map, visited));
            visited.clear();
        }
        return resultList;
    }

    // assert input: ["A,E,N,S", "S,H,N", "E,N", "H", "N"]
    // output: {"E":<"A">, "N":<"A","S","E">, "S":"A", "H":"S", "A":null}
    static Map<String, Set<String>> revertMappings(String[] input, String[] allModules) {
        Map<String, Set<String>> map = new HashMap<>();
        int k = 0;
        for (String s : input) {
            String[] curr = s.split(",");
            String value = curr[0];
            allModules[k++] = value;
            for (int i = 1; i < curr.length; i++) {
                String key = curr[i];
                if (!map.containsKey(key)) {
                    map.put(key, new HashSet<String>());
                }
                map.get(key).add(value);
            }
        }
        return map;
    }

    static void initVisited(Map<String, Boolean> visited, String[] allModules) {
        for (String module : allModules) {
            visited.put(module, false);
        }
    }

    static int dfs(String module, Map<String, Set<String>> map, Map<String, Boolean> visited) {
        if (visited.get(module)) return 0;
        visited.put(module, true);
        if (!map.containsKey(module)) return 1;
        Set<String> nextModuleSet = map.get(module);
        int count = 1;
        for (String nextModule : nextModuleSet) {
            count += dfs(nextModule, map, visited);
        }
        return count;
    }
}
