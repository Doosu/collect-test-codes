package com.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.utils.Formatter;
import com.test.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class PasswordPatternTest {

  @Test
  public void matchedRulePassword() {
    final var password = "Wide123(정)aa115655***";

    final var matchedRule = Formatter.PASSWORD_RULE_PATTERN.matcher(password).matches();
    final var repeated = Formatter.REPEAT_CHAR_PATTERN.matcher(password).find();

    log.warn("RULE TEST :: {}", matchedRule);
    log.warn("REPEAT TEST :: {}", repeated);

    assertTrue(matchedRule && !repeated);
  }

  @Test
  public void repeatLimit() {
    final var string = "12345a6";
    final var five = Utils.isSuccessionAtLimitMore(string, 5);
    final var six = Utils.isSuccessionAtLimitMore(string, 6);

    assertTrue(five);
    assertFalse(six);
  }

}
