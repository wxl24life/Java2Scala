package com.ddu.demo.java.algorithm.recursion;

import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

/**
 * EIP book: P16.1 <p/>
 *
 * A recursive solution
 * @author wxl24life
 */
public class TowerHanoi {
    private static final int NUM_PEGS = 3;

    static void computeTowerHanoi(int numRings) {
        List<Deque<Integer>> pegs = new ArrayList<>();
        for (int i = 0; i < NUM_PEGS; i++) {
            pegs.add(new LinkedList<Integer>());
        }

        // Initialize pegs
        for (int i = 0; i < numRings; i++) {
            pegs.get(0).addFirst(numRings - i);
        }
        computeTowerHanoiSteps(numRings, pegs, 0, 1, 2);
    }

    private static void computeTowerHanoiSteps(int numRings, List<Deque<Integer>> pegs, int from, int to, int use) {
        if (numRings == 0) return;
        computeTowerHanoiSteps(numRings - 1, pegs, from, use, to);
        System.out.println(String.format("Move from %s to %s", from, to));
        int remained = pegs.get(from).getFirst();
        pegs.get(to).addFirst(remained);
        computeTowerHanoiSteps(numRings - 1, pegs, use, to, from);
    }

    public static void main(String[] args) {
        computeTowerHanoi(6);
    }
}
