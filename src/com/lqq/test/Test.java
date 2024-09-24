package com.lqq.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

public class Test {
    public static void main(String[] args) {
        Vector<Integer> arr = new Vector<>();
        arr.add(1);
        arr.add(2);
        arr.add(5);
        arr.add(9);
        arr.add(7);
        Iterator<Integer> iterator = arr.iterator();
        while (iterator.hasNext()) {
            Integer next =  iterator.next();
            if(next == 2){
                iterator.remove();
            }
        }
        for (Integer integer:arr) {
            System.out.println(integer);
        }
        System.out.println(arr);
    }
}
