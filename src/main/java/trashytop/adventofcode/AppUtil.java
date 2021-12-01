package trashytop.adventofcode;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

@UtilityClass
public class AppUtil {

  public static void solve(List<Callable<DayResult>> days) {
    long startTime = System.nanoTime();

    List<CompletableFuture<DayResult>> futures = new ArrayList<>();
    for (Callable<DayResult> day : days) {
      // run in separate thread immediately
      CompletableFuture<DayResult> future = CompletableFuture.supplyAsync(() -> {
        try {
          DayResult dayResult = day.call();
          printResult(day, dayResult);
          return dayResult;
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      });
      futures.add(future);
    }

    // wait for all threads to finish
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    long endTime = System.nanoTime();
    System.out.println("*** Elapsed time:" + (endTime - startTime) / Math.pow(10, 6) + "ms");
  }

  public static void printResult(Callable<DayResult> day, DayResult dayResult) {
    System.out.println(day.getClass().getSimpleName() + ": " + dayResult.getDayName() +
      ": " + dayResult.getPart1() + "," + dayResult.getPart2());
  }

}