package com.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.types.UserStatus;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CompareTest {

  @Test
  public void compareEnumStatus() {
    log.warn(
        "DIFF :: {} | {} | {}",
        UserStatus.UNVERIFIED.getValue(),
        UserStatus.STABLE.getValue(),
        UserStatus.UNVERIFIED.compareTo(UserStatus.STABLE)
    );

    assertTrue(0 < UserStatus.UNVERIFIED.compareTo(UserStatus.STABLE));
  }

  @Test
  public void compare() {
    log.warn("equals : {}", "1".compareTo("1"));
    log.warn("crescendo : {}", "2".compareTo("5"));
    log.warn("decrescendo : {}", "4".compareTo("1"));

    assertTrue(0 < "3".compareTo("1"));
  }

  @Test
  public void enumCompare() {
    log.warn("EQUALS :: {}", UserStatus.STABLE.compareTo(UserStatus.STABLE));
    log.warn("CRESCENDO : {}", UserStatus.STABLE.compareTo(UserStatus.EXPIRED));
    log.warn("DECRESCENDO : {}", UserStatus.CREDENTIALS_EXPIRED.compareTo(UserStatus.UNVERIFIED));

    log.warn("TEST :: {}", UserStatus.UNVERIFIED.compareTo(UserStatus.STABLE));
    log.warn("TEST 1 :: {}", UserStatus.STABLE.compareTo(UserStatus.UNVERIFIED));
    log.warn("TEST 2 :: {}", UserStatus.STABLE.compareTo(UserStatus.DISAGREED));
    log.warn("TEST 3 :: {}", UserStatus.UNVERIFIED.compareTo(UserStatus.UNVERIFIED));
    log.warn("TEST 4 :: {}", UserStatus.UNVERIFIED.compareTo(UserStatus.DISAGREED));

    assertTrue(true);
  }

  @Test
  public void userDisableStatus() {
    log.warn(
        "COMPARE :: {}", UserStatus.DISAGREED.compareTo(UserStatus.CREDENTIALS_EXPIRED)
    );
    log.warn(
        "DISAGREE :: {}", UserStatus.DISAGREED.compareTo(UserStatus.DISAGREED)
    );
    log.warn(
        "UNVERIFIED :: {}", UserStatus.DISAGREED.compareTo(UserStatus.UNVERIFIED)
    );

    assertTrue(0 <= UserStatus.DISAGREED.compareTo(UserStatus.STABLE));
  }

  @Test
  public void compareAnnotation() throws Exception {
    final var method = getClass().getMethod("compareAnnotation");
    final var annotation = method.getAnnotation(Test.class);

    assertFalse(Objects.equals(Test.class, annotation));
  }

}
