package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.List;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/5
public class Day5 implements Day {

  private static final char ROW_LOWER_HALF_LETTER = 'F';
  private static final char SEAT_LOWER_HALF_LETTER = 'L';
  private static final int MAX_POSSIBLE_SEAT_ID = 127 * 8 + 5;
  private List<String> passes;

  public DayResult call() throws IOException {
    passes = Util.extractLinesFromFile("advent2020/day5/input.txt");

    boolean[] seats = new boolean[MAX_POSSIBLE_SEAT_ID];
    int maxSeatId = 0;
    int minSeatId = MAX_POSSIBLE_SEAT_ID;
    for (int i = 0; i < passes.size(); i++) {
      int seatId = calcSeatId(passes.get(i));
      if (seatId > maxSeatId) {
        maxSeatId = seatId;
      }
      if (seatId < minSeatId) {
        minSeatId = seatId;
      }
      seats[seatId] = true;
    }

    int mySeatId = 0;
    for (int i = minSeatId; i < maxSeatId; i++) {
      if (!seats[i]) {
        mySeatId = i;
        break;
      }
    }

    return new DayResult("Binary Boarding", maxSeatId, mySeatId);
  }

  private void fixSpace(Space space, char lowerHalfLetter, char letter) {
    int range = (space.max - space.min) / 2;
    if (letter == lowerHalfLetter) {
      space.max = space.max - range - 1;
    } else {
      space.min = space.min + range + 1;
    }
  }

  private void fixRow(Space space, char letter) {
    fixSpace(space, ROW_LOWER_HALF_LETTER, letter);
  }

  private void fixSeat(Space space, char letter) {
    fixSpace(space, SEAT_LOWER_HALF_LETTER, letter);
  }

  private int calcSeatId(String pass) {
    Space row = new Space(0, 127);
    for (int i = 0; i < 7; i++) {
      fixRow(row, pass.charAt(i));
    }
    Space seat = new Space(0, 7);
    for (int i = 7; i < 10; i++) {
      fixSeat(seat, pass.charAt(i));
    }
    return row.min * 8 + seat.min;
  }

  @Data
  static class Space {
    @NonNull
    private int min;
    @NonNull
    private int max;
  }
}
