package io.jonathanlee.clipboardapijava.service.notifications;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.notifications.NotificationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.notifications.NotificationDto;
import java.util.Collection;

public interface NotificationService {

  StatusDataContainer<Collection<NotificationDto>> getNotifications(
      final String requestingUserEmail);

  StatusDataContainer<Collection<NotificationDto>> getAllNotifications(
      final String requestingUserEmail);

  StatusDataContainer<NotificationDto> createNotification(
      final NotificationRequestDto notificationRequestDto);

  StatusDataContainer<NotificationDto> acknowledgeNotification(final String requestingUserEmail,
      final String notificationId);
}
