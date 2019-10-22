package com.ddu.demo.java.algorithm;

/**
 * @author wxl24life
 */
public class LC69 {
    public int mySqrt(int x) {
        return mySqrt(x, 0, x);
    }

    private int mySqrt(int x, int l, int h) {
        if (h - l <= 1) {
            if (x < l * l) {
                return l - 1;
            } else if (x >= h * h) {
                return h;
            } else { //  h*h > x >= l*l
                return l;
            }
        }
        int mid = l + ((h - l) >> 1);
        int r = mid * mid;
        if (r == x) return mid;
        if (r > x) {
            return mySqrt(x, l, mid - 1);
        } else {
            return mySqrt(x, mid, h);
        }

    }

    public static void main(String[] args) {
        System.out.println(new LC69().mySqrt(2147395599));
    }
}
