package trashytop.adventofcode;

import lombok.Data;
import lombok.NonNull;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;

@UtilityClass
public class InstructionUtil {

  public List<Instruction> extractInstructionsFromFile(String fileName,
                                                       Function<String, Instruction> instructionBuilder) throws FileNotFoundException {
    File file = Util.getFile(fileName);

    Scanner scanner = new Scanner(file);
    List<Instruction> instructions = new ArrayList<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        instructions.add(instructionBuilder.apply(line));
      } else {
        scanner.next();
      }
    }
    return instructions;
  }

  @Data
  public static class Instruction {
    @NonNull
    public String operator;
    final public int operand;
    public boolean executed;
  }
}

