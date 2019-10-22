package com.ddu.demo.java.algorithm;

import java.util.Stack;

/**
 * @author wxl24life
 */
public class LC155 {
    public static void main(String[] args) {
        MinStack minStack = new MinStack();
        minStack.push(512);
        minStack.push(-1024);
        minStack.push(-1024);
        minStack.push(512);
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
        minStack.pop();
        System.out.println(minStack.getMin());
    }
}

class MinStack {

    Stack<Integer> stack1 = new Stack<Integer>();
    Stack<Integer> stack2 = new Stack<Integer>();

    /** initialize your data structure here. */
    public MinStack() {
    }

    public void push(int x) {
        if (stack2.isEmpty() || x <= getMin()) {
            stack2.push(x);
        }
        stack1.push(x);
    }

    public void pop() {
        if (stack1.peek() == getMin()) {
            stack2.pop();
        }
        stack1.pop();
    }

    public int top() {
        return stack1.peek();
    }

    public int getMin() {
        return stack2.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(x);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
