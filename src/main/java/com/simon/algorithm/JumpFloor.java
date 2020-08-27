package com.simon.algorithm;

import java.util.Arrays;

public class JumpFloor {

    public static void main(String[] args) {
        int target = 5;
        System.out.println(String.format(">> jumpFloor(%s):[%s]", target, jumpFloor(target)));
    }

    public static int jumpFloor(int target) {
        int[] dp = new int[target];
        Arrays.fill(dp, 1);
        for (int i = 1; i < target; i++)
            for (int j = 0; j < i; j++)
                dp[i] += dp[j];
        return dp[target - 1];
    }

}
