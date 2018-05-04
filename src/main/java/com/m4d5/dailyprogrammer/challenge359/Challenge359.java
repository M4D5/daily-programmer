package com.m4d5.dailyprogrammer.challenge359;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Challenge359 {
    @SneakyThrows
    public static void main(String[] args) {
        List<String> lines = IOUtils.readLines(Challenge359.class.getClassLoader().getResourceAsStream("challenge359input.txt"), StandardCharsets.UTF_8);
        int maxWidth = lines.stream()
                .max(Comparator.comparing(String::length)).orElseThrow(() -> new IllegalStateException("Failed to find the longest line."))
                .length();

        List<String> paragraphs = new ArrayList<>();
        StringBuilder currentParagraph = new StringBuilder();

        // For all lines except for the last one in reverse order
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            currentParagraph.append(line).append(" ");

            if(i < lines.size() - 1) {
                // If the line ends with a punctuation mark and the first word of the next line (+1 for space after punctuation) added to this line is still as long or shorter than maxWidth.
                if (line.matches(".*[.?!]") && line.length() + getFirstWordLength(lines.get(i + 1)) + 1 <= maxWidth) {
                    paragraphs.add(currentParagraph.toString());
                    currentParagraph.setLength(0); // reset StringBuilder
                }
            } else {
                paragraphs.add(currentParagraph.toString());
            }
        }

        paragraphs.forEach(System.out::println);
    }

    private static int getFirstWordLength(String line) {
        return line.substring(0, line.indexOf(' ')).length();
    }
}