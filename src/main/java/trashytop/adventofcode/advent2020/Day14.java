package trashytop.adventofcode.advent2020;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalInt;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// https://adventofcode.com/2020/day/14
public class Day14 implements Day {

  private final static String LINE_REGEX = "\\[|\\] = | = ";
  private final static String MASK_TOKEN = "mask";
  private List<String> lines;

  public DayResult call() throws IOException {
    lines = Util.extractLinesFromFile("advent2020/day14/input.txt");

    return new DayResult("Docking Data", solvePart1(), solvePart2());
  }

  private long solvePart1() {
    long andMask = 0;
    long orMask = 0;
    Map<String, Long> memory = new HashMap<>();
    for (String line : lines) {
      String[] parts = line.split(LINE_REGEX);
      if (parts[0].equals(MASK_TOKEN)) {
        orMask = Long.parseLong(parts[1].replaceAll("X", "0"), 2);
        andMask = Long.parseLong(parts[1].replaceAll("X", "1"), 2);
      } else {
        long val = Long.parseLong(parts[2]);
        val |= orMask;
        val &= andMask;
        memory.put(parts[1], val);
      }
    }
    return memory.values().stream().mapToLong(v -> v).sum();
  }

  private long solvePart2() {
    String mask = "";
    Map<Long, Long> memory = new HashMap<>();
    for (String line : lines) {
      String[] parts = line.split(LINE_REGEX);
      if (parts[0].equals(MASK_TOKEN)) {
        mask = parts[1];
      } else {
        List<String> addresses = new ArrayList<>();
        long val = Long.parseLong(parts[2]);
        char[] addressChars = Long.toBinaryString(Long.parseLong(parts[1])).toCharArray();
        char[] maskChars = mask.toCharArray();

        // different lengths so work backwards
        // mask is always longer than address
        for (int i = 0; i < maskChars.length; ++i) {
          int mcIndex = maskChars.length - 1 - i;
          int acIndex = addressChars.length - 1 - i;
          char c = maskChars[mcIndex];
          if (c == '0') {
            maskChars[mcIndex] = (acIndex >= 0) ? addressChars[acIndex] : '0';
          }
        }

        generateAddresses(new String(maskChars), addresses);
        for (String address : addresses) {
          memory.put(Long.parseLong(address, 2), val);
        }
      }
    }
    return memory.values().stream().mapToLong(v -> v).sum();
  }

  private void generateAddresses(String s, List<String> addresses) {
    if (!s.contains("X")) {
      addresses.add(s);
      return;
    }
    String one = s, two = s;
    one = one.replaceFirst("X", "1");
    two = two.replaceFirst("X", "0");
    generateAddresses(one, addresses);
    generateAddresses(two, addresses);
  }
}
