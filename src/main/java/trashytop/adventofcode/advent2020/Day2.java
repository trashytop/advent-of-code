package trashytop.adventofcode.advent2020;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.NonNull;

public class Day2 implements Day {

  private List<String> lines;

  public String getName() {
    return "Day 2: Password Philosophy";
  }

  public void solve() throws IOException {
    lines = Util.buildArrayOfLinesFromFile("day2/input.txt");

    Predicate<Password> counter1 = (day2 -> {
      long count = day2.s.chars().filter(ch -> ch == day2.c).count();
      return (count >= day2.x && count <= day2.y);
    });
    System.out.println("#1: Valid password count:" + getCount(counter1));

    Predicate<Password> counter2 = (day2 -> {
      Boolean pos1Match = day2.s.charAt(day2.x - 1) == day2.c;
      Boolean pos2Match = day2.s.charAt(day2.y - 1) == day2.c;
      return ((pos1Match || pos2Match) && !(pos1Match && pos2Match));
    });
    System.out.println("#2: Valid password count:" + getCount(counter2));
  }

  private long getCount(Predicate<Password> counter) {
    // parse x-y c: s for each line
    Pattern pattern = Pattern.compile("([0-9]{1,2})-([0-9]{1,2}) ([a-z]): ([a-z]+)");
    return lines
        .stream()
        .filter(line -> {
          Matcher matcher = pattern.matcher(line);
          matcher.find();
          Password day2 = new Password(Integer.parseInt(matcher.group(1)),
              Integer.parseInt(matcher.group(2)),
              matcher.group(3).charAt(0),
              matcher.group(4));
          return counter.test(day2);
        })
        .count();
  }

  @Data
  static class Password {
    @NonNull
    private int x;
    @NonNull
    private int y;
    @NonNull
    private char c;
    @NonNull
    private String s;
  }

}
