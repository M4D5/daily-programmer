package com.m4d5.dailyprogrammer.challenge352;


import org.apache.commons.math3.fraction.Fraction;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

class WellPool {
    private final Set<WellSection> wellSections = new HashSet<>();

    WellPool(WellSection srcSection) {
        wellSections.add(srcSection);
    }

    public void addWater() {
        final Fraction dispersedWaterAmt = exploreAllNewSections();
        getLowestWaterLevelSections().forEach(s -> s.addWater(dispersedWaterAmt));
    }

    //Returns the amount of water to distribute across the new sections (for convenience)
    private Fraction exploreAllNewSections() {
        Set<WellSection> targetSections = getLowestWaterLevelSections();
        Fraction dispersedWaterAmt = new Fraction(1).divide(targetSections.size());

        //Add any sections that will will be filled when the water is dispersed over the current sections
        for (WellSection wellSection : targetSections) {
            for (WellSection neighbor : wellSection.getNeighbors()) {
                // If the neighbor exists, has a depth that will allow the water to flow, and isn't already a part of the pool
                if (!wellSections.contains(neighbor) && wellSection.getSectionWaterLevel().add(wellSection.getDepth()).add(dispersedWaterAmt).compareTo(new Fraction(neighbor.getDepth())) > 0) {
                    wellSections.add(neighbor);

                    //Recurse and explore the updated lowest sections; this should ensure that all the unblocked square's neighbors are tested.
                    return exploreAllNewSections();
                }
            }
        }

        return dispersedWaterAmt;
    }

    private Set<WellSection> getLowestWaterLevelSections() {
        final Fraction minWaterLevel = wellSections.stream().map(ws -> ws.getSectionWaterLevel().add(ws.getDepth())).min(Fraction::compareTo).orElseThrow(() -> new IllegalStateException("Unable to find the sections with the lowest water levels."));
        return wellSections.stream().filter(s -> (s.getSectionWaterLevel().add(s.getDepth())).equals(minWaterLevel)).collect(Collectors.toSet());
    }
}