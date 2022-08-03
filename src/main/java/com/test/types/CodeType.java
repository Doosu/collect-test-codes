package com.test.types;

import java.util.stream.Stream;

public interface CodeType {

  String getValue();
  String getCode();
  default String getName() {
    return getCode();
  }

  static <T extends CodeType> T of(final String value, final Class<T> type) {
    if (null == value) return null;

    return Stream.of(type.getEnumConstants())
        .filter(e -> value.equals(e.getValue())).findFirst().orElse(null);
  }

}
