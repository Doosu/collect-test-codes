package com.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class StreamGroupingTest {

  @Test
  public void 리스트_값_그룹핑_하기_SUM_JOIN() {
    final var list = Arrays.asList(
        new Foo(1L, "A", 3),
        new Foo(1L, "B", 2),
        new Foo(3L, "a", 2),
        new Foo(3L, "b", 1),
        new Foo(2L, "Z", 2)
    );

    final var grouped = StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(
            list.stream().collect(Collectors.groupingBy(Foo::getId)).entrySet().iterator(),
            Spliterator.ORDERED
        ),
        false
    ).map(entry -> {
      final var fooGroup = entry.getValue();

      return new Foo(
          entry.getKey(),
          fooGroup.stream().map(Foo::getProperty).collect(Collectors.joining(",")),
          fooGroup.stream().mapToInt(Foo::getCnt).sum()
      );
    }).collect(Collectors.toList());

    log.warn("GROUPED :: {}", grouped);

    assertTrue(5 == grouped.get(0).getCnt());
    assertTrue(3 == grouped.get(2).getCnt());
  }

  @Getter
  @AllArgsConstructor
  public class Foo {
    private Long id;
    private String property;
    private int cnt;
    @Override
    public String toString() {
      return String.format("ID: %d, %s, %d", id, property, cnt);
    }
  }

}