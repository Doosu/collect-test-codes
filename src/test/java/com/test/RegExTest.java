package com.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.utils.Formatter;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class RegExTest {

  @Test
  public void pathReplace() {
    final var file = "file:src/main/resources/static/images";
    final var base = "classpath:/resources/static/images";
    final var replaced = base.replaceAll("^classpath:\\/?|\\/?$", "");

    log.warn("ORIGIN :: {}", base);
    log.warn("REPLACED :: {}", replaced);

    assertTrue(!replaced.endsWith("/"));
  }

  @Test
  public void types() {
    final var types = Arrays.asList("authorizationGrantType", "clientAuthenticationMethodType").stream()
        .collect(Collectors.toMap(type -> type, type -> type));

    log.warn("TYPES :: {}", types);
  }

  @Test
  public void passwordMappedId() {
    final var pw = "{noop}1234";
    final var reg = Pattern.compile("^\\{(\\w)+}");
    log.warn(
        "BASE64 ?? {}",
        Pattern
            .compile("([A-Za-z0-9+/\\-_]{4})*([A-Za-z0-9+/\\-_]{3}=?|[A-Za-z0-9+/\\-_]{2}=?=?)?")
            .matcher("dGVzdAo=").matches()
    );
    log.warn("EMAIL ?? {}", Formatter.EMAIL_PATTERN.matcher("andxlddl@github.com").matches());

    assertTrue(reg.matcher(pw).find());
  }

  @Test
  public void idRegEx() {
//    final var ex = "^(\\w)[\\w\\d\\-_#]+(?:\\.[\\w_\\-#]+)+$";
    final var ex = "^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])[^@!#$%&'*+/=?`{|}~^]*";
    final var id = "andx_^lddl2";

    assertFalse(id.matches(ex));
  }

  @Test
  public void masking() {
    final var username = "andxlddl@github.com";
    final var masked = username.replaceAll("(?<!^.?).(?!.?$)", "*");

    assertTrue(masked.startsWith("an*") && masked.endsWith("*om"));
  }

}
