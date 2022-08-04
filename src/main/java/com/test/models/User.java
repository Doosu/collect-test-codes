package com.test.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Cloneable {

  private Long id;
  private String username;
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  private String password;
  private String profileImage;

  private Collection<String> authorities;

  @JsonIgnore
  private boolean adminRequest;

  @Override
  public User clone() throws CloneNotSupportedException {
    return (User) super.clone();
  }

}
