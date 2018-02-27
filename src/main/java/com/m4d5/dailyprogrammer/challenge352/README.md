# Challenge352
This package is a solution to the programming challenge:
[\[2018-02-23\] Challenge #352 \[Hard\] Well, Well, Well](https://www.reddit.com/r/dailyprogrammer/comments/7zriir/20180223_challenge_352_hard_well_well_well/)

### Solution Details
When new sections of the well are discovered because the dispersed amount of water to add will start filling a neighbor of one of the sections with the lowest relative water level, they are added to the pool. The sections with the lowest relative water levels are then reevaluated, and checked again for neighbors that would begin filling if the water was added. This repeats until no new neighbors have been added (see WellPool::exploreAllNewSections).

## Challenge
### Description
A square well is dug with a peculiar shape: each 1x1 section has varying heights above some floor. You wish to fill the well with water, filling from a hose above the square marked 1. Square 1 is the lowest (think of this as a heightmap in units from the bottom). Water flows at 1 cubic unit per unit time (e.g. 1 liter per minute if you want specific units). You wish to know when you fill a specific square.

You can assume water behaves like it does in the real world - it immediately disperses, evenly, to all accessible regions, and it cannot spontaneously leak from one square to another if there is no path.

Assume a constant flow rate for the water.

Today's question is - writing a program, can you tell at what time the well's target square is under a cubic unit of water?

### Input Description
You'll be given a row with two numbers, N and N, telling you the dimensions of the well. Then you'll be given N rows of N columns of unique numbers. Then you'll get one row with one number, M, telling you the target square to cover with one cubic unit of water. Example:

3 3   
1 9 6   
2 8 5   
3 7 4   
4

### Output Description
Your program should emit the time unit at which time the target square is covered in one cubic unit of water.

The above example's answer should be 16.

Explanation: In this case the column 9 8 7 forms a barrier from the 1 square to the 4 square, our target. As such you have to fill enough to get to a height of 7 to begin filling 4. (7-1) + (7-2) + (7-3) \[all to get over the barrier\] + 1 \[to fill the four block\].