package com.m4d5.dailyprogrammer.challenge352;

import lombok.Getter;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

@Getter
class Well {
    private final WellSection[][] wellSections;
    private final WellSection lowestSection;
    private final WellPool wellPool;
    private final int width;
    private final int height;

    Well(int width, int height, int[][] depths) {
        this.width = width;
        this.height = height;
        wellSections = new WellSection[height][width];

        for (int y = 0; y < depths.length; y++) {
            for (int x = 0; x < depths[y].length; x++) {
                wellSections[y][x] = new WellSection(this, new Position(x, y), depths[y][x]);
            }
        }

        lowestSection = Arrays.stream(wellSections)
                .flatMap(Arrays::stream)
                .min(Comparator.comparing(WellSection::getDepth))
                .orElseThrow(() -> new IllegalStateException("Unable to find the WellSection of maximum depth."));

        //Pool will always fill at lowest section first
        wellPool = new WellPool(lowestSection);
    }

    public Optional<WellSection> getSectionAtPosition(Position position) {
        if (!isPositionInBounds(position)) {
            return Optional.empty();
        }

        return Optional.of(wellSections[position.getY()][position.getX()]);
    }

    private boolean isPositionInBounds(Position position) {
        return position.getX() >= 0 && position.getY() >= 0 && //Not less than 0
                position.getX() < getWidth() && position.getY() < getHeight(); //Not greater than well width or height
    }

    public void simulate() {
        wellPool.addWater();
    }
}