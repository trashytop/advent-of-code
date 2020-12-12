package trashytop.adventofcode;

import java.util.concurrent.Callable;


// simple abstract class so we can use days interchangeably
public interface Day extends Callable<DayResult> {}
