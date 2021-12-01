package trashytop.adventofcode.advent2021;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2021/day/1
public class Day1 implements Day {

  private List<Integer> depths;

  private int increasingDepthCount = 0;
  private int increasingSlidingWindowCount = 0;

  public DayResult call() throws IOException {

    depths = Util.extractIntegersFromFile("advent2021/day1/input.txt");

    calcCountOfIncreasingDepths();
    calcCountOfIncreasingSlidingWindowDepthSums();

    return new DayResult("Sonar Sweep", increasingDepthCount, increasingSlidingWindowCount);
  }

  private void calcCountOfIncreasingDepths() {
    increasingDepthCount = Util.calcCountOfIncreasingValues(depths);
  }

  private void calcCountOfIncreasingSlidingWindowDepthSums() {
    List<Integer> slidingWindowDepthSums = Util.calcSlidingWindowSums(3, depths);
    increasingSlidingWindowCount = Util.calcCountOfIncreasingValues(slidingWindowDepthSums);
  }

}
