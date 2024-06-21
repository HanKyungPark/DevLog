package org.bitcamp.devlog.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Alias("Heart")
public class Heart {
  private Long heartId;
  private java.sql.Timestamp hCreatedAt;
  private java.sql.Timestamp hUpdatedAt;
  private Long accountId;
  private Long postId;
}
