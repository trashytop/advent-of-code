package trashytop.adventofcode.advent2021;

import trashytop.adventofcode.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App {

  public static void main(String[] args) {
    List<Day> days = new ArrayList<>(
        //List.of(new Day1(), new Day2(), new Day3(), new Day4()));
        List.of(new Day1()));

    solve(days);
  }

  private static void solve(List<Day> days) {
    days.forEach(day -> {
      try {
        solve(day);
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
