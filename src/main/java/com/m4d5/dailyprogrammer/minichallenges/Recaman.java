package com.m4d5.dailyprogrammer.minichallenges;

import com.google.common.collect.Range;
import java.util.*;

public class Recaman {
    public static void main(String[] args){
        long target;
        try(Scanner s = new Scanner(System.in)){
            target = s.nextLong();
        }

        System.out.println(solveSmall(target));
    }

    private static long solveSmall(long target) {
        long current = 0;
        Set<Long> encountered = new HashSet<>();
        for (long i = 1; i < target + 1; i++) {
            encountered.add(current);
            long minusAttempt = current - i;
            if(minusAttempt >= 0 && !encountered.contains(minusAttempt)) {
                current = minusAttempt;
            } else {
                current = current + i;
            }
        }
        return current;
    }

    private static long solveBig(long target) {
        long current = 0;
        List<Range<Long>> encountered = new ArrayList<>();
        long optimizeInterval = 1000;

        for (long i = 1; i < target + 1; i++) {
            if(notContainedInRanges(current, encountered)){
                encountered.add(Range.singleton(current));
            }

            long minusAttempt = current - i;
            if(minusAttempt >= 0 && notContainedInRanges(minusAttempt, encountered)) {
                current = minusAttempt;
            } else {
                current = current + i;
            }

            if(i % optimizeInterval == 0) {
                encountered = optimizeRanges(encountered);
                optimizeInterval *= 1.01; // 1.01 is arbitrary, maybe faster with some tuning.
            }
        }

        return current;
    }

    private static boolean notContainedInRanges(Long target, List<Range<Long>> ranges) {
        // Start from the highest range to avoid running through almost the whole list first in most cases.
        for (int i = ranges.size() - 1; i >= 0; i--) {
            if (ranges.get(i).contains(target)) {
                return false;
            }
        }
        return true;
    }

    // Combine consecutive numbers into the same range
    private static List<Range<Long>> optimizeRanges(List<Range<Long>> rangesOrig) {
        List<Range<Long>> ranges = new ArrayList<>();
        rangesOrig.sort(Comparator.comparing(Range::lowerEndpoint));
        Range<Long> currentRange = rangesOrig.get(0);
        for (int i = 1; i < rangesOrig.size(); i++) {
            Range<Long> range = rangesOrig.get(i);
            if (currentRange.upperEndpoint() + 1 == range.lowerEndpoint()) {
                currentRange = Range.closed(currentRange.lowerEndpoint(), range.upperEndpoint());
            } else {
                ranges.add(currentRange);
                currentRange = range;
            }
        }
        return ranges;
    }
}
