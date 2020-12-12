package trashytop.adventofcode;

import lombok.Data;
import lombok.NonNull;

@Data
public class DayResult {
  @NonNull
  private String dayName;
  @NonNull
  private long part1;
  @NonNull
  private long part2;
}
