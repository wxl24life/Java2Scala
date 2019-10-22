package com.ddu.demo.java.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author wxl24life
 */
public class LC438 {
    public List<Integer> findAnagrams(String s, String p) {
        if (s == null) return Collections.emptyList();
        int[] pArr = new int[26];
        int[] subAarr = new int[26];
        for (int i = 0; i < p.length(); i++) {
            pArr[p.charAt(i) - 'a']++;
        }

        List<Integer> ans = new ArrayList<>();
        int i = 0;
        while (i <= s.length() - p.length()) {
            int j = i;
            boolean flag = false;
            while (j < i + p.length()) {
                if (pArr[s.charAt(j) - 'a'] == 0) {
                    i = j + 1;
                    flag = true;
                    break;
                }
                subAarr[s.charAt(j) - 'a']++;
                j++;
            }
            if (!flag) {
                if (check(subAarr, pArr, p)) {
                    ans.add(i);
                }
                i++;
            }
            clearArray(subAarr, p);
        }
        return ans;
    }

    boolean check(int[] sa, int[] pa, String p) {
        boolean ans = true;
        for (int i = 0; i < p.length(); i++) {
            int index = p.charAt(i) - 'a';
            if (sa[index] != pa[index]) {
                ans = false;
                break;
            }
        }
        return ans;
    }

    void clearArray(int[] sa, String p) {
        // recover sa
        for (int i = 0; i < p.length(); i++) {
            sa[p.charAt(i) - 'a'] = 0;
        }
    }

    public static void main(String[] args) {
        String s = "eklpyqrbgjdwtcaxzsnifvhmoueklpyqrbgjdwtcaxzsnifvhmoueklp";
        String p = "yqrbgjdwtcaxzsnifvhmou";
        List<Integer> list = new LC438().findAnagrams(s, p);
        for (int i : list) {
            System.out.println(i);
        }
    }
}
