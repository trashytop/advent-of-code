package trashytop.adventofcode.advent2020;

import lombok.Value;
import trashytop.adventofcode.InstructionUtil;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;

import java.io.IOException;
import java.util.List;

// https://adventofcode.com/2020/day/8
public class Day8 implements Day {

  private static final String NOP = "NOP";
  private static final String JMP = "JMP";
  private static final String ACC = "ACC";

  private List<InstructionUtil.Instruction> program;

  public DayResult call() throws IOException {
    program = InstructionUtil.extractInstructionsFromFile("advent2020/day8/input.txt",
      s -> {
        String[] instruction = s.split(" ");
        return new InstructionUtil.Instruction(instruction[0], Integer.parseInt(instruction[1]));
      });

    DayResult dayResult = new DayResult("Handheld Halting", 0, 0);

    dayResult.setPart1(runProgram().accumulator);

    int op = 0;
    InstructionUtil.Instruction oldInstruction;
    while (op < program.size()) {
      InstructionUtil.Instruction instruction = get(op);
      if (!ACC.equals(instruction.operator)) {
        oldInstruction = instruction;
        if (NOP.equals(instruction.operator)) {
          set(op, new InstructionUtil.Instruction(JMP, instruction.operand));
        } else {
          set(op, new InstructionUtil.Instruction(NOP, instruction.operand));
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
      InstructionUtil.Instruction instruction = get(op);
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

  private InstructionUtil.Instruction get(int index) {
    return program.get(index);
  }

  private void set(int index, InstructionUtil.Instruction instruction) {
    program.set(index, instruction);
  }

  private void clearExecutedFlags() {
    program.forEach((instruction) -> instruction.executed = false);
  }

  @Value
  static class ProgramResult {
    int accumulator;
    boolean instructionWasRepeated;
  }

}
