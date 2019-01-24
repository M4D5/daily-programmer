package com.m4d5.dailyprogrammer.challenge372;

import java.util.function.Function;
import java.util.stream.Collectors;

public class Challenge372 {
    public static void main(String[] args) {
        System.out.println(isBalanced("xxxyyyzzz"));
        System.out.println(isBalanced("abccbaabccba"));
        System.out.println(isBalanced("xxxyyyzzzz"));
        System.out.println(isBalanced("abcdefghijklmnopqrstuvwxyz"));
        System.out.println(isBalanced("pqq"));
        System.out.println(isBalanced("fdedfdeffeddefeeeefddf"));
        System.out.println(isBalanced("www"));
        System.out.println(isBalanced("x"));
        System.out.println(isBalanced(""));
    }


    private static boolean isBalanced(String input) {
        // Create a frequency map, and find the number of unique values.
        // The result should be equal to either 0 or 1, since this also means
        // that the string is balanced.
        return input
                .chars()
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .values().stream().distinct().count() <= 1;
    }
}