package com.ddu.demo.java.algorithm.eip;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author wxl24life
 */
public class CollectionsBinarySearch {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);
        list.add(4);
        list.add(6);
        list.add(8);
        Collections.binarySearch(list, 4);

        list = new LinkedList<>();
        list.add(3);
        list.add(4);
        list.add(6);
        list.add(8);
        Collections.binarySearch(list, 4);
    }
}
