package io.jonathanlee.clipboardapijava.controller.deliveries;

import io.jonathanlee.clipboardapijava.dto.response.deliveries.DeliveryDto;
import java.util.Collection;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/deliveries")
public class DeliveryController {

  @GetMapping
  public ResponseEntity<Collection<DeliveryDto>> getDeliveries(
      @RequestHeader final String organizationId
  ) {
    log.info("Request header says organization ID: {}", organizationId);
    final DeliveryDto deliveryDto = new DeliveryDto(
        organizationId,
        "Test",
        "Salmon Dinner",
        true
    );
    return ResponseEntity.status(HttpStatus.OK).body(List.of(deliveryDto));
  }

}
