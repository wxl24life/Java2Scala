package com.ddu.demo.java.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 76. Minimum Window Substring
 * https://leetcode.com/problems/minimum-window-substring/
 * @author wxl24life
 */
public class LC76 {
    public String minWindow(String s, String t) {
        Map<Character, Integer> window = new HashMap<>();
        Map<Character, Integer> target = new HashMap<>();

        // build target
        for (char ch : t.toCharArray()) {
            target.put(ch, target.getOrDefault(ch, 0) + 1);
        }
        int matchCounter = target.size();

        String res = s + "a";
        int left = 0, right = 0;
        while (right < s.length()) {
            char rightCh = s.charAt(right);
            if (target.containsKey(rightCh)) {
                window.put(rightCh, window.getOrDefault(rightCh, 0) + 1);
                // 下面的相等判断不能使用 ==，因为是两个 Integer 对象，而非 int
                // 两种处理方案：1. 使用 equals 2. 使用 Integer.intValue() 转换为 int
                // if (window.get(rightCh) == target.get(rightCh)) {
                if (window.get(rightCh).intValue() == target.get(rightCh).intValue()) {
                    matchCounter--;
                }
            }

            right++;
            // if match
            while (matchCounter == 0) {
                int currMatchLength = right - left;
                if (currMatchLength < res.length()) {
                    res = s.substring(left, right);
                }
                char leftCh = s.charAt(left);
                if (target.containsKey(leftCh)) {
                    window.put(leftCh, window.get(leftCh) - 1);
                    if (window.get(leftCh) < target.get(leftCh)) {
                        matchCounter++;
                    }
                }
                left++;
            }
        }

        return res.length() > s.length() ? "" : res;
    }

    // Paste it here to learn some comment technique
    public String minWindowFromLCSolution(String s, String t) {

        if (s.length() == 0 || t.length() == 0) {
            return "";
        }

        // Dictionary which keeps a count of all the unique characters in t.
        Map<Character, Integer> dictT = new HashMap<Character, Integer>();
        for (int i = 0; i < t.length(); i++) {
            int count = dictT.getOrDefault(t.charAt(i), 0);
            dictT.put(t.charAt(i), count + 1);
        }

        // Number of unique characters in t, which need to be present in the desired window.
        int required = dictT.size();

        // Left and Right pointer
        int l = 0, r = 0;

        // formed is used to keep track of how many unique characters in t
        // are present in the current window in its desired frequency.
        // e.g. if t is "AABC" then the window must have two A's, one B and one C.
        // Thus formed would be = 3 when all these conditions are met.
        int formed = 0;

        // Dictionary which keeps a count of all the unique characters in the current window.
        Map<Character, Integer> windowCounts = new HashMap<Character, Integer>();

        // ans list of the form (window length, left, right)
        int[] ans = {-1, 0, 0};

        while (r < s.length()) {
            // Add one character from the right to the window
            char c = s.charAt(r);
            int count = windowCounts.getOrDefault(c, 0);
            windowCounts.put(c, count + 1);

            // If the frequency of the current character added equals to the
            // desired count in t then increment the formed count by 1.
            if (dictT.containsKey(c) && windowCounts.get(c).intValue() == dictT.get(c).intValue()) {
                formed++;
            }

            // Try and contract the window till the point where it ceases to be 'desirable'.
            while (l <= r && formed == required) {
                c = s.charAt(l);
                // Save the smallest window until now.
                if (ans[0] == -1 || r - l + 1 < ans[0]) {
                    ans[0] = r - l + 1;
                    ans[1] = l;
                    ans[2] = r;
                }

                // The character at the position pointed by the
                // `Left` pointer is no longer a part of the window.
                windowCounts.put(c, windowCounts.get(c) - 1);
                if (dictT.containsKey(c) && windowCounts.get(c) < dictT.get(c).intValue()) {
                    formed--;
                }

                // Move the left pointer ahead, this would help to look for a new window.
                l++;
            }

            // Keep expanding the window once we are done contracting.
            r++;
        }

        return ans[0] == -1 ? "" : s.substring(ans[1], ans[2] + 1);
    }
}