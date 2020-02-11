package com.simon.string;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Create by SunHe on 2020/1/17
 */
public class LongestCommonPrefix {

    public static void main(String[] args) {
        String[] array = {"flower", "flow", "flight"};
        String prefix = longestCommonPrefix(array);
        System.out.println(prefix);
    }

    private static String longestCommonPrefix(String[] array) {
        if (ArrayUtils.isEmpty(array)) {
            return "";
        }
        char c = array[0].charAt(0);
        Node node = new Node(c, null);
        for (String s : array) {
            if (s.charAt(0) != c) {
                return "";
            }
            char[] chars = s.toCharArray();
            for (char cha : chars) {

            }

        }
        return null;
    }

    static class Node {
        private char value;
        private Node nextNode;

        public Node(char value, Node nextNode) {
            this.value = value;
            this.nextNode = nextNode;
        }

        public char getValue() {
            return value;
        }

        public void setValue(char value) {
            this.value = value;
        }

        public Node getNextNode() {
            return nextNode;
        }

        public void setNextNode(Node nextNode) {
            this.nextNode = nextNode;
        }
    }

}
