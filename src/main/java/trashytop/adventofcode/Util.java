package trashytop.adventofcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Util {

  public void printList(List list) {
    list.forEach(System.out::println);
  }

  public void printMap(HashMap<String, String> map) {
    map.forEach((key, value) -> System.out.println(key + ":" + value));
  }

  public List<Integer> buildArrayOfIntegersFromFile(String fileName) throws FileNotFoundException {
    File file = getFile(fileName);

    // build array of integers from file
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

  public List<String> buildArrayOfLinesFromFile(String fileName) throws FileNotFoundException {
    File file = getFile(fileName);

    // build array of lines from file
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

  public File getFile(String fileName) {
    ClassLoader classLoader = Util.class.getClassLoader();
    return new File(Objects.requireNonNull(classLoader.getResource(fileName)).getFile());
  }

}
