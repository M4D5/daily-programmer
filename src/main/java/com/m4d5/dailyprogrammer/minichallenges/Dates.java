package com.m4d5.dailyprogrammer.minichallenges;

import com.google.common.collect.Range;

import java.text.*;
import java.util.*;

public class Dates {
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("MMMMM d, yyyy", Locale.US);
        Range<Date> dateRange = Range.closed(df.parse("March 2, 2018"), df.parse("March 16, 2018"));
        Range<Date> dateSelection = Range.closed(df.parse("March 10, 2018"), df.parse("March 24, 2018"));

        System.out.println(dateRange.isConnected(dateSelection)); // prints true

        dateSelection = Range.closed(df.parse("March 17, 2018"), df.parse("March 24, 2018"));
        System.out.println(dateRange.isConnected(dateSelection)); // prints false
    }
}
