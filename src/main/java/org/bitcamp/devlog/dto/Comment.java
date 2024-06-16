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
@Alias("Comment")
public class Comment {
  private Long commentId;
  private java.sql.Timestamp cCreatedAt;
  private java.sql.Timestamp cUpdatedAt;
  private String cContent;
  private Long parentId;
  private Long postId;
  private Long accountId;
}
