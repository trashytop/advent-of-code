package trashytop.adventofcode.advent2021;

import trashytop.adventofcode.AppUtil;
import trashytop.adventofcode.DayResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class App {

  public static void main(String[] args) {

    List<Callable<DayResult>> days = new ArrayList<>(
//        List.of(
//            new Day1(), new Day2(), new Day3()
//        ));
      List.of(new Day3()));

    AppUtil.solve(days);
  }

}
