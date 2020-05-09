package com.simon.stream;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Create by SunHe on 2020/4/9
 */
public class StringList {

    public static void main(String[] args) {

        String delimiter = "|";

        List<String> list = new ArrayList<>();
        list.add("1|2|3|4|5|6|");
        list.add("7|8|9|4|5|6|");

//        for (String s : list) {
//            String[] split = s.split("[," + delimiter + "]");
//            System.out.println(s);
//        }

        list = list.stream()
                .map(s -> s.replace("|", ","))
                .collect(Collectors.toList());
        System.out.println(list);

        for (String s : list) {

        }
    }


}
