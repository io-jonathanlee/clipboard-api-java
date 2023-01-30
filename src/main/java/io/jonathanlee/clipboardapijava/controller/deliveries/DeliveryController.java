package io.jonathanlee.clipboardapijava.controller.deliveries;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.deliveries.DeliveryRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.deliveries.DeliveryDto;
import io.jonathanlee.clipboardapijava.helper.AuthenticationHelper;
import io.jonathanlee.clipboardapijava.service.deliveries.DeliveryService;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {

  private final DeliveryService deliveryService;

  @GetMapping(
      value = "/assigned",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Collection<DeliveryDto>> getAssignedDeliveries(
      @RequestHeader final String organizationId
  ) {
    final StatusDataContainer<Collection<DeliveryDto>> statusDataContainer =
        this.deliveryService
            .getAllAssignedDeliveries(AuthenticationHelper.getCurrentUsername(), organizationId);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @GetMapping(
      value = "/created",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Collection<DeliveryDto>> getCreatedDeliveries(
      @RequestHeader final String organizationId
  ) {
    final StatusDataContainer<Collection<DeliveryDto>> statusDataContainer =
        this.deliveryService
            .getAllCreatedDeliveries(AuthenticationHelper.getCurrentUsername(), organizationId);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<DeliveryDto> createDelivery(
      @RequestHeader final String organizationId,
      @Valid @RequestBody final DeliveryRequestDto deliveryRequestDto
  ) {
    final StatusDataContainer<DeliveryDto> statusDataContainer =
        this.deliveryService
            .createDelivery(AuthenticationHelper.getCurrentUsername(), organizationId,
                deliveryRequestDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PutMapping(
      value = "/{deliveryId}/mark-delivered",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<DeliveryDto> markDeliveryAsDelivered(
      @RequestHeader final String organizationId,
      @PathVariable final String deliveryId
  ) {
    final StatusDataContainer<DeliveryDto> statusDataContainer =
        this.deliveryService
            .markDeliveryAsDelivered(
                AuthenticationHelper.getCurrentUsername(),
                organizationId,
                deliveryId
            );
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PutMapping(
      value = "/{deliveryId}/mark-undelivered",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<DeliveryDto> markDeliveryAsUndelivered(
      @RequestHeader final String organizationId,
      @PathVariable final String deliveryId
  ) {
    final StatusDataContainer<DeliveryDto> statusDataContainer =
        this.deliveryService
            .markDeliveryAsUndelivered(
                AuthenticationHelper.getCurrentUsername(),
                organizationId,
                deliveryId
            );
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
