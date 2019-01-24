# Challenge372
This package is a solution to the programming challenge:
[\[2019-01-14\] Challenge #372 \[Easy\] Perfectly balanced](https://old.reddit.com/r/dailyprogrammer/comments/afxxca/20190114_challenge_372_easy_perfectly_balanced/)

### Solution Details
Bonus version only;
Solved with a one-liner that primarily uses Java's stream api. It first creates a frequency map, then finds the distinct values and checks if the result is equal to 0 or 1.

## Challenge
### Description
Given a string containing only lowercase letters, find whether every letter that appears in the string appears the same number of times. Don't forget to handle the empty string ("") correctly!

### Input Description
    balanced_bonus("xxxyyyzzz") => true
    balanced_bonus("abccbaabccba") => true
    balanced_bonus("xxxyyyzzzz") => false
    balanced_bonus("abcdefghijklmnopqrstuvwxyz") => true
    balanced_bonus("pqq") => false
    balanced_bonus("fdedfdeffeddefeeeefddf") => false
    balanced_bonus("www") => true
    balanced_bonus("x") => true
    balanced_bonus("") => true
Note that 'balanced_bonus' behaves differently than 'balanced' for a few inputs, e.g. "x".