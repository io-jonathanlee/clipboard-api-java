package io.jonathanlee.clipboardapijava.model.notifications;

import io.jonathanlee.clipboardapijava.enums.notifications.NotificationType;
import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Notification {

  @Id
  private ObjectId objectId;

  private String id;

  private String targetUserEmail;

  private String title;

  private String content;

  private Instant timestamp;

  private NotificationType type;

  private boolean acknowledged;

}
