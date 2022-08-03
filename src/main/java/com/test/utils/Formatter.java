package com.test.utils;

import java.util.regex.Pattern;

public abstract class Formatter {

  public static final String PASSWORD_RULE_STRING = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w|\\d])(.*){8,}";
  public static final String HTTP_URL_STRING = "^(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?(www\\.)?((?:[a-z0-9-]+)(\\.[a-z]{2,})?)";

  public static final Pattern PASSWORD_WITH_MAPPED = Pattern.compile("^\\{(\\w)+}");
  public static final Pattern CAMEL_PATTERN = Pattern.compile("[\\W|_]+(.)");
  public static final Pattern BASE64_PATTERN =
      Pattern.compile("([A-Za-z0-9+/\\-_]{4})*([A-Za-z0-9+/\\-_]{3}=?|[A-Za-z0-9+/\\-_]{2}=?=?)?");
  public static final Pattern EMAIL_PATTERN = Pattern.compile(
      "[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-z0-9-]+\\.)+[a-z]{2,}",
      Pattern.CASE_INSENSITIVE
  );
  public static final Pattern VALID_IMAGE_FILE_NAME_PATTERN =
      Pattern.compile("^([\\w])+\\.(\\w){2,}$", Pattern.CASE_INSENSITIVE);
  public static final Pattern PASSWORD_RULE_PATTERN = Pattern.compile(PASSWORD_RULE_STRING);
  public static final Pattern REPEAT_CHAR_PATTERN = Pattern.compile("(.)\\1\\1");

  public static final Pattern MARKUP_PATTERN = Pattern.compile(
      "<\\/?(?:h[1-5]|[a-z]+(?:\\:[a-z]+)?)[^>]*>",
      Pattern.CASE_INSENSITIVE
  );
  public static final Pattern ANCHOR_PATTERN = Pattern.compile(
      "(<a[^>]*href\\s*=\\s*[\\\"']?([^>\\\"']+)[\\\"']?[^>]*>(.*)<\\/*a>)",
      Pattern.CASE_INSENSITIVE
  );
  public static final Pattern NEWLINE_PATTERN = Pattern.compile(
      "<[^>]*br\\s*\\/*\\s*>", Pattern.CASE_INSENSITIVE
  );

  public final static boolean isBase64String(final String txt) {
    return BASE64_PATTERN.matcher(txt).matches();
  }

  public final static boolean isEmail(final String email) {
    return EMAIL_PATTERN.matcher(email).matches();
  }

  public final static boolean isValidUploadFilename(final String name) {
    return VALID_IMAGE_FILE_NAME_PATTERN.matcher(name).matches();
  }

  public final static String maskingBetweenChars(final String origin) {
    return origin.replaceAll("(?<!^.?).(?!.?$)", "*");
  }

  public final static String stripTags(final String markup) {
    return MARKUP_PATTERN.matcher(stripNewline(markup)).replaceAll("");
  }

  public final static String stripNewline(final String markup) {
    return NEWLINE_PATTERN.matcher(markup).replaceAll("\\\n");
  }

}
