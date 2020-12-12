package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trashytop.adventofcode.Day;

public class App {

  public static void main(String[] args) {
    List<Day> days = new ArrayList<>(
        //List.of(new Day1(), new Day2(), new Day3(), new Day4(), new Day5(), new Day6(), new Day7(), new Day8(),
        //    new Day9(), new Day10(), new Day11(), new Day12()));
    List.of(new Day12()));
    solve(days);
  }

  private static void solve(List<Day> days) {
    days.forEach(day -> {
      try {
        long startTime = System.nanoTime();
        solve(day);
        long endTime = System.nanoTime();
        System.out.println("\t" + (endTime - startTime) / Math.pow(10, 6) + "ms");
      } catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  private static void solve(Day day) throws IOException {
    System.out.println(day.getName());
    day.solve();
  }

}
