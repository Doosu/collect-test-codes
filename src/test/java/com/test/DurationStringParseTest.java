package com.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Duration;
import java.time.format.DateTimeParseException;
import org.junit.jupiter.api.Test;

public class DurationStringParseTest {

  @Test
  void durationTest() {
    assertThrows(DateTimeParseException.class, () -> Duration.parse("PD1T24H"));
  }

  @Test
  void durations() {
    parse("PT20S");//T must be at the beginning to time part
    parse("P2D");//2 day
    parse("-P2D");//minus 2 days
    parse("P-2DT-20S");//S for seconds
    parse("PT20H");//H for hours
    parse("PT220H");
    parse("PT20M");//M for minutes
    parse("PT20.3S");//second can be in fraction like 20.3
    parse("P4DT12H20M20.3S");
    parse("P-4DT-12H-20M-20.3S");
    parse("-P4DT12H20M20.3S");
  }

  void parse(String pattern) {
    Duration d = Duration.parse(pattern);
    System.out.println(String.format("Pattern: %s => %d", pattern, d.getSeconds()));
  }

}
