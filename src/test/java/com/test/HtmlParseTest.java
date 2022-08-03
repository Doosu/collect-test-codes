package com.test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.test.utils.Formatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class HtmlParseTest {

  @Test
  public void parseHtml() {
    final var ANCHOR_PATTERN = Pattern.compile(
        "<a[^>]*href\\s*=\\s*[\\\"']?([^>\\\"']+)[\\\"']?[^>]*>(.*)</*a>",
        Pattern.CASE_INSENSITIVE
    );
    final var MARKUP_PATTERN = Pattern.compile(
        "<\\/?(?:h[1-5]|[a-z]+(?:\\:[a-z]+)?)[^>]*>", Pattern.CASE_INSENSITIVE
    );
//    final var html = "<a href='localhost:8091/community/board/2377/view/75' target='_blank'><b>test</b>에 필독자로 지정되었습니다.<br>내용을 꼭 확인해 주세요.</a>";
    final var html ="홍길동님이 새 글을 등록하였습니다.<br/>제목 : test<br/>등록된 내용을 확인해주세요.</br><a href=\"http://localhost:8091/community/board/2377/view/75\">[확인하러가기]</a>";
    final var matcher = ANCHOR_PATTERN.matcher(html);
    final var anchors = new ArrayList<Map<String, String>>();
    final var parsed = Formatter.stripTags(html);

    while (matcher.find()) {
      anchors.add(
          new HashMap<>() {
            {
              put("link", matcher.group(1));
              put("text", Formatter.stripTags(matcher.group(2)));
            }
          }
      );
    }

    log.warn("parsed :: {}", parsed);
    log.warn("anchors :: {}", anchors);

    assertFalse(MARKUP_PATTERN.matcher(parsed).matches());
  }

  @Test
  public void slackMansionTest() {
    final var message = "<@U036SFWRYHH> ping";
    final var regex = "^(<@U[\\d|A-Z]+>\\s?)(.*)$";
    final var realMessage = message.replaceAll(regex, "$2");

    log.warn("MANSION ?? {}", Pattern.compile(regex).matcher(message).matches());
    log.warn("CLEARING :: {}", realMessage);
    log.warn("GOOGLE MANSION :: {}", "@test-bot ping".replaceAll(regex, "$2"));
    log.warn("REPLACE :: {}", "<@U036SFWRYHH> approve WNJ-24953-03 그냥 승인해범".replaceAll(regex, "$2"));
    log.warn("NOT MATCHED :: {}", "reject WNJ-202201024-01 안돼이거 절대안돼이거 무조건안돼!! ASDFASDF".replaceAll(regex, "$2"));

    assertTrue("ping".equals(realMessage));
  }

  @Test
  public void googleMansionTest() {
    final var message = "@test-bot ping";
    final var regex = "^(@[\\w|\\d|\\-_]+\\s?)?(.*)$";
    final var realMessage = message.replaceAll(regex, "$2");

    log.warn("MANSION ?? {}", Pattern.compile(regex).matcher(message).matches());
    log.warn("CLEARING :: {}", realMessage);
    log.warn("SLACK MANSION :: {}", "<@U036SFWRYHH> approve WNJ-24953-03".replaceAll(regex, "$2"));
    log.warn("REPLACE :: {}", "@U036SFWRYHH approve WNJ-24953-03 그냥 승인해범".replaceAll(regex, "$2"));
    log.warn("NOT MATCHED :: {}", "reject WNJ-202201024-01 안돼이거 절대안돼이거 무조건안돼!! ASDFASDF".replaceAll(regex, "$2"));

    assertTrue("ping".equals(realMessage));
  }

}
