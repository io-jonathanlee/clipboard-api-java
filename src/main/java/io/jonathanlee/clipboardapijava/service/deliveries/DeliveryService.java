package io.jonathanlee.clipboardapijava.service.deliveries;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.deliveries.DeliveryRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.deliveries.DeliveryDto;
import java.util.Collection;

public interface DeliveryService {

  StatusDataContainer<Collection<DeliveryDto>> getAllAssignedDeliveries(
      final String requestingUserEmail, final String organizationId);

  StatusDataContainer<Collection<DeliveryDto>> getAllCreatedDeliveries(
      final String requestingUserEmail, final String organizationId);

  StatusDataContainer<DeliveryDto> createDelivery(
      final String requestingUserEmail,
      final String organizationId,
      final DeliveryRequestDto deliveryRequestDto);

  StatusDataContainer<DeliveryDto> markDeliveryAsDelivered(final String requestingUserEmail,
      final String organizationId, final String deliveryId);

  StatusDataContainer<DeliveryDto> markDeliveryAsUndelivered(final String requestingUserEmail,
      final String organizationId, final String deliveryId);
}
