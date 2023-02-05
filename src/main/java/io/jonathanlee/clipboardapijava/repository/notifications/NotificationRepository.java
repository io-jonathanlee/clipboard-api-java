package io.jonathanlee.clipboardapijava.repository.notifications;

import io.jonathanlee.clipboardapijava.model.notifications.Notification;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, ObjectId> {

  Collection<Notification> getNotificationsByTargetUserEmailIsAndAcknowledgedIsFalse(
      final String targetUserEmail);

  Collection<Notification> getNotificationsByTargetUserEmailIs(final String targetUserEmail);

  Optional<Notification> getNotificationById(final String id);

}
