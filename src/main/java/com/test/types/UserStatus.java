package com.test.types;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserStatus implements CodeType {

  STABLE("1"),
  UNVERIFIED("2"),
  DISAGREED("4"),
  CREDENTIALS_EXPIRED("5"),
  LOCKED("6"),
  EXPIRED("8"),
  DISABLED("9");

  @Getter
  private final String value;

  public String getCode() {
    return super.name();
  }

  public static UserStatus of(final String value) {
    return Arrays.stream(values())
        .filter(status -> value.equals(status.getValue()) || value.equals(status.getCode()))
        .findFirst()
        .orElse(null);
  }

}