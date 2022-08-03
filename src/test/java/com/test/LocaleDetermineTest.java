package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.RestTemplate;

@Slf4j
public class LocaleDetermineTest {

  private final RestTemplate restTemplate = new RestTemplate();

  @Test
  public void localeText() {
    log.warn("KOREA :: {}", Locale.KOREA.toLanguageTag());
    log.warn("KOREAN :: {}", Locale.KOREAN.toLanguageTag());
    log.warn("JAPAN :: {}", Locale.JAPAN.toLanguageTag());

    log.warn("KOREAN toString :: {}", Locale.KOREAN.toString());

    assertEquals(Locale.KOREA.toLanguageTag(), "ko-KR");
  }

  @Test
  public void localeEquals() {
    final var nullCale = new Locale("jq");
    final var emptyCale = new Locale("");
    final var a = (String) null;

    log.warn("A : {}", a);
    log.warn("NULL : {}", nullCale.toLanguageTag());
    log.warn("EMPTY : {}", emptyCale.toLanguageTag());

    log.warn("for language :: {}", Locale.forLanguageTag("ja").toLanguageTag());
    log.warn("default :: {}", Locale.getDefault());



    assertTrue(nullCale.equals(emptyCale));
  }

  @Test
  public void testForChangeAcceptLanguageAndNonParam() {
    final var response = restTemplate.exchange(
        "http://localhost:9000/clause/test/locales",
        HttpMethod.GET,
        new HttpEntity<>(new HttpHeaders() {
          {
            add("Accept-Language", "ko_KR");
          }
        }),
        Map.class
    );

    log.warn("STATUS :: {}", response.getStatusCode());
    log.warn("HEADERS :: {}", response.getHeaders());
    log.warn("RESPONSE : {}", response.getBody());

    assertTrue(response.getStatusCode() == HttpStatus.OK);
    assertTrue(response.getBody().get("holder").equals(Locale.KOREA));
    assertTrue(response.getBody().get("request").equals(new Locale("")));
  }

}
