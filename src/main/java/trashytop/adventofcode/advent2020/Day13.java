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
    // this algorithm only works for primes, so exit if a non-prime is found in our input
    if (buses.stream().anyMatch(b -> !Util.isPrime(b.id))) return -1;

    // worked example for input of 67,7,x,59,61
    //   LCM for two primes p,q is pxq
    //   t = 335, when t mod 67 == 0 and (t+1) mod 7 == 0, using increment of 67
    //   t = 16281, when (t+3) mod 59 == 0, using increment of 335
    //   t = 1261476, when (t+4) mod 61 == 0, using increment of 16281

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
