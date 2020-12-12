package trashytop.adventofcode.advent2020;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// https://adventofcode.com/2020/day/11
public class Day11 implements Day {

  private static final char VACANT_SEAT = 'L';
  private static final char OCCUPIED_SEAT = '#';
  private static final char FLOOR = '.';
  private static final int INFINITE_DISTANCE = -1;
  private static final int LEFT = -1;
  private static final int RIGHT = 1;
  private static final int UP = -1;
  private static final int DOWN = 1;
  private static final int STAY = 0;

  List<List<Character>> grid = new ArrayList<>();
  int gridHeight;
  int gridWidth;

  public DayResult call() throws IOException {
    String fileName = "advent2020/day11/input.txt";
    extractFromFile(fileName);
    gridHeight = grid.size();
    gridWidth = grid.get(0).size();

    int changes;
    DayResult dayResult = new DayResult("Seating System",0,0);

    do {
      changes = nextGen(4, 1);
    }
    while (changes > 0);
    dayResult.setPart1(getOccupiedCount());

    // rebuild grid
    grid = new ArrayList<>();
    extractFromFile(fileName);
    do {
      changes = nextGen(5, INFINITE_DISTANCE);
    }
    while (changes > 0);
    dayResult.setPart2(getOccupiedCount());

    return dayResult;
  }

  public void printGrid() {
    for (List<Character> characters : grid) {
      characters.forEach(System.out::print);
      System.out.println();
    }
    System.out.println();
  }

  public void extractFromFile(String fileName) throws FileNotFoundException {
    File file = Util.getFile(fileName);

    Scanner scanner = new Scanner(file);
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        List<Character> row = line.chars().mapToObj((i) -> (char) i).collect(Collectors.toList());
        grid.add(row);
      } else {
        scanner.next();
      }
    }
  }

  private boolean isDirectionOccupied(int x, int y, int dx, int dy, int distance) {
    int ix = x + dx;
    int iy = y + dy;
    int id = 1;
    while (iy >= 0 && iy < gridHeight && ix >= 0 && ix < gridWidth &&
        (id <= distance || distance == INFINITE_DISTANCE)) {
      char toCheck = grid.get(iy).get(ix);
      if (toCheck == OCCUPIED_SEAT) {
        return true;
      } else if (toCheck == VACANT_SEAT) {
        return false;
      }
      ix = ix + dx;
      iy = iy + dy;
      id++;
    }
    return false;
  }

  private int getOccupiedCount() {
    int occupied = 0;
    for (int y = 0; y < gridHeight; y++) {
      for (int x = 0; x < gridWidth; x++) {
        char current = grid.get(y).get(x);
        if (current == OCCUPIED_SEAT) {
          occupied++;
        }
      }
    }
    return occupied;
  }

  private boolean changeToOccupiedNotPossible(int occupied) {
    return (occupied > 0);
  }

  private boolean changeToVacantInevitable(int seatOccupancyTolerance, int occupied) {
    return (occupied >= seatOccupancyTolerance);
  }

  private int nextGen(int seatOccupancyTolerance, int distanceCheck) {
    List<Change> changes = new ArrayList<>();

    for (int y = 0; y < gridHeight; y++) {
      for (int x = 0; x < gridWidth; x++) {
        char current = grid.get(y).get(x);
        if (current != FLOOR) {
          int occupied = 0;

          // try all 8 directions
          List<Point> deltas = new ArrayList<>(
              List.of(new Point(STAY, UP), new Point(RIGHT, UP), new Point(RIGHT, STAY), new Point(RIGHT, DOWN),
                  new Point(STAY, DOWN), new Point(LEFT, DOWN), new Point(LEFT, STAY), new Point(LEFT, UP)));
          for (Point delta : deltas) {
            if (isDirectionOccupied(x, y, delta.x, delta.y, distanceCheck)) occupied++;
            if (current == VACANT_SEAT && changeToOccupiedNotPossible(occupied)) {
              break;
            }
            if (current == OCCUPIED_SEAT && changeToVacantInevitable(seatOccupancyTolerance, occupied)) {
              break;
            }
          }

          // add change
          if (current == VACANT_SEAT && occupied == 0) {
            changes.add(new Change(x, y, OCCUPIED_SEAT));
          } else if (current == OCCUPIED_SEAT && occupied >= seatOccupancyTolerance) {
            changes.add(new Change(x, y, VACANT_SEAT));
          }
        }
      }
    }

    // apply changes
    changes.forEach(c -> grid.get(c.y).set(c.x, c.newChar));

    return changes.size();
  }

  @Data
  static class Change {
    @NonNull
    private int x;
    @NonNull
    private int y;
    @NonNull
    private char newChar;
  }


}
