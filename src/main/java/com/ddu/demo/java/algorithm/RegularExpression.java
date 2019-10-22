package com.ddu.demo.java.algorithm;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 星号*代表 0 个或者多个前面的字符，问号?代表 0 个或者 1 个前面的字符
 * @author wxl24life
 */
public class RegularExpression {
    static boolean match(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        if (!s.isEmpty() && s.charAt(0) == p.charAt(0)) {
            if (p.length() > 1 && (p.charAt(1) == '*' || p.charAt(1) == '?')) {
                return match(s, p.substring(2)) || match(s.substring(1), p);
            } else {
                return match(s.substring(1), p.substring(1));
            }
//            return (p.length() > 1 && match(s, p.substring(2))) || match(s.substring(1), p)
//                    || match(s.substring(1), p.substring(1));
        } else {
            if (p.length() < 2 || (p.charAt(1) != '*' && p.charAt(1) != '?')) {
                return false;
            } else {
                return p.length() > 1 && match(s, p.substring(2));
            }
        }
    }

    /**
     * 星号匹配 0 或者多个任意字符
     * 问号匹配 0 或者 1 个任意字符
     * @param s
     * @param p
     * @return
     */
    static boolean match2(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        if (p.charAt(0) == '?') {
            return match2(s, p.substring(1)) || (s.length() > 1 && match2(s.substring(1), p.substring(1)));
        }
        if (p.charAt(0) == '*') {
            return (s.length() > 0 && match2(s.substring(1), p)) || match2(s, p.substring(1));
        }
        if (s.length() > 0) {
            if (s.charAt(0) != p.charAt(0)) {
                return false;
            } else {
                return match2(s.substring(1), p.substring(1));
            }
        } else {
            return false;
        }
    }


    /**
     * 星号匹配 0 或者多个任意字符
     * 问号匹配 1 个任意字符
     * @param s
     * @param p
     * @return
     */
    static boolean isMatch(String s, String p) {
        if (p.isEmpty()) return s.isEmpty();
        if (p.length() == 1 && p.charAt(0) == '*') return true;
        if (p.length() == 2 && p.charAt(0) == '*') {
            if (p.charAt(1) != '*' && p.charAt(1) != '?') {
                if ((s.length() > 0 && s.charAt(s.length() - 1) != p.charAt(1)) || s.length() == 0) {
                    return false;
                }
            }
        }
        if (p.charAt(0) == '?') {
            if (s.length() == 0) {
                return false;
            }
            return isMatch(s.substring(1), p.substring(1));
        }
        if (p.charAt(0) == '*') {
            return (s.length() > 0 && isMatch(s.substring(1), p)) || isMatch(s, p.substring(1));
        }
        if (s.length() > 0) {
            if (s.charAt(0) != p.charAt(0)) {
                return false;
            } else {
                return isMatch(s.substring(1), p.substring(1));
            }
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        System.out.println(match("", "")); // true
        System.out.println(match("", "a*b")); // false
        System.out.println(match("", "a*")); // true
        System.out.println(match("", "a")); // false
        System.out.println(match("", "a?")); // true
        System.out.println(match("abc", "a?b*abc*c")); // true
        System.out.println(match("abc", "b*a*abc*c")); // true
        System.out.println(match("abc", "b*a?bc*c")); // true
        System.out.println(match("abc", "cb*a?bc*c")); // false
        System.out.println(match("abc", "abc")); // true
        System.out.println(match("abc", "adbc")); // false
        System.out.println(match("cab", "c*a*b")); // true


        System.out.println("======");
        System.out.println(match2("", "")); // true
        System.out.println(match2("", "abc")); // false
        System.out.println(match2("abc", "*c")); // true
        System.out.println(match2("abc", "a*c")); // true
        System.out.println(match2("abbc", "a*c")); // true
        System.out.println(match2("abbc", "a*")); // true
        System.out.println(match2("abbc", "a*b*")); // true
        System.out.println(match2("ab", "a*b?")); // true
        System.out.println(match2("abb", "a*b?")); // true
        System.out.println(match2("abbb", "a*b?")); // true
        System.out.println(match2("ab", "?ab")); // true
        System.out.println(match2("cab", "?cab")); // true
        System.out.println(match2("ccab", "?ab")); // false
        System.out.println("======");

        System.out.println(isMatch("", "")); // true
        System.out.println(isMatch("aa", "a")); // false
        System.out.println(isMatch("aa", "*")); // true
        System.out.println(isMatch("cb", "?a")); // false
        System.out.println(isMatch("adceb", "*a*b")); // true
        System.out.println(isMatch("addcb", "a*c?b")); // false
    }
}
