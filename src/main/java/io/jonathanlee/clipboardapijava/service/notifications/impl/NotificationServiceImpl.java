package io.jonathanlee.clipboardapijava.service.notifications.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.notifications.NotificationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.notifications.NotificationDto;
import io.jonathanlee.clipboardapijava.exception.BadRequestException;
import io.jonathanlee.clipboardapijava.model.notifications.Notification;
import io.jonathanlee.clipboardapijava.repository.notifications.NotificationRepository;
import io.jonathanlee.clipboardapijava.service.RandomService;
import io.jonathanlee.clipboardapijava.service.notifications.NotificationService;
import java.time.Instant;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  private final RandomService randomService;

  @Override
  public StatusDataContainer<Collection<NotificationDto>> getNotifications(
      final String requestingUserEmail) {
    return new StatusDataContainer<>(HttpStatus.OK,
        this.notificationRepository.getNotificationsByTargetUserEmailIsAndAcknowledgedIsFalse(
                requestingUserEmail).stream()
            .map(notification -> new NotificationDto(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getTimestamp(),
                notification.getType(),
                notification.isAcknowledged()
            )).toList());
  }

  @Override
  public StatusDataContainer<Collection<NotificationDto>> getAllNotifications(
      String requestingUserEmail) {
    return new StatusDataContainer<>(HttpStatus.OK,
        this.notificationRepository.getNotificationsByTargetUserEmailIs(
                requestingUserEmail).stream()
            .map(notification -> new NotificationDto(
                notification.getId(),
                notification.getTitle(),
                notification.getContent(),
                notification.getTimestamp(),
                notification.getType(),
                notification.isAcknowledged()
            )).toList());
  }

  @Override
  public StatusDataContainer<NotificationDto> createNotification(
      final NotificationRequestDto notificationRequestDto) {
    final Notification notification = new Notification(
        ObjectId.get(),
        this.randomService.generateNewId(),
        notificationRequestDto.getTargetUserEmail(),
        notificationRequestDto.getTitle(),
        notificationRequestDto.getContent(),
        Instant.now(),
        notificationRequestDto.getType(),
        notificationRequestDto.isAcknowledged()
    );

    final Notification savedNotification = this.notificationRepository.save(notification);
    return new StatusDataContainer<>(HttpStatus.CREATED,
        new NotificationDto(
            savedNotification.getId(),
            savedNotification.getTitle(),
            savedNotification.getContent(),
            savedNotification.getTimestamp(),
            savedNotification.getType(),
            savedNotification.isAcknowledged()
        ));
  }

  @Override
  public StatusDataContainer<NotificationDto> acknowledgeNotification(
      final String requestingUserEmail, final String notificationId) {
    final Optional<Notification> notificationOptional = this.notificationRepository.getNotificationById(
        notificationId);
    if (notificationOptional.isEmpty()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }

    final Notification notification = notificationOptional.get();
    if (notification.isAcknowledged()) {
      throw new BadRequestException("Notification is already acknowledged");
    }
    notification.setAcknowledged(true);
    Notification savedNotification = this.notificationRepository.save(notification);

    return new StatusDataContainer<>(HttpStatus.OK, new NotificationDto(
        savedNotification.getId(),
        savedNotification.getTitle(),
        savedNotification.getContent(),
        savedNotification.getTimestamp(),
        savedNotification.getType(),
        savedNotification.isAcknowledged()
    ));
  }
}
