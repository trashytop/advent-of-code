package trashytop.adventofcode.advent2020;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// https://adventofcode.com/2020/day/10
public class Day10 implements Day {

  private static final int CHARGING_OUTLET_JOLTS = 0;
  private static final int DEVICE_ADAPTER_JOLTS = 3;
  private static final int MAX_DIFF_JOLTS = 3;

  private List<Integer> adapters;
  int[] differences = new int[MAX_DIFF_JOLTS + 1];
  private final Map<Integer, Long> pathCounts = new HashMap<>();

  public String getName() {
    return "Day 10: Adapter Array";
  }

  public void solve() throws IOException {
    adapters = Util.extractIntegersFromFile("advent2020/day10/input.txt");
    Collections.sort(adapters);

    // add charging outlet and device
    adapters.add(0, CHARGING_OUTLET_JOLTS);
    adapters.add(adapters.get(adapters.size() - 1) + DEVICE_ADAPTER_JOLTS);

    calcDifferences();
    System.out.println("#1: product of differences:" + differences[1] * differences[3]);

    long pathCount = countPaths(adapters);
    System.out.println("#2: total distinct ways:" + pathCount);
  }

  private void calcDifferences() {
    int current = 0;
    for (Integer adapter : adapters) {
      if (adapter <= current + MAX_DIFF_JOLTS) {
        differences[adapter - current]++;
        current = adapter;
      }
    }
  }

  private long countPaths(List<Integer> adapters) {
    // reached the end node in the tree
    if (adapters.size() == 1)
      return 1;

    int index = 1;
    long paths = 0;
    while (index < adapters.size() &&
        adapters.get(index) - adapters.get(0) <= MAX_DIFF_JOLTS) {
      // make new list without lead adapter
      List<Integer> trimmed = adapters.subList(index, adapters.size());
      // get unique hash code for list contents
      Integer hashCode = trimmed.hashCode();

      if (pathCounts.containsKey(hashCode)) {
        // already been down this path so use count
        paths += pathCounts.get(hashCode);
      } else {
        // recurse down path
        long countTrimmedPaths = countPaths(trimmed);
        pathCounts.put(hashCode, countTrimmedPaths);
        paths += countTrimmedPaths;
      }
      index++;
    }
    return paths;
  }

}
