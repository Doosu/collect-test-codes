package com.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import com.test.models.User;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class ModelCloneTest {

  @Test
  public void cloneUser() throws Exception {
    final var user = User.builder()
        .id(1L)
        .username("andxlddl")
        .password("{noop}1234")
        .authorities(new ArrayList<>() {
          {
            add("ADMIN");
            add("MANAGER");
            add("USER");
          }
        })
        .adminRequest(true)
      .build();
    final var clone = user.clone();

    log.warn("CREATED :: {}", user);
    log.warn("CLONED :: {}", clone);

    assertEquals(user, clone);

    clone.getAuthorities().remove("USER");
    assertEquals(user, clone);

    clone.setAuthorities(Set.of("ADMIN", "MANAGER"));
    assertFalse(Objects.equals(user, clone));

    clone.setAdminRequest(false);
    assertFalse(Objects.equals(user, clone));
  }

}
