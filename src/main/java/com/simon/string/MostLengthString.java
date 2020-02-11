package com.simon.string;

/**
 * Create by SunHe on 2020/1/16
 */
public class MostLengthString {

    public static void main(String[] args) {
        String s = "dvdf";
        int maxLength = lengthOfLongestSubstring(s);
        System.out.println(maxLength);
    }

    public static int lengthOfLongestSubstring(String s) {
        //128个ASCII码+128个扩展ASCII码=256个,所以申请了一个长度为256的int数组
        int[] dict = new int[256];
        int maxLenth = 0;
        int currentLenth = 0;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            //当前下标的字符距离上一下标同字符的长度
            int sameCharLength = i - dict[c] + 1;
            dict[c] = i + 1;//当前字符结尾的字符串长度
            if (sameCharLength <= currentLenth) {
                //如果当前下标的字符距离上一下标同字符的长度小于等于当前长度
                //则将当前下标的字符距离上一下标同字符的长度赋值给当前长度
                currentLenth = sameCharLength;
            } else {
                currentLenth ++;
            }
            if (currentLenth > maxLenth) {
                maxLenth = currentLenth;
            }
        }
        return maxLenth;
    }
}
