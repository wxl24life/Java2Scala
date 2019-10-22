package com.ddu.demo.java.algorithm;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
/**
 * @author wxl24life
 */
public class MinCostToConnectRopes {

    // use a min-heap, everytime we chose two shortest ropes, connect them and add the new rope length to the heap
    int minCost(int[] a) {
        // there is not enough ropes to connect
        if (a.length <= 1) return 0;
        PriorityQueue<Integer> pq = new PriorityQueue<>();
        // put all a's elements to pq
        for (int i : a) {
            pq.add(i);
        }
        // the total cost
        int total = 0;
        while (pq.size() > 1) {
            int first = pq.poll();
            int second = pq.poll();
            // connect the two shortest ropes
            int cost = first + second;
            // add to the total cost
            total += cost;
            // add new rope's length (equals to the connecting cost) to heap
            pq.add(cost);
        }
        return total;
    }

    int withoutPriorityQueue(int numOfSubFiles, int[] files) {
        if (files == null || files.length == 0 || numOfSubFiles == 0) return 0;
        Arrays.sort(files);
        List<Integer> sortedList = new LinkedList<>();
        for (int file : files) {
            sortedList.add(file);
        }
        int res = 0;
        while (sortedList.size() > 1) {
            int first = sortedList.remove(0);
            int second = sortedList.remove(0);
            int cost = first + second;
            res += cost;
            // add cost to list
            addNewCostWithBinarySearch(sortedList, cost);
        }
        return res;
    }

    // use binary seartch to find the insertion index for n to insure ordering property of the list
    void addNewCostWithBinarySearch(List<Integer> list, int n) {
        if (list.size() == 0) {
            list.add(n);
            return;
        }
        int left = 0, right = list.size() - 1;
        int insertIndex = -1;
        while (left < right) {
            int mid = left + (right - left) / 2;
            int midValue = list.get(mid);
            if (midValue == n) {
                insertIndex = mid;
                break;
            } else if (midValue < n) {
                left = mid + 1;
            } else {
                right = mid;
            }
        }
        // left == right
        if (insertIndex == -1) {
            // have not been set to a new value while binary search
            insertIndex = list.get(left) >= n ? left : left + 1;
        }
        list.add(insertIndex, n);
    }

    public static void main(String[] args) {
        int[] ropes = new int[] {1, 2, 5, 10, 35, 89};
        MinCostToConnectRopes app = new MinCostToConnectRopes();
        System.out.println(app.minCost(ropes));
        System.out.println(app.withoutPriorityQueue(6, ropes));
    }
}
