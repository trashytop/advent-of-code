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
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/8
public class Day8 implements Day {

  private static final String NOP = "NOP";
  private static final String JMP = "JMP";
  private static final String ACC = "ACC";

  private List<Instruction> program;

  public DayResult call() throws IOException {
    program = extractInstructionsFromFile("advent2020/day8/input.txt");

    DayResult dayResult = new DayResult("Handheld Halting", 0,0);

    dayResult.setPart1(runProgram().accumulator);

    int op = 0;
    Instruction oldInstruction;
    while (op < program.size()) {
      Instruction instruction = get(op);
      if (!ACC.equals(instruction.operator)) {
        oldInstruction = instruction;
        if (NOP.equals(instruction.operator)) {
          set(op, new Instruction(JMP, instruction.operand));
        } else {
          set(op, new Instruction(NOP, instruction.operand));
        }
        ProgramResult result = runProgram();
        if (!result.instructionWasRepeated) {
          dayResult.setPart2(result.accumulator);
          break;
        }
        // restore program
        set(op, oldInstruction);
        clearExecutedFlags();
      }
      op++;
    }

    return dayResult;
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
      switch (instruction.operator.toUpperCase()) {
        case NOP:
          op++;
          break;
        case ACC:
          accumulator = accumulator + instruction.operand;
          op++;
          break;
        case JMP:
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
    program.forEach((instruction) -> instruction.executed = false);
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
    private boolean instructionWasRepeated;
  }

}
