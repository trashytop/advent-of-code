package trashytop.adventofcode.advent2020;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

import java.io.IOException;
import java.util.List;

// https://adventofcode.com/2020/day/9
public class Day9 implements Day {

  private static final int PREAMBLE = 25;

  private List<Integer> numbers;

  public DayResult call() throws IOException {
    numbers = Util.extractIntegersFromFile("advent2020/day9/input.txt");

    boolean exit = false;
    int invalidNumber = 0;
    for (int i = PREAMBLE; i < numbers.size() && !exit; i++) {
      if (isInvalidNumber(i)) {
        invalidNumber = numbers.get(i);
        exit = true;
      }
    }

    int sumMinMax = findContiguousSet(invalidNumber);

    return new DayResult("Encoding Error", invalidNumber, sumMinMax);
  }

  private boolean isInvalidNumber(int index) {
    for (int i = index - PREAMBLE; i < index; i++) {
      for (int j = i + 1; j < index; j++) {
        if (numbers.get(i) + numbers.get(j) == numbers.get(index)) {
          return false;
        }
      }
    }
    return true;
  }

  private int findContiguousSet(int number) {
    for (int i = 0; i < numbers.size(); i++) {
      int p = numbers.get(i);
      int min, max, sum;
      min = max = sum = p;
      int j = i + 1;
      while (sum < number) {
        int q = numbers.get(j);
        min = Integer.min(min, q);
        max = Integer.max(max, q);
        sum += q;
        j++;
      }
      if (sum == number) {
        return min + max;
      }
    }
    return 0;
  }

}
