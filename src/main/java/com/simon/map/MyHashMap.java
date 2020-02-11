package com.simon.map;

import java.util.HashMap;
import java.util.Map;

/**
 * Create by SunHe on 2020/1/15
 */
public class MyHashMap {

    public static void main(String[] args) {
        Map<Object, Object> map = new HashMap<>(3);

        for (int i = 0; i < 5; i++) {
            map.put("k" + i, i);
        }

    }


//    static final int MAXIMUM_CAPACITY = 1 << 30;
//
//    public static void main(String[] args) {
//        int cap = 3;
//        int i = tableSizeFor(cap);
//        System.out.println(i);
//    }
//
//    static int tableSizeFor(int cap) {
//        int n = cap - 1;
//        n |= n >>> 1;
//        n |= n >>> 2;
//        n |= n >>> 4;
//        n |= n >>> 8;
//        n |= n >>> 16;
//        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
//    }

}
