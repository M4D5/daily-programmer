package com.m4d5.dailyprogrammer.challenge352;

import org.apache.commons.math3.fraction.Fraction;

import java.util.Arrays;
import java.util.Scanner;

public class Challenge352 {
    public static void main(String[] args) {
        int[][] depths;
        final int targetDepth;
        int width, height;

        try (Scanner s = new Scanner(System.in)) {
            height = s.nextInt();
            width = s.nextInt();

            depths = new int[height][width];

            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    depths[y][x] = s.nextInt();
                }
            }

            targetDepth = s.nextInt();
        }

        Well well = new Well(width, height, depths);
        WellSection targetSection = Arrays.stream(well.getWellSections())
                .flatMap(Arrays::stream)
                .filter(s -> s.getDepth() == targetDepth)
                .findFirst().orElseThrow(() -> new IllegalStateException("Could not find target section."));

        int elapsedSeconds = 0;

        while (targetSection.getSectionWaterLevel().compareTo(new Fraction(1)) < 0) {
            well.simulate();
            elapsedSeconds++;
        }
        System.out.println(elapsedSeconds);
    }
}