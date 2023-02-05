package io.jonathanlee.clipboardapijava.controller.notifications;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.response.notifications.NotificationDto;
import io.jonathanlee.clipboardapijava.helper.AuthenticationHelper;
import io.jonathanlee.clipboardapijava.service.notifications.NotificationService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/notifications")
public class NotificationsController {

  private final NotificationService notificationService;

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<NotificationDto>> getNotifications() {
    final StatusDataContainer<Collection<NotificationDto>> statusDataContainer =
        this.notificationService.getNotifications(AuthenticationHelper.getCurrentUsername());

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<Collection<NotificationDto>> getAllNotifications() {
    final StatusDataContainer<Collection<NotificationDto>> statusDataContainer =
        this.notificationService.getAllNotifications(AuthenticationHelper.getCurrentUsername());

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PutMapping(value = "/acknowledge/{notificationId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<NotificationDto> acknowledgeNotification(
      @PathVariable final String notificationId) {
    final StatusDataContainer<NotificationDto> statusDataContainer =
        this.notificationService.acknowledgeNotification(AuthenticationHelper.getCurrentUsername(),
            notificationId);

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
