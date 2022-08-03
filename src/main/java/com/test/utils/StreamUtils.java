package com.test.utils;

import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public abstract class StreamUtils {

  public static Stream stream(final Object o) {
    if (!Utils.isIterable(o)) return null;

    return Iterable.class.isAssignableFrom(o.getClass())
        ? iterableAsStream((Iterable) o)
        : o.getClass().isArray()
            ? Arrays.stream((Object[]) o)
            : Enumeration.class.isAssignableFrom(o.getClass())
                ? enumerationAsStream((Enumeration) o)
                : iteratorAsStream((Iterator) o);
  }

  public static <T> Stream<T> iterableAsStream(final Iterable<T> e) {
    return StreamSupport.stream(e.spliterator(), false);
  }

  public static <T> Stream<T> iteratorAsStream(final Iterator<T> it) {
    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(it, Spliterator.ORDERED),
        false
    );
  }

  public static <T> Stream<T> enumerationAsStream(final Enumeration<T> e) {
    return StreamSupport.stream(
        Spliterators.spliteratorUnknownSize(
            new Iterator<T>() {
              @Override
              public T next() {
                return e.nextElement();
              }

              @Override
              public boolean hasNext() {
                return e.hasMoreElements();
              }
            },
            Spliterator.ORDERED
        ), false
    );
  }

}
