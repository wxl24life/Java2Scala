package com.ddu.demo.java.algorithm;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author wxl24life
 */
public class MaxQueue {
    Deque<Integer> entries = new LinkedList<>();
    Deque<Integer> candidates = new LinkedList<>();

    /*
    // right boundary for the descending ordered max-value deque
    int right = 0;

    void enqueueMyFirstVersion(int val) {
        entries.addLast(val);
        if (val > ordered.getFirst()) {
            ordered.set(0, val);
            right = 0;
        }
        // find the position for val using binary-search
        right = binarySearch(ordered, 0, right, val);
        ordered.set(right, val);
    }

    // not proper to do binary search on a list (or deque)
    int binarySearch(List<Integer> list, int lo, int hi, int val) {
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            if (list.get(mid) >= val) {
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }
        return lo;
    }

    */

    void enqueue(int val) {
        entries.addLast(val);
        while (!candidates.isEmpty() && val > candidates.getLast()) {
            candidates.removeLast();
        }
        candidates.addLast(val);
    }

    int dequeue() {
        if (entries.isEmpty()) {
            throw new NoSuchElementException("Called dequeue() on empty queue.");
        }
        int val = entries.removeFirst();
        if (val == candidates.getFirst()) {
            candidates.removeFirst();
        }
        return val;
    }

    int max() {
        if (entries.isEmpty()) {
            throw new NoSuchElementException("Called max() on empty queue.");
        }
        return candidates.getFirst();
    }
}
