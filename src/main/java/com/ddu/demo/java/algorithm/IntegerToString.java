package com.ddu.demo.java.algorithm;

import java.util.*;

/**
 * @author wxl24life
 */
public class IntegerToString {
    public static final int LOOPS = 1000000;

    public static String myVersion(int x) {
        if (x == 0) return "0";
        char[] integers = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
        List<Character> strList = new LinkedList<>();
        while (x != 0) {
            int i = x % 10;
            strList.add(integers[i]);
            x /= 10;
        }
        StringBuilder sb = new StringBuilder();
        int len = strList.size();
        int p = len - 1;
        while (p >= 0) {
            sb.append(strList.get(p--));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < LOOPS; i++) {
            myVersion(1234);
            myVersion(1234534534);
        }
        long end1 = System.currentTimeMillis();


        long start2 = System.currentTimeMillis();
        for (int i = 0; i < LOOPS; i++) {
            String.valueOf(1234);
            String.valueOf(1234534534);
        }
        long end2 = System.currentTimeMillis();

        System.out.println(end1 - start1);
        System.out.println(end2 - start2);
    }
}
