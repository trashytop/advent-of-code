package trashytop.adventofcode.advent2020;

import java.awt.Point;
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

// https://adventofcode.com/2020/day/12
public class Day12 implements Day {

  private static final char NORTH = 'N';
  private static final char SOUTH = 'S';
  private static final char EAST = 'E';
  private static final char WEST = 'W';
  private static final char LEFT = 'L';
  private static final char RIGHT = 'R';
  private static final char FORWARD = 'F';

  private List<Instruction> program;

  public String getName() {
    return "Day 12: Rain Risk";
  }

  public void solve() throws IOException {
    program = extractInstructionsFromFile("advent2020/day12/input.txt");

    ProgramResult result = runProgramForPart1();
    System.out.println("#1: Manhattan distance:" + result.manhattanDistance);

    result = runProgramForPart2();
    System.out.println("#2: Manhattan distance:" + result.manhattanDistance);
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
      Instruction instruction = get(op);
      switch (instruction.action) {
        case NORTH:
          p.y += instruction.value;
          break;
        case SOUTH:
          p.y -= instruction.value;
          break;
        case EAST:
          p.x += instruction.value;
          break;
        case WEST:
          p.x -= instruction.value;
          break;
        case LEFT:
          direction = rotateShip(direction, -instruction.value);
          break;
        case RIGHT:
          direction = rotateShip(direction, instruction.value);
          break;
        case FORWARD:
          switch (direction) {
            case NORTH:
              p.y += instruction.value;
              break;
            case SOUTH:
              p.y -= instruction.value;
              break;
            case EAST:
              p.x += instruction.value;
              break;
            case WEST:
              p.x -= instruction.value;
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
      Instruction instruction = get(op);
      switch (instruction.action) {
        case NORTH:
          waypoint.point.y += instruction.value;
          break;
        case SOUTH:
          waypoint.point.y -= instruction.value;
          break;
        case EAST:
          waypoint.point.x += instruction.value;
          break;
        case WEST:
          waypoint.point.x -= instruction.value;
          break;
        case LEFT:
          waypoint = rotateWaypoint(waypoint, -instruction.value);
          break;
        case RIGHT:
          waypoint = rotateWaypoint(waypoint, instruction.value);
          break;
        case FORWARD:
          p.x += instruction.value * waypoint.point.x;
          p.y += instruction.value * waypoint.point.y;
          break;
        default:
          throw new IllegalArgumentException("Illegal operator at " + op + ":" + instruction);
      }
      op++;
    }
  }

  private Instruction get(int index) {
    return program.get(index);
  }

  public List<Instruction> extractInstructionsFromFile(String fileName) throws FileNotFoundException {
    File file = Util.getFile(fileName);

    Scanner scanner = new Scanner(file);
    List<Instruction> instructions = new ArrayList<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        instructions.add(new Instruction(line.charAt(0), Integer.parseInt(line.substring(1))));
      } else {
        scanner.next();
      }
    }
    return instructions;
  }

  @Data
  static class Instruction {
    @NonNull
    private char action;
    @NonNull
    private int value;
  }

  @Data
  static class ProgramResult {
    @NonNull
    private Point point;
    @NonNull
    private int manhattanDistance;
  }

  @Data
  static class Waypoint {
    @NonNull
    private char NorthOrSouth;
    @NonNull
    private char EastOrWest;
    @NonNull
    private Point point;
  }

}
