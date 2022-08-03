package com.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class VolatileAccessTest {

  private static volatile Map MAP = new HashMap<String, String>();
  private static final Map RMAP = new HashMap<String, String>();
  private static final int MAX_SIZE = 1500;
  private static final Random random = new Random();

  private static boolean normalTerminate = false;
  private static boolean volatileTerminate = false;

  @Test
  public void stress() {
    final var stressCount = 10000000;
    final var keys = IntStream.range(0, MAX_SIZE)
        .mapToObj(i -> UUID.randomUUID().toString()).collect(Collectors.toList());

    final var normalWatch = new StopWatch();
    final var volatileWatch = new StopWatch();

    volatileWatch.start();
    Flux.fromStream(IntStream.range(1, stressCount).boxed())
        .parallel()
        .runOn(Schedulers.newParallel("VOLATILE", 20))
        .doOnNext(
            i -> {
              final var key = keys.get(random.nextInt(MAX_SIZE));
              MAP.put(key, UUID.randomUUID().toString());
            }
        ).then().subscribe(
            null, null,
            () -> {
              volatileWatch.stop();
              log.warn("VOLATILE TIME :: {}", volatileWatch.getTotalTimeMillis() / 1000F);
              volatileTerminate = true;
            }
        );

    normalWatch.start();
    Flux.fromStream(IntStream.range(1, stressCount).boxed())
        .parallel()
        .runOn(Schedulers.newParallel("NORMAL", 20))
        .doOnNext(
            i -> {
              final var key = keys.get(random.nextInt(MAX_SIZE));
              RMAP.put(key, UUID.randomUUID().toString());
            }
        ).then().subscribe(
            null, null,
            () -> {
              normalWatch.stop();
              log.warn("NORMAL TIME :: {}", normalWatch.getTotalTimeMillis() / 1000F);
              normalTerminate = true;
            }
        );

    while (!volatileTerminate || !normalTerminate) {
      try {
        Thread.sleep(1000L);
      } catch (InterruptedException e) {}
    }
  }

}
