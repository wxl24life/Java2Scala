package com.ddu.demo.java.algorithm;

/**
 * @author wxl24life
 */
public class BagWithPrice {

    static int maxW = Integer.MIN_VALUE;
    static int maxP = Integer.MIN_VALUE;

    static void f(int[] a, int[] p, int n, int w, int i, int cw, int cp) {
        if (i == n || cw > w) return;
        if (cw <= w && cw > maxW) {
            maxW = cw;
        }
        if (cp > maxP) {
            maxP = cp;
        }
        f(a, p, n, w, i + 1, cw, cp);
        f(a, p, n, w, i + 1, cw + a[i], cp + p[i]);
    }

    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 4, 9, 15};
        int[] p = new int[] {2, 5, 3, 11, 1};
        print(a, p, 7);  // 10
        print(a, p, 8);  // 10
        print(a, p, 9);  // 11
        print(a, p, 10);  // 13
        print(a, p, 11);  // 16
        print(a, p, 12);  // 18
        print(a, p, 13);  // 18
        print(a, p, 14);  // 18
    }

    static void print(int[] a, int[] p, int w) {
        int n = a.length;
        maxP = 0;
        maxW = 0;
        f(a, p, n, w, 0, 0, 0);
        System.out.println(maxP);
    }
}
