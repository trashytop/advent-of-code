package trashytop.adventofcode.advent2019;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import trashytop.adventofcode.AppUtil;
import trashytop.adventofcode.DayResult;

public class App {

  public static void main(String[] args)  {

    List<Callable<DayResult>> days = new ArrayList<>(
        List.of(
            new Day2()
        ));
    //List.of(new Day1()));

    AppUtil.solve(days);
  }

}
