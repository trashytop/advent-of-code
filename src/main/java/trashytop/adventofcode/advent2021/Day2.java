package trashytop.adventofcode.advent2021;

import lombok.Value;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;
import trashytop.adventofcode.InstructionUtil;
import trashytop.adventofcode.InstructionUtil.Instruction;

import java.io.IOException;
import java.util.List;

// https://adventofcode.com/2021/day/2
public class Day2 implements Day {

  private static final String FORWARD = "forward";
  private static final String DOWN = "down";
  private static final String UP = "up";
  private List<Instruction> program;

  public DayResult call() throws IOException {

    program = InstructionUtil.extractInstructionsFromFile("advent2021/day2/input.txt",
      s -> {
        String[] instruction = s.split(" ");
        return new Instruction(instruction[0], Integer.parseInt(instruction[1]));
      });

    ProgramResult result = runProgram();
    ProgramResultWithAim resultWithAim = runProgramWithAim();

    return new DayResult("Dive!",
      (long) result.horizontalPosition * result.depth,
      (long) resultWithAim.horizontalPosition * resultWithAim.depth);
  }

  private Instruction get(int index) {
    return program.get(index);
  }

  private ProgramResult runProgram() {
    int horizontalPosition = 0;
    int depth = 0;
    int op = 0;
    while (true) {
      if (op == program.size()) {
        return new ProgramResult(horizontalPosition, depth);
      }
      Instruction instruction = get(op);
      switch (instruction.operator) {
        case FORWARD:
          horizontalPosition += instruction.operand;
          op++;
          break;
        case DOWN:
          depth += instruction.operand;
          op++;
          break;
        case UP:
          depth -= instruction.operand;
          op++;
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      instruction.executed = true;
    }
  }

  private ProgramResultWithAim runProgramWithAim() {
    int horizontalPosition = 0;
    int depth = 0;
    int aim = 0;
    int op = 0;
    while (true) {
      if (op == program.size()) {
        return new ProgramResultWithAim(horizontalPosition, depth, aim);
      }
      Instruction instruction = get(op);
      switch (instruction.operator) {
        case FORWARD:
          horizontalPosition += instruction.operand;
          depth += aim * instruction.operand;
          op++;
          break;
        case DOWN:
          aim += instruction.operand;
          op++;
          break;
        case UP:
          aim -= instruction.operand;
          op++;
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      instruction.executed = true;
    }
  }

  @Value
  static class ProgramResult {
    int horizontalPosition;
    int depth;
  }

  @Value
  static class ProgramResultWithAim {
    int horizontalPosition;
    int depth;
    int aim;
  }
}
