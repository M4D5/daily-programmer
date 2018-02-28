package com.m4d5.dailyprogrammer.challenge352;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.fraction.Fraction;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(of = {"position", "depth"})
class WellSection {
    private final Well well;
    private final Position position;
    private final int depth;
    private Fraction sectionWaterLevel = new Fraction(0);

    void addWater(Fraction waterAmt) {
        sectionWaterLevel = sectionWaterLevel.add(waterAmt);
    }

    Fraction getRelativeDepth(){
        return sectionWaterLevel.add(depth);
    }

    List<WellSection> getNeighbors() {
        List<WellSection> neighbors = new ArrayList<>();
        well.getSectionAtPosition(new Position(position.getX(), position.getY() - 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY())).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY())).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX(), position.getY() + 1)).ifPresent(neighbors::add);
        return neighbors;
    }

    List<WellSection> getDiagonalNeighbors() {
        List<WellSection> neighbors = new ArrayList<>();
        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY() - 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY() - 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() - 1, position.getY() + 1)).ifPresent(neighbors::add);
        well.getSectionAtPosition(new Position(position.getX() + 1, position.getY() + 1)).ifPresent(neighbors::add);
        return neighbors;
    }


}