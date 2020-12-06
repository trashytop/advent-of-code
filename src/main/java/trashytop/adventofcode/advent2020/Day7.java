package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.List;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/7
public class Day7 implements Day {

  private List<String> passes;

  public String getName() {
    return "Day 7: xxx";
  }

  public void solve() throws IOException {
    passes = Util.extractLinesFromFile("advent2020/day7/input.txt");
  }
}
