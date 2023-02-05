package io.jonathanlee.clipboardapijava.dto.request.notifications;

import io.jonathanlee.clipboardapijava.enums.notifications.NotificationType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationRequestDto {

  private String title;

  private String content;

  private String targetUserEmail;

  private NotificationType type;

  private boolean acknowledged;

}
