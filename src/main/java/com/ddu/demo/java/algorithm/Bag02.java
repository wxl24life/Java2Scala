package com.ddu.demo.java.algorithm;

/**
 * 2019-09-30 my version
 * @author wxl24life
 */
public class Bag02 {
    static int max = Integer.MIN_VALUE;
    static int[] a = new int[] {1, 2, 4, 9, 15};
    static int n = 5, w;

    static void f(int[] a, int n, int w, int i, int curr) {
        if (i == n || curr > w) return;
        if (curr <= w && curr > max) {
            max = curr;
        }
        f(a, n, w, i + 1, curr);
        f(a, n, w, i + 1, curr + a[i]);
    }

    static int ks(int i, int curr, int w) {
        if (i == n || curr > w) return 0;
        int result = -1;
        if (curr + a[i] > w) {
            result = ks(i + 1, curr, w);
        } else {
            result = Math.max(ks(i + 1, curr, w), a[i] + ks(i + 1, curr + a[i], w));
        }
        return result;
    }

    public static void main(String[] args) {
        print(a, 7);  // 7
        print(a, 8);  // 7
        print(a, 9);  // 9
        print(a, 10);  // 10
        print(a, 11);  // 11
        print(a, 12);  // 12
        print(a, 13);  // 13
        print(a, 14);  // 14
        System.out.println(ks(0, 0, 7));
        System.out.println(ks(0, 0, 8));
        System.out.println(ks(0, 0, 9));
        System.out.println(ks(0, 0, 10));
        System.out.println(ks(0, 0, 11));
        System.out.println(ks(0, 0, 12));
    }

    static void print(int[] a, int w) {
        int n = a.length;
        f(a, n, w, 0, 0);
        System.out.println(max);
    }

}
