package com.test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class CharacterTest {

  @Test
  public void asciiTest() {
    final var value = 'a';
    log.warn("ASCII :: {}", (int) value);

    assertTrue((int) value == 97);
  }

  @Test void charTest() {
    final var value = 97;
    log.warn("CHAR :: {}", (char) value);

    assertTrue((char) value == 'a');
  }

  @Test
  public void slicing() {
    final var startNum = 97;
    final var range = 25;
    final var list = IntStream.range(0, range)
        .mapToObj(i -> String.valueOf((char) (startNum + i))).collect(Collectors.toList());

    final var startCount = 3;
    final var sliceSize = 4D;
    final var count = (int) Math.ceil((list.size() - startCount) / sliceSize);
    final var arr = IntStream.range(0, count).mapToObj(i -> {
      final var start = ((int) sliceSize * i) + startCount;
      final var end = start + (int) sliceSize;

      return list.subList(start, range < end ? range : end);
    }).collect(Collectors.toList());

    log.warn("ARRAY :: {}", arr);

    assertTrue(arr.size() == count);
  }

  @Test
  public void replaceNullable() {
    final var origin = "TEST asdf test";
    final var word = "test";

    assertThrows(
        NullPointerException.class,
        () -> origin.replace(word, null)
    );

  }

}
