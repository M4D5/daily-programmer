package com.m4d5.dailyprogrammer.minichallenges;

import com.google.common.collect.Range;

import java.util.*;

public class Recaman {

    public static void main(String[] args) {
        long target;
        try (Scanner s = new Scanner(System.in)) {
            target = s.nextLong();
        }

        System.out.println(solveBig(target));
    }

    private static long solveSmall(long target) {
        long current = 0;
        Set<Long> encountered = new HashSet<>();
        for (long i = 1; i < target + 1; i++) {
            encountered.add(current);
            long minusAttempt = current - i;
            if (minusAttempt >= 0 && !encountered.contains(minusAttempt)) {
                current = minusAttempt;
            } else {
                current = current + i;
            }
        }
        return current;
    }

    private static long solveBig(long target) {
        long current = 0;
        TreeMap<Long, Range<Long>> encountered = new TreeMap<>();
        encountered.put(0L, Range.singleton(0L));
        long optimizeInterval = 1000;

        for (long i = 1; i < target + 1; i++) {

            long minusAttempt = current - i;
            if (minusAttempt >= 0 && notContainedInRanges(minusAttempt, encountered)) {
                current = minusAttempt;
                encountered.put(current, Range.singleton(current));
            } else {
                current = current + i;
                if (notContainedInRanges(current, encountered)) {
                    encountered.put(current, Range.singleton(current));
                }
            }

            // Do the first optimization at 100
            if (i % optimizeInterval == 100) {
                encountered = optimizeRanges(encountered);
                optimizeInterval *= 1.01; // 1.01 is arbitrary, maybe faster with some tuning.
            }
        }

        return current;
    }

    private static boolean notContainedInRanges(Long target, NavigableMap<Long, Range<Long>> ranges) {
        Map.Entry<Long, Range<Long>> range = ranges.floorEntry(target);
        return range == null || target > range.getValue().upperEndpoint();
    }

    // Combine consecutive numbers into the same range
    private static TreeMap<Long, Range<Long>> optimizeRanges(TreeMap<Long, Range<Long>> rangesOrig) {
        if(rangesOrig.size() == 0) {
            return rangesOrig;
        }

        TreeMap<Long, Range<Long>> ranges = new TreeMap<>();
        Range<Long> currentRange = rangesOrig.get(0L);
        rangesOrig.remove(0L);

        while (rangesOrig.values().size() > 0) {
            if (rangesOrig.containsKey(currentRange.upperEndpoint() + 1)) {
                Range<Long> range =  rangesOrig.get(currentRange.upperEndpoint() + 1);
                currentRange = Range.closed(currentRange.lowerEndpoint(), range.upperEndpoint());
                rangesOrig.remove(range.lowerEndpoint());
            } else {
                ranges.put(currentRange.lowerEndpoint(), currentRange);
                currentRange = rangesOrig.values().iterator().next();
                rangesOrig.remove(currentRange.lowerEndpoint());
            }
        }

        ranges.put(currentRange.lowerEndpoint(), currentRange);

        return ranges;
    }
}