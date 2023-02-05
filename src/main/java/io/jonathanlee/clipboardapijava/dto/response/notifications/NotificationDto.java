package io.jonathanlee.clipboardapijava.dto.response.notifications;

import io.jonathanlee.clipboardapijava.enums.notifications.NotificationType;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationDto {

  private String id;

  private String title;

  private String content;

  private Instant timestamp;

  private NotificationType type;

  private boolean acknowledged;

}
