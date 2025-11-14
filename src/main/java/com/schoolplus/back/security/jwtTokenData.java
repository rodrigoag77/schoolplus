package com.schoolplus.back.security;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class jwtTokenData {
  private String id;
  private String login;
  private String token;
  private Timestamp accessAt;
}
