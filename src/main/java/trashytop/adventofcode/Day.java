package trashytop.adventofcode;

import java.io.IOException;

// simple interface so we can use days interchangeably
public interface Day {
  String getName();

  void solve() throws IOException;
}
