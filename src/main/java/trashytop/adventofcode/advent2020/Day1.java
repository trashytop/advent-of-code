package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.List;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/1
public class Day1 implements Day {

  private List<Integer> numbers;

  public String getName() {
    return "Day 1: Report Repair";
  }

  public void solve() throws IOException {
    numbers = Util.extractIntegersFromFile("advent2020/day1/input.txt");

    // find product of first two entries that sum to 2020
    loopDouble:
    for (int index1 = 0; index1 < numbers.size(); index1++) {
      for (int index2 = 0; index2 < numbers.size(); index2++) {
        int n1 = numbers.get(index1);
        int n2 = numbers.get(index2);
        if (n1 + n2 == 2020) {
          System.out.println("#1: Product:" + (n1 * n2));
          break loopDouble;
        }
      }
    }

    // find product of first three entries that sum to 2020
    loopTriple:
    for (int index1 = 0; index1 < numbers.size(); index1++) {
      for (int index2 = 0; index2 < numbers.size(); index2++) {
        for (int index3 = 0; index3 < numbers.size(); index3++) {
          int n1 = numbers.get(index1);
          int n2 = numbers.get(index2);
          int n3 = numbers.get(index3);
          if (n1 + n2 + n3 == 2020) {
            System.out.println("#2: Product:" + (n1 * n2 * n3));
            break loopTriple;
          }
        }
      }
    }
  }

}
