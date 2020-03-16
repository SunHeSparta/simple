package com.simon.algorithm;

/**
 * 回文字符串
 * Create by SunHe on 2020/3/16
 */
public class HuiWenString {

    public static void main(String[] args) {
        String s = "abcba";
        char[] chars = s.toCharArray();
        boolean flag = true;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] != chars[chars.length - i - 1]) {
                flag = false;
                break;
            }
        }
        if (flag) {
            System.out.println("回文字符串");
        } else {
            System.out.println("不是回文字符串");
        }
    }

}
