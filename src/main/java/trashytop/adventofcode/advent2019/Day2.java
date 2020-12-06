package trashytop.adventofcode.advent2019;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2019/day/2
public class Day2 implements Day {

  private List<Integer> program;

  public String getName() {
    return "Day 2: 1202 Program Alarm";
  }

  public void solve() throws IOException {

    program = Util.extractGenericFromFile("advent2019/day2/input.txt", ",", Integer::parseInt);

    List<Integer> programCopy = program.stream().map(Integer::new).collect(Collectors.toList());

    // reset state to just before the last computer caught fire
    set(1, 12);
    set(2, 2);
    runProgram();
    System.out.println("#1: Value at position 0:" + get(0));

    loopDouble:
    for (int noun = 0; noun <= 99; noun++) {
      for (int verb = 0; verb <= 99; verb++) {
        program = programCopy.stream().map(Integer::new).collect(Collectors.toList());
        set(1, noun);
        set(2, verb);
        runProgram();
        if (get(0) == 19690720) {
          System.out.println("#2: 100 * noun + verb:" + (100 * noun + verb));
          break loopDouble;
        }
      }
    }
  }

  private void runProgram() {
    for (int i = 0; i < program.size(); i = i + 4) {
      switch (get(i)) {
        case 1:
          set(get(i + 3), get(get(i + 1)) + get(get(i + 2)));
          break;
        case 2:
          set(get(i + 3), get(get(i + 1)) * get(get(i + 2)));
          break;
        case 99:
          return;
        default:
          throw new IllegalArgumentException("Illegal opcode at " + i + ":" + get(i));
      }
    }
  }

  private int get(int index) {
    return program.get(index);
  }

  private void set(int index, int value) {
    program.set(index, value);
  }
}
