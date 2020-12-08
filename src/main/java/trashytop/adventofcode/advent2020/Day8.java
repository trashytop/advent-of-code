package trashytop.adventofcode.advent2020;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/8
public class Day8 implements Day {

  private List<Instruction> program;

  public String getName() {
    return "Day 8: Handheld Halting";
  }

  public void solve() throws IOException {
    program = extractInstructionsFromFile("advent2020/day8/input.txt");

    ProgramResult result = runProgram();
    System.out.println("#1: value of accumulator:" + result.accumulator);

    int op = 0;
    Instruction oldInstruction;
    while (op < program.size()) {
      Instruction instruction = get(op);
      if (instruction.operator != "acc") {
        oldInstruction = instruction;
        if (instruction.operator == "nop") {
          set(op, new Instruction("jmp", instruction.operand));
        } else {
          set(op, new Instruction("nop", instruction.operand));
        }
        result = runProgram();
        if (!result.repeat) {
          System.out.println("#2: value of accumulator:" + result.accumulator);
          break;
        }
        // restore program
        set(op, oldInstruction);
        clearExecutedFlags();
      }
      op++;
    }
  }

  private ProgramResult runProgram() {
    int accumulator = 0;
    int op = 0;
    while (true) {
      if (op == program.size()) {
        return new ProgramResult(accumulator, false);
      }
      Instruction instruction = get(op);
      if (instruction.executed) {
        return new ProgramResult(accumulator, true);
      }
      switch (instruction.operator) {
        case "nop":
          op++;
          break;
        case "acc":
          accumulator = accumulator + instruction.operand;
          op++;
          break;
        case "jmp":
          op = op + instruction.operand;
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      instruction.executed = true;
    }
  }

  private Instruction get(int index) {
    return program.get(index);
  }

  private void set(int index, Instruction instruction) {
    program.set(index, instruction);
  }

  private void clearExecutedFlags() {
    for (Instruction instruction1 : program) {
      instruction1.executed = false;
    }
  }

  public List<Instruction> extractInstructionsFromFile(String fileName) throws FileNotFoundException {
    File file = Util.getFile(fileName);

    Scanner scanner = new Scanner(file);
    List<Instruction> instructions = new ArrayList<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        String[] instruction = line.split(" ");
        instructions.add(new Instruction(instruction[0], Integer.parseInt(instruction[1])));
      } else {
        scanner.next();
      }
    }
    return instructions;
  }

  @Data
  static class Instruction {
    @NonNull
    private String operator;
    @NonNull
    private int operand;
    private boolean executed;
  }

  @Data
  static class ProgramResult {
    @NonNull
    private int accumulator;
    @NonNull
    private boolean repeat;
  }

}
