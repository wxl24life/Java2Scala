package com.ddu.demo.java.algorithm;

/**
 * 2019-09-25
 * @author wxl24life
 */
public class MergeTwoSortedListNode {
    static ListNode merge(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode dummyHead = new ListNode(0);
        dummyHead.next = l1.val <= l2.val ? l1 : l2;
        ListNode p = dummyHead;
        ListNode q = l1.val <= l2.val ? l2 : l1;
        while (p.next != null && q != null) {
            while (p.next != null && p.next.val <= q.val) {
                p = p.next;
            }
            ListNode tmp = p.next;
            p.next = q;
            q = tmp;
            p = p.next;
        }
        if (p.next == null) {
            p.next = q;
        }
        return dummyHead.next;
    }


    // wrong answer, head = head.next!!
    ListNode merge2(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head = new ListNode(-1);
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                head.next = l1;
                l1 = l1.next;
            } else {
                head.next = l2;
                l2 = l2.next;
            }
        }
        head.next = l1 == null ? l2 : l1;
        return head.next;
    }

    // best answer
    ListNode merge3(ListNode l1, ListNode l2) {
        if (l1 == null) return l2;
        if (l2 == null) return l1;
        ListNode head = new ListNode(-1);
        ListNode curr = head;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        curr.next = l1 == null ? l2 : l1;
        return head.next;
    }

    // 单链表寻找中间节点(中间节点有两个，返回第一个），快慢指针，快指针初始往前多移动一位
    ListNode findMidNode(ListNode node) {
        if (node == null) return null;
        ListNode fast = node.next, slow = node;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }
        return slow;
    }

    static void printListNode(ListNode node) {
        while (node != null) {
            System.out.print(node.val + ",");
            node = node.next;
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);

        node2.next = node3;
        printListNode(node1);
        System.out.println();
        printListNode(node2);
        System.out.println();
        printListNode(merge(node1, node2));



    }
}


