package trashytop.adventofcode.advent2020;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Data;
import lombok.NonNull;
import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/7
public class Day7 implements Day {

  private List<String> bagLines;
  HashMap<Bag, List<BagRule>> bagRules = new HashMap<>();

  public String getName() {
    return "Day 7: Handy Haversacks";
  }

  public void solve() throws IOException {
    bagLines = Util.extractLinesFromFile("advent2020/day7/input.txt");

    for (String bagLine : bagLines) {
      buildBagRules(bagLine);
    }

    Bag bagToMatch = new Bag("shiny", "gold");
    int matchingBagCount = 0;
    for (Bag bagToCheck : bagRules.keySet()) {
      if (getNumberOfMatchingBags(bagToCheck, bagToMatch) > 0) {
        matchingBagCount++;
      }
    }
    System.out.println("#1: Number of bag colours with at least one shiny gold bag: " + matchingBagCount);

    int insideBagCount = getNumberOfBags(bagToMatch) - 1; // subtract the outer bag
    System.out.println("#1: Number of bags within a single shiny gold bag: " + insideBagCount);
  }

  // return number of matching bags found
  public int getNumberOfMatchingBags(Bag bagToCheck, Bag bagToMatch) {
    return bagRules.get(bagToCheck).stream()
        .mapToInt(rule -> (rule.bag.equals(bagToMatch) ? 1 : getNumberOfMatchingBags(rule.bag, bagToMatch)))
        .sum();
  }

  // return number of bags
  public int getNumberOfBags(Bag bagToCheck) {
    return 1 + bagRules.get(bagToCheck).stream()
        .mapToInt(rule -> rule.count * getNumberOfBags(rule.bag))
        .sum();
  }

  public void buildBagRules(String bagLine) {
    final String BAGS_CONTAIN = "bags contain";
    int outerBagSplit = bagLine.indexOf(BAGS_CONTAIN) + BAGS_CONTAIN.length();
    String outerBagString = bagLine.substring(0, outerBagSplit);
    String contents = bagLine.substring(outerBagSplit);
    Matcher outerBagMatcher = Pattern.compile("([a-z]+) ([a-z]+)").matcher(outerBagString);
    Bag bag = null;
    if (outerBagMatcher.find()) {
      bag = new Bag(outerBagMatcher.group(1), outerBagMatcher.group(2));
    }

    Matcher innerBagGroupMatcher = Pattern.compile("([^,]+)").matcher(contents);
    List<BagRule> rulesForBag = new ArrayList<>();
    while (innerBagGroupMatcher.find()) {
      Matcher innerBagMatcher = Pattern.compile("(\\d+) ([a-z]+) ([a-z]+)").matcher(innerBagGroupMatcher.group());
      if (innerBagMatcher.find()) {
        BagRule bagRule = new BagRule(Integer.parseInt(innerBagMatcher.group(1)),
            new Bag(innerBagMatcher.group(2), innerBagMatcher.group(3)));
        rulesForBag.add(bagRule);
      }
    }
    bagRules.put(bag, rulesForBag);
  }

  @Data
  static class Bag {
    @NonNull
    private String adjective;
    @NonNull
    private String colour;
  }

  @Data
  static class BagRule {
    @NonNull
    private int count;
    @NonNull
    private Bag bag;
  }

}
