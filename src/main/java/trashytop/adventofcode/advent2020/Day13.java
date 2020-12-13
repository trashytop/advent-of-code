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
import java.util.List;
import java.util.OptionalInt;
import java.util.Scanner;

// https://adventofcode.com/2020/day/13
public class Day13 implements Day {
  
  private int earliestDepartTimestamp;
  private final List<Bus> buses = new ArrayList<>();

  public DayResult call() throws IOException {
    extractFromFile("advent2020/day13/input.txt");

    int earliest = findEarliestBus();
    long earliestTimestamp = findEarliestTimestamp();

    return new DayResult("Shuttle Search", earliest, earliestTimestamp);
  }

  public void extractFromFile(String fileName) throws FileNotFoundException {
    File file = Util.getFile(fileName);

    Scanner scanner = new Scanner(file);
    earliestDepartTimestamp = Integer.parseInt(scanner.nextLine());
    String[] busIds = scanner.nextLine().split(",");
    for (int i = 0; i < busIds.length; i++) {
      if (!busIds[i].equals("x")) {
        buses.add(new Bus(Integer.parseInt(busIds[i]), i));
      }
    }
  }

  private int findEarliestBus() {
    int ts = earliestDepartTimestamp;
    while (true) {
      List<Integer> candidates = new ArrayList<>();
      for (Bus bus : buses) {
        if (ts % bus.id == 0) candidates.add(bus.id);
      }
      OptionalInt min = candidates.stream().mapToInt(v -> v).min();
      if (min.isPresent()) {
        return min.getAsInt() * (ts - earliestDepartTimestamp);
      }
      ts++;
    }
  }

  private long findEarliestTimestamp() {
    long ts = 0;
    long inc = buses.get(0).id;
    for (int i = 1; i < buses.size(); i++) {
      long tsNew = buses.get(i).id;
      while (true) {
        ts += inc;
        if ((ts + buses.get(i).offset) % tsNew == 0) {
          inc *= tsNew;
          break;
        }
      }
    }
    return ts;
  }

  @Data
  static class Bus {
    @NonNull
    private int id;
    @NonNull
    private int offset;
  }

}
