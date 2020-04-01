package com.simon.entity;

import lombok.Data;
import org.openjdk.jol.info.ClassLayout;

/**
 * Create by SunHe on 2020/3/21
 */
@Data
public class User {
    private String name;
    private Integer age;
    private boolean sex;

    public static void main(String[] args) {
        User user = new User();
        user.setAge(12);
        user.setName("lee");
        System.out.println(ClassLayout.parseInstance(user).toPrintable());
        System.out.println();
//        System.out.println(ClassLayout.parseInstance(user).instanceSize());
        System.out.println();
//        System.out.println(ClassLayout.parseInstance(user).headerSize());
        System.out.println();
//        System.out.println(System.getProperties());
    }
}
