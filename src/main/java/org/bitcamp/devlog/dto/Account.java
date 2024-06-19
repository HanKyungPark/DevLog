package org.bitcamp.devlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Alias("Account")
public class Account {

  private Long accountId;
  private Long providerType;
  private java.sql.Timestamp aCreatedAt;
  private java.sql.Timestamp aUpdateAt;
  private String biography;//블로그 설명
  private String blogId;// 블로그의 이름
  private String email;
  private String homepage;//url 주소
  private String name;
  private String refreshToken;
  private String accessToken;
  private String file;
}