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
      CompletableFuture<DayResult> i = CompletableFuture.supplyAsync(() -> {
        try {
          DayResult dayResult = day.call();
          System.out.println(day.getClass().getSimpleName() + ": " + dayResult.getDayName() +
              ": " + dayResult.getPart1() + "," + dayResult.getPart2());
          return dayResult;
        } catch (Exception e) {
          throw new IllegalStateException(e);
        }
      });
      futures.add(i);
    }

    // wait for all threads to finish
    CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

    long endTime = System.nanoTime();
    System.out.println("*** Elapsed time:" + (endTime - startTime) / Math.pow(10, 6) + "ms");
  }

}