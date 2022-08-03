package com.test.utils;

import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Utils {

  /**
   * 전달된 객체가 비어있는지 검사합니다.
   * @param obj
   * @return
   */
  public static boolean isEmpty(final Object obj) {
    return null == obj
        || (obj instanceof String && "".equals(((String) obj).trim()))
        || (obj instanceof List && ((List<?>) obj).isEmpty())
        || (obj instanceof Map && ((Map<?,?>) obj).isEmpty())
        || (obj instanceof Long && 0L == (Long) obj)
        || (obj instanceof Integer && 0 == (Integer) obj)
        || (obj instanceof Array || obj instanceof Object[] && 0 >= Array.getLength(obj));
  }

  /**
   * 주어진 객체가 반복 가능한 객체인지 검사합니다.
   * @param o
   * @return
   */
  public static boolean isIterable(final Object o) {
    return null != o && (
        o.getClass().isArray() ||
            Iterable.class.isAssignableFrom(o.getClass()) ||
            Iterator.class.isAssignableFrom(o.getClass()) ||
            Enumeration.class.isAssignableFrom(o.getClass())
    );
  }

  /**
   * 주어진 문자열에서 (limit 이상) 연속된 문자 혹은 숫자가 있는지 검사합니다.
   * @param input
   * @return
   */
  public static boolean isSuccessionAtThreeTimeMore(final String input) {
    return isSuccessionAtLimitMore(input, 3);
  }
  public static boolean isSuccessionAtLimitMore(final String input, final int limit) {
    if (null == input || limit > input.length()) return false;

    int prev = 0, prevDiff = 0, diff = 0, count = 0;

    for (int i = 0;i < input.length();i++) {
      final var letter = input.charAt(i);

      if (i > 0 && (diff = prev - letter) > -2 && (count += diff == prevDiff ? 1 : 0) > limit - 3)
        return true;

      prev = letter;
      prevDiff = diff;
    }

    return false;
  }

}
