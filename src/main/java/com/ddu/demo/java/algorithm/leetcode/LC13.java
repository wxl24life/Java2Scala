package com.ddu.demo.java.algorithm.leetcode;

/**
 * @author wxl24life
 */
public class LC13 {
    int[] a = new int[]{1,4,5,9,10,40,50,90,100,400,500,900,1000};
    public int romanToInt(String str) {
        int[] a = new int[26];
        a['I' - 'A'] = 1;
        a['V' - 'A'] = 5;
        a['X' - 'A'] = 10;
        a['L' - 'A'] = 50;
        a['C' - 'A'] = 100;
        a['D' - 'A'] = 500;
        a['M' - 'A'] = 1000;
        char prev = 'A';
        int sum = 0;
        for(char s : str.toCharArray()) {
            if(a[s - 'A'] > a[prev - 'A']) {
                sum = sum - 2 * a[prev - 'A'];
            }
            sum = sum + a[s - 'A'];
            prev = s;
        }
        return sum;
    }

    public static void main(String[] args) {
        int iv = new LC13().romanToInt("IV");
        System.out.println(iv);
    }
}
