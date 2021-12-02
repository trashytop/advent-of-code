package trashytop.adventofcode.advent2020;

import lombok.Value;
import trashytop.adventofcode.InstructionUtil;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.DayResult;

import java.awt.*;
import java.io.IOException;
import java.util.List;

// https://adventofcode.com/2020/day/12
public class Day12 implements Day {

  private static final char NORTH = 'N';
  private static final char SOUTH = 'S';
  private static final char EAST = 'E';
  private static final char WEST = 'W';
  private static final char LEFT = 'L';
  private static final char RIGHT = 'R';
  private static final char FORWARD = 'F';

  private List<InstructionUtil.Instruction> program;

  public DayResult call() throws IOException {
    program = InstructionUtil.extractInstructionsFromFile("advent2020/day12/input.txt",
      s -> new InstructionUtil.Instruction(s.substring(0, 1), Integer.parseInt(s.substring(1))));

    ProgramResult result1 = runProgramForPart1();
    ProgramResult result2 = runProgramForPart2();

    return new DayResult("Rain Risk", result1.manhattanDistance, result2.manhattanDistance);
  }

  private char rotateShip90(char currentDirection) {
    if (currentDirection == NORTH) return EAST;
    if (currentDirection == EAST) return SOUTH;
    if (currentDirection == SOUTH) return WEST;
    if (currentDirection == WEST) return NORTH;
    throw new IllegalArgumentException("Illegal direction:" + currentDirection);
  }

  private char rotateShip(char currentDirection, int degrees) {
    if (degrees == 90 || degrees == -270) {
      return rotateShip90(currentDirection);
    } else if (degrees == 180 || degrees == -180) {
      return rotateShip90(rotateShip90(currentDirection));
    } else if (degrees == 270 || degrees == -90) {
      return rotateShip90(rotateShip90(rotateShip90(currentDirection)));
    }
    throw new IllegalArgumentException("Illegal rotation:" + degrees);
  }

  private Waypoint rotateWaypoint90(Waypoint waypoint) {
    Point newPoint = new Point(waypoint.point.y, -waypoint.point.x);
    if (waypoint.NorthOrSouth == NORTH && waypoint.EastOrWest == EAST)
      return new Waypoint(SOUTH, EAST, newPoint);
    if (waypoint.NorthOrSouth == SOUTH && waypoint.EastOrWest == EAST)
      return new Waypoint(SOUTH, WEST, newPoint);
    if (waypoint.NorthOrSouth == SOUTH && waypoint.EastOrWest == WEST)
      return new Waypoint(NORTH, WEST, newPoint);
    if (waypoint.NorthOrSouth == NORTH && waypoint.EastOrWest == WEST)
      return new Waypoint(NORTH, EAST, newPoint);
    throw new IllegalArgumentException("Illegal waypoint:" + waypoint);
  }

  private Waypoint rotateWaypoint(Waypoint waypoint, int degrees) {
    if (degrees == 90 || degrees == -270) {
      return rotateWaypoint90(waypoint);
    } else if (degrees == 180 || degrees == -180) {
      return rotateWaypoint90(rotateWaypoint90(waypoint));
    } else if (degrees == 270 || degrees == -90) {
      return rotateWaypoint90(rotateWaypoint90(rotateWaypoint90(waypoint)));
    }
    throw new IllegalArgumentException("Illegal rotation:" + degrees);
  }

  private ProgramResult runProgramForPart1() {
    Point p = new Point();
    char direction = EAST;
    int op = 0;
    while (true) {
      if (op == program.size()) {
        return new ProgramResult(p, Math.abs(p.x) + Math.abs(p.y));
      }
      InstructionUtil.Instruction instruction = get(op);
      switch (instruction.operator.charAt(0)) {
        case NORTH:
          p.y += instruction.operand;
          break;
        case SOUTH:
          p.y -= instruction.operand;
          break;
        case EAST:
          p.x += instruction.operand;
          break;
        case WEST:
          p.x -= instruction.operand;
          break;
        case LEFT:
          direction = rotateShip(direction, -instruction.operand);
          break;
        case RIGHT:
          direction = rotateShip(direction, instruction.operand);
          break;
        case FORWARD:
          switch (direction) {
            case NORTH:
              p.y += instruction.operand;
              break;
            case SOUTH:
              p.y -= instruction.operand;
              break;
            case EAST:
              p.x += instruction.operand;
              break;
            case WEST:
              p.x -= instruction.operand;
              break;
            default:
              throw new IllegalArgumentException("Illegal operator for FORWARD at " + op + ":" + instruction);
          }
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      op++;
    }
  }

  private ProgramResult runProgramForPart2() {
    Point p = new Point();
    Waypoint waypoint = new Waypoint(NORTH, EAST, new Point(10, 1));
    int op = 0;
    while (true) {
      if (op == program.size()) {
        return new ProgramResult(p, Math.abs(p.x) + Math.abs(p.y));
      }
      InstructionUtil.Instruction instruction = get(op);
      switch (instruction.operator.charAt(0)) {
        case NORTH:
          waypoint.point.y += instruction.operand;
          break;
        case SOUTH:
          waypoint.point.y -= instruction.operand;
          break;
        case EAST:
          waypoint.point.x += instruction.operand;
          break;
        case WEST:
          waypoint.point.x -= instruction.operand;
          break;
        case LEFT:
          waypoint = rotateWaypoint(waypoint, -instruction.operand);
          break;
        case RIGHT:
          waypoint = rotateWaypoint(waypoint, instruction.operand);
          break;
        case FORWARD:
          p.x += instruction.operand * waypoint.point.x;
          p.y += instruction.operand * waypoint.point.y;
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      op++;
    }
  }

  private InstructionUtil.Instruction get(int index) {
    return program.get(index);
  }

  @Value
  static class ProgramResult {
    Point point;
    int manhattanDistance;
  }

  @Value
  static class Waypoint {
    char NorthOrSouth;
    char EastOrWest;
    Point point;
  }

}
