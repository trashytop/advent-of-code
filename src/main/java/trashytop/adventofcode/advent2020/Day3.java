package trashytop.adventofcode.advent2020;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

import java.io.IOException;
import java.util.List;

// https://adventofcode.com/2020/day/3
public class Day3 implements Day {

  private List<String> lines;
  private int gridWidth;

  public String getName() {
    return "Day 3: Toboggan Trajectory";
  }

  public void solve() throws IOException {
    lines = Util.buildArrayOfLinesFromFile("advent2020/day3/input.txt");
    gridWidth = lines.get(0).length();

    int right1_down1 = calcNumberOfTreesHit(1, 1);
    int right3_down1 = calcNumberOfTreesHit(3, 1);
    int right5_down1 = calcNumberOfTreesHit(5, 1);
    int right7_down1 = calcNumberOfTreesHit(7, 1);
    int right1_down2 = calcNumberOfTreesHit(1, 2);
    System.out.println("#1:" + right3_down1);
    System.out.println("#2:" + right1_down1 * right3_down1 * right5_down1 * right7_down1 * right1_down2);
  }

  public int calcNumberOfTreesHit(int right, int down) {
    int treeCount = 0;
    int x = 1;
    int y = 1;
    while (y <= lines.size()) {
      if (isTree(x, y)) {
        treeCount++;
      }
      x = x + right;
      y = y + down;
    }
    return treeCount;
  }

  public boolean isTree(int x, int y) {
    return (lines.get(y - 1).charAt((x - 1) % gridWidth) == '#');
  }

}
