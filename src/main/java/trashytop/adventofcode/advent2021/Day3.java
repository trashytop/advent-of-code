package trashytop.adventofcode.advent2021;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

// https://adventofcode.com/2021/day/3
public class Day3 implements Day {

  private List<String> numbers;
  private int gammaRate = 0;
  private int epsilonRate = 0;
  private int oxygenGeneratorRating = 0;
  private int co2ScrubberRating = 0;

  enum RatingType {
    OXYGEN_GENERATOR, CO2_SCRUBBER
  }

  public DayResult call() throws IOException {

    numbers = Util.extractLinesFromFile("advent2021/day3/input.txt");

    calcRates();
    calcRating(RatingType.OXYGEN_GENERATOR);
    calcRating(RatingType.CO2_SCRUBBER);

    return new DayResult("Binary Diagnostic",
      (long) gammaRate * epsilonRate,
      (long) oxygenGeneratorRating * co2ScrubberRating);
  }

  private void calcRates() {
    int numBinaryDigits = numbers.get(0).length();
    int numNumbers = numbers.size();
    int[] zeroCount = new int[numBinaryDigits];

    for (String line : numbers) {
      char[] chars = line.toCharArray();
      for (int i = 0; i < chars.length; i++) {
        if (chars[i] == '0') {
          zeroCount[i]++;
        }
      }
    }

    for (int i = 0; i < zeroCount.length; i++) {
      if (zeroCount[i] > numNumbers / 2) {
        epsilonRate += 1 << numBinaryDigits - i - 1;
      } else if (zeroCount[i] < numNumbers / 2) {
        gammaRate += 1 << numBinaryDigits - i - 1;
      } else {
        epsilonRate += 1 << numBinaryDigits - i - 1;
        gammaRate += 1 << numBinaryDigits - i - 1;
      }
    }
  }

  private void calcRating(RatingType ratingType) {
    int nBits = numbers.get(0).length();
    int nNumbers = numbers.size();

    // "zero count" for each bit
    int[] zeroCount = new int[nBits];

    // set to false for a number if number is no longer a possibility
    boolean[] possible = new boolean[nNumbers];
    Arrays.fill(possible, true);

    for (int i = 0; i < nBits; i++) {
      int nPossible = calcZeroCountsForBits(zeroCount, possible);
      if (nPossible == 1) {
        // we have a winner so exit loop
        break;
      }
      // mask for current bit
      int mask = 1 << nBits - i - 1;
      for (int l = 0; l < numbers.size(); l++) {
        if (possible[l]) {
          // number is a possibility so check current bit
          int val = Integer.valueOf(numbers.get(l), 2);
          possible[l] = isPossibleValue(ratingType, zeroCount[i], nPossible, val & mask);
        }
      }
    }

    // find rating of the winner
    for (int l = 0; l < numbers.size(); l++) {
      if (possible[l]) {
        if (ratingType == RatingType.OXYGEN_GENERATOR)
          oxygenGeneratorRating = Integer.parseInt(numbers.get(l), 2);
        else
          co2ScrubberRating = Integer.parseInt(numbers.get(l), 2);
      }

    }

  }

  // return if bit meets criteria or not
  boolean isPossibleValue(RatingType ratingType, int zeroCount, int nPossible, int maskedVal) {
    if (ratingType == RatingType.OXYGEN_GENERATOR) {
      // return true if bit matches most common value
      if (zeroCount > nPossible / 2) {
        // more 0s than 1s so return true if bit is 0
        return (maskedVal) == 0;
      } else {
        // 1s >= 0s so return true if bit is 1
        return (maskedVal) != 0;
      }
    } else {
      // return true if bit matches least common value
      if (zeroCount > nPossible / 2) {
        // more 0s than 1s so return true if bit is 1
        return (maskedVal) != 0;
      } else {
        // 1s >= 0s so return true if bit is 0
        return (maskedVal) == 0;
      }
    }
  }

  // for remaining possible values calc "zero counts" for each bit
  // return count of possible values
  private int calcZeroCountsForBits(int[] zeroCount, boolean[] possible) {
    Arrays.fill(zeroCount, 0);
    int nPossible = 0;
    for (int l = 0; l < numbers.size(); l++) {
      if (possible[l]) {
        nPossible++;
        char[] chars = numbers.get(l).toCharArray();
        for (int j = 0; j < chars.length; j++) {
          if (chars[j] == '0') {
            zeroCount[j]++;
          }
        }
      }
    }
    return nPossible;
  }

}
