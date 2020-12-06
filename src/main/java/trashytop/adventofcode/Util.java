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

  // extract Rs from a file containing Ts (Ts split using regex)
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

}
