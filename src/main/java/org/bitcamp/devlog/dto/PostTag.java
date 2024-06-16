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
@Alias("PostTag")
public class PostTag {
  private Long postTagId;
  private java.sql.Timestamp ptCreatedAt;
  private java.sql.Timestamp ptUpdatedAt;
  private Long postId;
  private Long tagId;
}
