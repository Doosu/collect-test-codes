package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

@Slf4j
public class WebFluxParallelTest {

  private final Random random = new Random();
  private final Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
  private final LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue(100);

  @Test
  public void publisherSubscriberParallelThreadTest() throws InterruptedException {
    // 반복실행 수
    int supplierCount = 5;
    // sleep 최대 시간 (초) (random)
    int bound = 5;

    CountDownLatch latch = new CountDownLatch(supplierCount);
    AtomicInteger counter = new AtomicInteger();
    AtomicInteger subCount = new AtomicInteger();
    Flux.generate(sink -> {
      final var job = queue.poll();

      if (null == job) {
        sink.complete();
      } else {
        sink.next(job);
      }
    })
        .repeatWhen(it -> it.delayElements(Duration.ofSeconds(1)))
        .parallel()
        .runOn(scheduler)
//        .subscribeOn(scheduler)
        .subscribe(job -> {
          try { Thread.sleep(random.nextInt(500)); } catch (InterruptedException e) {}
          ((Runnable) job).run();
        });

    new Thread(() -> {
      while(true) {
        try {
          for(int i = 0;i < 25;i++) {
            queue.put(new WorkerJob(subCount.incrementAndGet(), getRandomString(1, true)));
          }

          Thread.sleep(random.nextInt(bound) * 1000L);

          latch.countDown();
          if (supplierCount == counter.incrementAndGet()) break;
        } catch (InterruptedException e) {
        }
      }
    }).start();

    latch.await();
    assertEquals(supplierCount, counter.get());
  }

  String getRandomString(final int length, final boolean isSpecialChars) {
    var	str	= "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890" +
        (isSpecialChars ? "`~!@#$%^&*()_+<>,./" : "");
    str	= shuffle(str, 3);
    str	= repeat(str, 5);
    str	= shuffle(str, 5);

    return str.substring(0, length);
  }

  String shuffle(final String in, final int loop) {
    var out = new StringBuilder(in);
    var chars = new ArrayList<Character>();

    for(int i = 0;i < loop;i++) {
      var org = out.toString();

      out.setLength(0);
      for(var c : org.toCharArray())
        chars.add(c);

      while(!chars.isEmpty())
        out.append(chars.remove((int)(Math.random() * chars.size())));
    }

    return out.toString();
  }

  public final static String repeat(final String in, final int count) {
    var	out	= new StringBuilder(in.length() * count);

    for(int i = 0;i < count;i++) out.append(in);

    return out.toString();
  }

  @RequiredArgsConstructor
  class WorkerJob implements Runnable {
    private final int count;
    private final String arg;

    public void run() {
      log.debug("{} - 작업을 실행 :: {} | JOB :: {}", Thread.currentThread().getName(), count, arg);
    }
  }

}
