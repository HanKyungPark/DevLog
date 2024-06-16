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
@Alias("Tag")
public class Tag {
  private Long tagId;
  private java.sql.Timestamp tCreatedAt;
  private java.sql.Timestamp tUpdatedAt;
  private String tagName;
}