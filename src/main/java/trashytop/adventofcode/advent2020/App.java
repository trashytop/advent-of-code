package trashytop.adventofcode.advent2020;

import trashytop.adventofcode.AppUtil;
import trashytop.adventofcode.DayResult;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class App {

  public static void main(String[] args)  {

    List<Callable<DayResult>> days = new ArrayList<Callable<DayResult>>(
       List.of(
           new Day1(), new Day2(), new Day3(), new Day4(), new Day5(), new Day6(), new Day7(), new Day8(), new Day9(),
           new Day10(), new Day11(), new Day12()
       ));
    //List.of(new Day12()));

    AppUtil.solve(days);
  }

}
