package com.ddu.demo.java.algorithm;

/**
 * @author wxl24life
 */
public class LC147 {

    public ListNode insertionSortList(ListNode head) {
        ListNode fackeHead = new ListNode(-1);
        ListNode maxNode = new ListNode(Integer.MAX_VALUE);
        maxNode.next = null;
        ListNode fakedHead = new ListNode(0);
        fakedHead.next = maxNode;

        ListNode curr = head;
        while (curr != null) {
            ListNode prev = fakedHead;
            ListNode p = fakedHead.next;
            while (p != null && p.val <= curr.val) {
                prev = p;
                p = p.next;
            }

            ListNode nextTemp = curr.next;
            curr.next = prev.next;
            prev.next = curr;
            curr = nextTemp;
        }
        maxNode = null;
        return fackeHead.next;
    }
}

class ListNode {
    int val;
    ListNode next;
    ListNode(int val) {
        this.val = val;
    }
}
