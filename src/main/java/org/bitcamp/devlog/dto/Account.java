package org.bitcamp.devlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("Account")
public class Account {
  private Long accountId;
  private Long providerType;
  private java.sql.Timestamp aCreatedAt;
  private java.sql.Timestamp aUpdatedAt;
  private String biography;
  private String blogId;
  private String email;
  private String homepage;
  private String name;
  private String refreshToken;
  private String accessToken;
  private String file;
  List<AccountRole> role;
}
