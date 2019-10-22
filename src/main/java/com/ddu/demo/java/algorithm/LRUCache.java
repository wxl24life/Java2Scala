package com.ddu.demo.java.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * @author wxl24life
 */
public class LRUCache {
    DequeNode head, tail;
    Map<Integer, DequeNode> map;
    int cap, size;

    public LRUCache(int capacity) {
        this.cap = capacity;
        map = new HashMap<>(capacity);
    }

    public int get(int key) {
        // return -1 if not found
        int value = -1;
        if (map.containsKey(key)) {
            DequeNode node = map.get(key);
            value = node.val;
            if (node != head) {
                // remove node then add to head position
                if (node == tail) {
                    removeLast();
                } else {
                    DequeNode prev = node.prev;
                    prev.next = node.next;
                    node.next.prev = prev;
                    size--;
                }
                addFirst(node);
            }
        }
        return value;
    }

    public void put(int key, int value) {
        // if not found, add it
        // if found, get(key) has move it to head, so just update value
        if (get(key) == -1) {
            if (size == cap) {
                map.remove(tail.key);
                removeLast();
            }
            addFirst(new DequeNode(key, value));
        } else {
            head.val = value;
        }
        map.put(key, head);
    }

    void addFirst(DequeNode node) {
        if (head == null) {
            head = node;
            tail = node;
        } else {
            // move it to head
            node.prev = null;
            node.next = head;
            head.prev = node;
            head = node;
        }
        size++;
    }

    void removeLast() {
        if (tail == null) return;
        if (tail == head) {
            tail = null;
            head = null;
        } else {
            DequeNode prev = tail.prev;
            prev.next = null;
            tail = prev;
        }
        size--;
    }

    public static void main(String[] args) {
        LRUCache obj = new LRUCache(2);
        obj.put(1, 1);
        obj.put(1, 2);
        System.out.println(obj.get(1));
//        obj.put(3,3);
//        obj.get(2);
//        obj.put(4,4);
    }
}

class DequeNode {
    int key;
    int val;
    DequeNode prev;
    DequeNode next;
    DequeNode(int key, int value) {
        this.key = key;
        this.val = value;
    }
}