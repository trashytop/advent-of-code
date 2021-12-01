package trashytop.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

  public void printList(List list) {
    list.forEach(System.out::println);
  }

  public void printListWithCommas(List list) {
    list.forEach(s -> System.out.print(s + ","));
  }

  public void printMap(HashMap<String, String> map) {
    map.forEach((key, value) -> System.out.println(key + ":" + value));
  }

  public List<Integer> extractIntegersFromFile(String fileName) throws FileNotFoundException {
    File file = getFile(fileName);

    Scanner scanner = new Scanner(file);
    List<Integer> numbers = new ArrayList<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextInt()) {
        numbers.add(scanner.nextInt());
      } else {
        scanner.next();
      }
    }
    return numbers;
  }

  public List<String> extractLinesFromFile(String fileName) throws FileNotFoundException {
    File file = getFile(fileName);

    Scanner scanner = new Scanner(file);
    List<String> lines = new ArrayList<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        lines.add(scanner.nextLine());
      } else {
        scanner.next();
      }
    }
    return lines;
  }

  // extract Rs from a file containing something that can be split using regex
  public <R> List<R> extractGenericFromFile(String fileName, String splitRegex, Function<String, R> mapper) throws FileNotFoundException {
    File file = getFile(fileName);

    // build answers from file
    Scanner scanner = new Scanner(file);
    List<R> all = null;
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        List<R> partial = Arrays.stream(line.split(splitRegex))
          .map(mapper)
          .collect(Collectors.toList());
        if (all == null) {
          all = partial;
        } else {
          all.addAll(partial);
        }
      } else {
        scanner.next();
      }
    }
    return all;
  }

  public File getFile(String fileName) {
    ClassLoader classLoader = Util.class.getClassLoader();
    return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
  }

  public boolean isPrime(int n) {
    //check if n is a multiple of 2
    if (n % 2 == 0) return false;
    //if not, then just check the odds
    for (int i = 3; i * i <= n; i += 2) {
      if (n % i == 0)
        return false;
    }
    return true;
  }

  public int calcCountOfIncreasingValues(List<Integer> values) {
    int prev = Integer.MAX_VALUE; // ignore first measurement
    int count = 0;
    for (Integer value : values) {
      if (value > prev) {
        count++;
      }
      prev = value;
    }
    return count;
  }

  public List<Integer> calcSlidingWindowSums(int windowLen, List<Integer> values) {
    List<Integer> sums = new ArrayList<>();
    for (int valueIndex = 0; valueIndex < values.size(); valueIndex++) {
      if (valueIndex <= values.size() - windowLen) {
        int sum = 0;
        for (int windowIndex = 0; windowIndex < windowLen; windowIndex++) {
          sum += values.get(valueIndex + windowIndex);
        }
        sums.add(sum);
      }
    }
    return sums;
  }

}
