package trashytop.adventofcode.advent2020;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/6
public class Day6 implements Day {

  private int countEveryone = 0;
  private int countAnyone = 0;
  private int countPeopleInCurrentGroup = 0;

  public String getName() {
    return "Day 6: Custom Customs";
  }

  public void solve() throws IOException {
    File file = Util.getFile("advent2020/day6/input.txt");

    // build answers from file
    Scanner scanner = new Scanner(file);
    HashMap<Character, Integer> answers = new HashMap<>();

    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.equals("")) {
          for (int i = 0; i < line.length(); i++) {
            Character c = line.charAt(i);
            if (answers.containsKey(c)) {
              // repeat answer
              answers.put(c, answers.get(c) + 1);
            } else {
              // first answer
              answers.put(c, 1);
            }
          }
          countPeopleInCurrentGroup++;
        } else {
          processGroup(answers);
          // clear down ready for next group in file
          answers = new HashMap<>();
          countPeopleInCurrentGroup = 0;
        }
      } else {
        scanner.next();
      }
    }
    // process last group
    processGroup(answers);

    System.out.println("#1: Sum anyone:" + countAnyone);
    System.out.println("#2: Sum everyone:" + countEveryone);
  }

  private void processGroup(HashMap<Character, Integer> answers) {
    for (Map.Entry<Character, Integer> entry : answers.entrySet()) {
      int answerCount = entry.getValue();
      if (answerCount > 0) {
        countAnyone++;
        if (answerCount == countPeopleInCurrentGroup) {
          countEveryone++;
        }
      }
    }
  }

  private void processGroupAlternative(HashMap<Character, Integer> answers) {
    countEveryone += answers.entrySet().stream().filter(x -> x.getValue() == countPeopleInCurrentGroup).count();
    countAnyone += answers.entrySet().stream().filter(x -> x.getValue() > 0).count();
  }

}
