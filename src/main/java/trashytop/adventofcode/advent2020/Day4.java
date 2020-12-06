package trashytop.adventofcode.advent2020;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import trashytop.adventofcode.Day;
import trashytop.adventofcode.Util;

// https://adventofcode.com/2020/day/4
public class Day4 implements Day {

  private int requiredFieldsPassportCount = 0;
  private int validPassportCount = 0;

  public String getName() {
    return "Day 4: Passport Processing";
  }

  public void solve() throws IOException {
    File file = Util.getFile("advent2020/day4/input.txt");

    // build passports from file
    Scanner scanner = new Scanner(file);
    HashMap<String, String> passport = new HashMap<>();
    while (scanner.hasNext()) {
      if (scanner.hasNextLine()) {
        String line = scanner.nextLine();
        if (!line.equals("")) {
          String[] keyVals = line.split(" ");
          buildPassport(passport, keyVals);
        } else {
          processPassport(passport);
          // clear down ready for next passport in file
          passport = new HashMap<>();
        }
      } else {
        scanner.next();
      }
    }
    // process last passport
    processPassport(passport);

    System.out.println("#1: Valid passport count:" + requiredFieldsPassportCount);
    System.out.println("#2: Valid passport count:" + validPassportCount);
  }

  public void processPassport(HashMap<String, String> passport) {
    if (hasRequiredFieldsPassport(passport)) {
      requiredFieldsPassportCount++;
    }
    if (isValidPassport(passport)) {
      validPassportCount++;
    }
  }

  public void buildPassport(HashMap<String, String> passport, String[] keyVals) {
    for (String keyVal : keyVals) {
      String[] parts = keyVal.split(":", 2);
      passport.put(parts[0], parts[1]);
    }
  }

  public boolean hasRequiredFieldsPassport(HashMap<String, String> passport) {
    return (passport.containsKey("byr") &&
        passport.containsKey("iyr") &&
        passport.containsKey("eyr") &&
        passport.containsKey("hgt") &&
        passport.containsKey("hcl") &&
        passport.containsKey("ecl") &&
        passport.containsKey("pid"));
  }

  public boolean isValidPassport(HashMap<String, String> passport) {
    try {
      int byr = Integer.parseInt(passport.get("byr"));
      if (byr < 1920 || byr > 2002) {
        return false;
      }

      int iyr = Integer.parseInt(passport.get("iyr"));
      if (iyr < 2010 || iyr > 2020) {
        return false;
      }

      int eyr = Integer.parseInt(passport.get("eyr"));
      if (eyr < 2020 || eyr > 2030) {
        return false;
      }

      Matcher matcher = Pattern.compile("^([0-9]+)(in|cm)$").matcher(passport.get("hgt"));
      matcher.find();
      int height = Integer.parseInt(matcher.group(1));
      if (matcher.group(2).equals("in")) {
        if (height < 59 || height > 76) {
          return false;
        }
      }
      if (matcher.group(2).equals("cm")) {
        if (height < 150 || height > 193) {
          return false;
        }
      }

      matcher = Pattern.compile("^#[0-9a-f]{6}$").matcher(passport.get("hcl"));
      if (!matcher.find()) {
        return false;
      }

      matcher = Pattern.compile("^(amb|blu|brn|gry|grn|hzl|oth)$").matcher(passport.get("ecl"));
      if (!matcher.find()) {
        return false;
      }

      matcher = Pattern.compile("^[0-9]{9}$").matcher(passport.get("pid"));
      if (!matcher.find()) {
        return false;
      }
    } catch (Exception e) {
      return false;
    }

    return true;
  }

}
