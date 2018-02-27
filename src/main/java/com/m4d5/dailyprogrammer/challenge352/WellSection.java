package com.m4d5.dailyprogrammer.challenge352;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fraction.Fraction;

import java.util.HashSet;
import java.util.Set;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode
class WellSection {
    private final Well well;
    private final Position position;
    private final int depth;
    private Fraction sectionWaterLevel = new Fraction(0);

    public void addWater(Fraction waterAmt) {
        sectionWaterLevel = sectionWaterLevel.add(waterAmt);
    }

    public Set<WellSection> getNeighbors() {
        Set<WellSection> neighbors = new HashSet<>();

        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY() - 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX(), position.getY() - 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY() - 1)).ifPresent(neighbors::add);

        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY())).ifPresent(neighbors::add);
        //well.getSectionAtPosition(new Position(position.getX(), position.getY())).ifPresent(neighbors::add); ignore self
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY())).ifPresent(neighbors::add);

        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY() + 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX(), position.getY() + 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY() + 1)).ifPresent(neighbors::add);

        return neighbors;
    }
}