package com.m4d5.dailyprogrammer.challenge352;

import org.apache.commons.math3.fraction.Fraction;

import java.io.PrintStream;
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
            //render(well, System.out);
            well.simulate();
            elapsedSeconds++;

            if(elapsedSeconds % 10000 == 0) {
                System.out.println(elapsedSeconds);
            }
        }

        render(well, System.out);

        System.out.println(elapsedSeconds);
    }

    private static void render(Well well, PrintStream printStream) {
        StringBuilder stringBuilder = new StringBuilder();

        for (WellSection[] rows : well.getWellSections()) {
            for (WellSection ws : rows) {
                double relativeDepth = Math.round(ws.getRelativeDepth().percentageValue() / 10) / 10d;
                stringBuilder.append(relativeDepth).append(" ");
            }
            stringBuilder.append("\n");
        }

        stringBuilder.append("\n\n");
        printStream.print(stringBuilder);
    }
}