package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.List;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/2
public class Day2 implements Day {

  private List<String> lines;

  public String getName() {
    return "Day 2: Password Philosophy";
  }

  public void solve() throws IOException {
    lines = Util.extractLinesFromFile("advent2020/day2/input.txt");

    Predicate<PasswordWithSpec> passwordChecker1 = (passwordWithSpec -> {
      long count = passwordWithSpec.password.chars().filter(ch -> ch == passwordWithSpec.letter).count();
      return (count >= passwordWithSpec.minTimesLetterMustAppear && count <= passwordWithSpec.maxTimesLetterMustAppear);
    });
    System.out.println("#1: Valid password count:" + getCount(passwordChecker1));

    Predicate<PasswordWithSpec> passwordChecker2 = (passwordWithSpec -> {
      Boolean pos1Match = passwordWithSpec.password.charAt(passwordWithSpec.minTimesLetterMustAppear - 1) ==
          passwordWithSpec.letter;
      Boolean pos2Match = passwordWithSpec.password.charAt(passwordWithSpec.maxTimesLetterMustAppear - 1) ==
          passwordWithSpec.letter;
      return ((pos1Match || pos2Match) && !(pos1Match && pos2Match));
    });
    System.out.println("#2: Valid password count:" + getCount(passwordChecker2));
  }

  private long getCount(Predicate<PasswordWithSpec> passwordChecker) {
    // parse for each line
    Pattern pattern = Pattern.compile("([0-9]{1,2})-([0-9]{1,2}) ([a-z]): ([a-z]+)");
    return lines
        .stream()
        .filter(line -> {
          Matcher matcher = pattern.matcher(line);
          matcher.find();
          PasswordWithSpec passwordWithSpec = new PasswordWithSpec(Integer.parseInt(matcher.group(1)),
              Integer.parseInt(matcher.group(2)),
              matcher.group(3).charAt(0),
              matcher.group(4));
          return passwordChecker.test(passwordWithSpec);
        })
        .count();
  }

  @Data
  static class PasswordWithSpec {
    @NonNull
    private int minTimesLetterMustAppear;
    @NonNull
    private int maxTimesLetterMustAppear;
    @NonNull
    private char letter;
    @NonNull
    private String password;
  }

}
