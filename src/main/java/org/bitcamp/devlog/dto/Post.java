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
@Alias("Post")
public class Post {
  private Long postId;
  private Long categoryId;
  private java.sql.Timestamp pCreatedAt;
  private java.sql.Timestamp pUpdatedAt;
  private String pContent;
  private Long hits;
  private Long openType;
  private String postUrl;
  private String title;
  private Long accountId;
  private String file;
  private Long postTagId;
}
