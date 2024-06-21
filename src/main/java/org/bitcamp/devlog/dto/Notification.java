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
@Alias("Notification")
public class Notification {
  private Long notificationId;
  private String notificationType;
  private java.sql.Timestamp nCreatedAt;
  private Long deleted;
  private java.sql.Timestamp readAt;
  private Long accountId;
}
