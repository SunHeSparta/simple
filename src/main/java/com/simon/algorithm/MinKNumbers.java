package com.simon.algorithm;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
 * Create by SunHe on 2020/3/16
 */
public class MinKNumbers {

    public static void main(String[] args) {
        int[] input = {4,5,1,6,2,7,3,8};
        ArrayList<Integer> arrayList = new MinKNumbers().GetLeastNumbers_Solution(input, 4);
        System.out.println(arrayList.toString());
    }

    // 本题解法适合处理海量数据
    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        // 1.非空判断
        if (k > input.length || k <= 0) {
            return new ArrayList<>();
        }

        // 2.Java中TreeSet底层基于TreeMap实现，TreeMap底层基于红黑树实现，因此对于删除，查找，添加的时间复杂度都是基于logn的
        TreeSet<Integer> results = new TreeSet<>();

        for (int value : input) {
            if (results.size() < k) {   // 存的数小于K
                results.add(value);
            } else {
                Integer maxValue = results.last();
                if (value < maxValue) {
                    results.remove(maxValue);
                    results.add(value);
                }
            }
        }

        return new ArrayList<>(results);
    }
}
