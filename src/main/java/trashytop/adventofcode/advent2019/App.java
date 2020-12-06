package trashytop.adventofcode.advent2019;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import trashytop.adventofcode.Day;

public class App {

  public static void main(String[] args) {
    List<Day> days = new ArrayList<>(
        //List.of(new Day2()));
        List.of(new Day2()));
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
