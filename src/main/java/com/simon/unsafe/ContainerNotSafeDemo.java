package com.simon.unsafe;

import java.util.ArrayList;

/**
 * Create by SunHe on 2020/2/12
 */
public class ContainerNotSafeDemo {

    public static void main(String[] args) {
        new ArrayList<Integer>().add(1);
    }

}
