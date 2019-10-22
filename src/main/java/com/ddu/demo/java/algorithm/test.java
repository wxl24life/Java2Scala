package com.ddu.demo.java.algorithm;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wxl24life
 */
public class test {
    public static void main(String[] args) {

        PriorityQueue<Integer> pq = new PriorityQueue<>((a, b) -> b - a);
        pq.offer(3);
        pq.offer(5);
        System.out.println(pq);


        pq = new PriorityQueue<>();
        pq.offer(3);
        pq.offer(5);
        System.out.println(pq);

        String a = "923";
        String b = "1234";
        System.out.println(a.compareTo(b));


        // test map.get
        Map<String, List<String>> listMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : listMap.entrySet()) {

        }
            String key0 = "abc";
        if (!listMap.containsKey(key0)) {
            listMap.put(key0, new ArrayList<>());
        }
        listMap.get(key0).add("bcd");
        System.out.println(listMap.get(key0));

        if (listMap.containsKey(key0)) {
            List<String> list = listMap.get(key0);
            list.add("bcd");
            // listMap.put(key0, list);
        } else {
            ArrayList<String> list = new ArrayList<>();
            list.add("bcd");
            listMap.put(key0, list);
        }

        //listMap.getOrDefault(key, key);

//        HashMap[] a = new HashMap[2];
//        HashMap<String, String> map = new HashMap<String, String>();
//        map.put("k1", "v1");
//        map.put("k2", "v2");
//        a[0] = map;
//        map = new HashMap<String, String>();
//        map.put("A", "123");
//        a[1] = map;


        String key = "34\n";
        System.out.print(key.toCharArray()[2]);
        for (char ch : key.toCharArray()) {
            System.out.println(ch);
        }
        System.out.println(key.length());
        String other = "34a";

        System.out.println(key.compareTo("34123421") > 0 );
        System.out.println(other.compareTo("34123421") > 0);

        Random r = new Random();


        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] += r.nextInt(100);
        }
        Arrays.sort(array);
        for (int j : array) {
            System.out.println(j);
        }

        Stack<Integer> stack = new Stack<>();
        stack.push(100);
        stack.push(101);
        stack.add(1);
        while (!stack.isEmpty()) {
            System.out.println(stack.peek());
            System.out.println(stack.pop());

        }

        // Queue<Integer> queue = new Deque<>();
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        heap.add(1);
        heap.add(2);
        heap.add(3);
        while (!heap.isEmpty()) {
            System.out.println(heap.peek());
            Integer cur = heap.poll();
            System.out.println(cur);
        }

        int[] cards = new int[] {-1, 2, 3, 4, 5};
        assert isContinusCard(cards);
        cards = new int[] {-1, 2, 3, 4, 6};
        assert isContinusCard(cards);
        cards = new int[] {-1, 11, 12, 13, 10};
        assert isContinusCard(cards);

        cards = new int[] {-1, -2, 12, 13, 10};
        assert isContinusCard(cards);
        cards = new int[] {-1, -2, 9, 13, 10};
        assert isContinusCard(cards);
        cards = new int[] {11, 12, 9, 13, 10};
        assert isContinusCard(cards);
        cards = new int[] {11, 12, 9, 13, 8};
        assert !isContinusCard(cards);


        String test = "abc";
        test.isEmpty();

        System.out.println(test.substring(3));
        System.out.println(test.substring(4));
        System.out.println(test.substring(2));


        ///

    }

    private class IntervalComparator implements Comparator<Integer[]> {
        @Override
        public int compare(Integer[] a, Integer[] b) {
            return a[0] < b[0] ? -1 : a[0] == b[0] ? 0 : 1;
        }
    }
//    public int[][] merge(int[][] intervals) {
//
//        List<Integer[]> list = Arrays.asList((Integer[])) intervals);
//        Collections.sort(list, new IntervalComparator());
//    }
//}

    public static boolean isContinusCard(int[] cards) {
        Arrays.sort(cards);
        // check duplicated
        int min = 14;
        int max = 0;
        int xCount = 0;
        for (int i = 0; i < 4; i++) {
            if (cards[i] == cards[i+1]) {
                return false;
            }
            if (cards[i] < 0) {
                xCount++;
                continue;
            } else {
                if (cards[i] < min) {
                    min = cards[i];
                }
                if (cards[i] > max) {
                    max = cards[i];
                }
            }
        }
        int diff = max - min;
        if (diff > 4) return false;
        if (xCount == 1 && diff > 2) return true;
        if (xCount == 2 && diff > 1) return true;
        return false;
    }


}

class Pair implements Comparable<Pair> {
    int key;
    int value;
    Pair(int key, int value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int compareTo(Pair that) {
        return this.value - that.value;
    }

}
