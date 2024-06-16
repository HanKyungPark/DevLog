package org.bitcamp.devlog.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Alias("AccountRole")
public class AccountRole {
  private Long accountRoleId;
  private java.sql.Timestamp arCreatedAt;
  private java.sql.Timestamp arUpdatedAt;
  private Long accountId;
  private String roleName;
}
