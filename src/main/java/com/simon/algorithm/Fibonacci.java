package com.simon.algorithm;

public class Fibonacci {

    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format(">> f(%s):[%s]", i, f2(i)));
        }
    }

    public static int f(int n) {
        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            return f(n - 1) + f(n - 2);
        }
    }

    public static int f1(int n) {
        if (n <= 1) {
            return n;
        }
        int[] fi = new int[n + 1];
        fi[1] = 1;
        for (int i = 2; i <= n; i++) {
            fi[i] = fi[i - 1] + fi[i - 2];
        }
        return fi[n];
    }

    public static int f2(int n) {
        if (n <= 1) {
            return n;
        }
        int pre1 = 1, pre2 = 0;
        int fi = 0;
        for (int i = 2; i <= n; i++) {
            fi = pre1 + pre2;
            pre2 = pre1;
            pre1 = fi;
        }
        return fi;
    }

}
