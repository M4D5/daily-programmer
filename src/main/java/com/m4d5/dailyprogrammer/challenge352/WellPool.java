package com.m4d5.dailyprogrammer.challenge352;


import com.google.common.collect.Lists;
import org.apache.commons.math3.fraction.Fraction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

class WellPool {
    private final Set<WellSection> wellSections = new HashSet<>();
    private final WellSection srcSection;
    private final Well well;

    WellPool(WellSection srcSection, Well well) {
        this.srcSection = srcSection;
        this.well = well;
        wellSections.add(srcSection);
    }

    void addWater() {
        exploreAllNewSections();
        List<WellSection> lowerLevelWaterLevelSections = getLowerWaterLevelSections();
        lowerLevelWaterLevelSections.forEach(s -> s.addWater(getDispersedWaterAmt(lowerLevelWaterLevelSections)));
    }

    //Returns the amount of water to distribute across the new sections (for convenience)
    private void exploreAllNewSections() {
        List<WellSection> targetSections = getLowerWaterLevelSections();
        Fraction dispersedWaterAmt = getDispersedWaterAmt(targetSections);

        //Add any sections that will will be filled when the water is dispersed over the current sections
        for (WellSection wellSection : new ArrayList<>(targetSections)) {
            checkSectionNeighbors(dispersedWaterAmt, wellSection, targetSections);
        }
    }

    private void checkSectionNeighbors(Fraction dispersedWaterAmt, WellSection wellSection, List<WellSection> targetSections) {
        wellSection.getNeighbors().forEach(neighbor -> {
            // If the neighbor has a depth that will allow the water to flow, and isn't already a part of the pool
            if (!wellSections.contains(neighbor) && wellSection.getRelativeDepth().add(dispersedWaterAmt).compareTo(new Fraction(neighbor.getDepth())) > 0) {
                wellSections.add(neighbor);
                //Recurse and explore the updated lowest sections; this should ensure that all the unblocked square's neighbors are tested.
                exploreAllNewSections();
            }
        });

        wellSection.getDiagonalNeighbors().forEach(neighbor -> {
            // If the neighbor exists, has a depth that will allow the water to flow, and isn't already a part of the pool.
            // Diagonal neighbors are a special case, where there may not be a lower section on the path between it and the original section
            List<WellSection> sharedNeighbors = getSharedNeighbors(neighbor, wellSection);
            if (!wellSections.contains(neighbor) && wellSection.getRelativeDepth().add(dispersedWaterAmt).compareTo(new Fraction(neighbor.getDepth())) > 0) {
                //If the relative depth is equal we can save calling getLowerWaterLevelSections again.
                if (sharedNeighbors.stream().noneMatch(nu -> nu.getRelativeDepth().compareTo(new Fraction(neighbor.getDepth())) < 0)) {
                    wellSections.add(neighbor);
                    //Recurse and explore the updated lowest sections; this should ensure that all the unblocked square's neighbors are tested.
                    exploreAllNewSections();
                }
            }
        });
    }

    private List<WellSection> getSharedNeighbors(WellSection neighbor1, WellSection neighbor2) {
        Position sharedNeighbor1Pos = new Position(
                neighbor1.getPosition().getX() + (neighbor2.getPosition().getX() - neighbor1.getPosition().getX()),
                neighbor1.getPosition().getY());

        WellSection sharedNeighbor1 = well.getSectionAtPosition(sharedNeighbor1Pos)
                .orElseThrow(() -> new IllegalStateException("Could not get shared neighbor at position " + sharedNeighbor1Pos.toString()));

        Position sharedNeighbor2Pos = new Position(
                neighbor1.getPosition().getX(),
                neighbor1.getPosition().getY() + (neighbor2.getPosition().getY() - neighbor1.getPosition().getY()));

        WellSection sharedNeighbor2 = well.getSectionAtPosition(sharedNeighbor2Pos)
                .orElseThrow(() -> new IllegalStateException("Could not get shared neighbor at position " + sharedNeighbor2Pos.toString()));

        return Lists.newArrayList(sharedNeighbor1, sharedNeighbor2);
    }

    private Fraction getDispersedWaterAmt(List<WellSection> targetSections) {
        return new Fraction(1).divide(targetSections.size());
    }

    private List<WellSection> getLowerWaterLevelSections() {
        // srcSection will always be at the highest relativeDepth, and we just need everything under that
        List<WellSection> lowerSections = wellSections.stream().filter(s -> s.getRelativeDepth().compareTo(srcSection.getRelativeDepth()) < 0).collect(Collectors.toList());

        if (lowerSections.isEmpty()) {
            lowerSections.addAll(wellSections);
        }

        return lowerSections;
    }
}