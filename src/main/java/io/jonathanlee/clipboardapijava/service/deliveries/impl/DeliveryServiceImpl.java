package io.jonathanlee.clipboardapijava.service.deliveries.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.deliveries.DeliveryRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.deliveries.DeliveryDto;
import io.jonathanlee.clipboardapijava.model.deliveries.Delivery;
import io.jonathanlee.clipboardapijava.repository.deliveries.DeliveryRepository;
import io.jonathanlee.clipboardapijava.service.RandomService;
import io.jonathanlee.clipboardapijava.service.deliveries.DeliveryService;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationService;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final OrganizationService organizationService;

  private final DeliveryRepository deliveryRepository;

  private final RandomService randomService;

  @Override
  public StatusDataContainer<Collection<DeliveryDto>> getAllAssignedDeliveries(
      final String requestingUserEmail, final String organizationId) {
    if (!this.organizationService.isUserMemberOfOrganization(requestingUserEmail, organizationId)) {
      return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
    }
    final Collection<Delivery> deliveries = this.deliveryRepository.findDeliveriesByOrganizationIdIsAndAssignedDriverEmailIs(
        organizationId, requestingUserEmail);
    log.info("Request to retrieve all deliveries assigned to: {} for organization with ID: {}",
        requestingUserEmail, organizationId);
    return new StatusDataContainer<>(HttpStatus.OK,
        deliveries.stream().map(delivery -> new DeliveryDto(
            delivery.getId(),
            delivery.getOrganizationId(),
            delivery.getTitle(),
            delivery.getDetails(),
            delivery.isDelivered()
        )).toList());
  }

  @Override
  public StatusDataContainer<Collection<DeliveryDto>> getAllCreatedDeliveries(
      final String requestingUserEmail, final String organizationId) {
    if (!this.organizationService.isUserAdministratorOfOrganization(requestingUserEmail,
        organizationId)) {
      return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
    }
    final Collection<Delivery> deliveries = this.deliveryRepository.findDeliveriesByCreatorEmailIs(
        requestingUserEmail);
    log.info("Request to retrieve all deliveries created by: {} for organization with ID: {}",
        requestingUserEmail, organizationId);
    return new StatusDataContainer<>(HttpStatus.OK,
        deliveries.stream().map(delivery -> new DeliveryDto(
            delivery.getId(),
            delivery.getOrganizationId(),
            delivery.getTitle(),
            delivery.getDetails(),
            delivery.isDelivered()
        )).toList());
  }

  @Override
  public StatusDataContainer<DeliveryDto> createDelivery(
      final String requestingUserEmail,
      final String organizationId,
      final DeliveryRequestDto deliveryRequestDto) {
    if (!this.organizationService
        .isUserAdministratorOfOrganization(requestingUserEmail, organizationId)) {
      return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
    }

    final Delivery delivery = new Delivery(
        ObjectId.get(),
        this.randomService.generateNewId(),
        requestingUserEmail,
        deliveryRequestDto.getAssignedDriverEmail(),
        organizationId,
        deliveryRequestDto.getTitle(),
        deliveryRequestDto.getDetails(),
        deliveryRequestDto.isDelivered()
    );
    final Delivery savedDelivery = this.deliveryRepository.save(delivery);
    log.info("Delivery created with ID: {}", savedDelivery.getId());
    return new StatusDataContainer<>(HttpStatus.CREATED, new DeliveryDto(
        savedDelivery.getId(),
        savedDelivery.getOrganizationId(),
        savedDelivery.getTitle(),
        savedDelivery.getDetails(),
        savedDelivery.isDelivered()
    ));
  }

  @Override
  public StatusDataContainer<DeliveryDto> markDeliveryAsDelivered(final String requestingUserEmail,
      final String organizationId, final String deliveryId) {
    return this.markDeliveryAsDeliveredOrUndelivered(requestingUserEmail, organizationId,
        deliveryId,
        true);
  }

  @Override
  public StatusDataContainer<DeliveryDto> markDeliveryAsUndelivered(
      final String requestingUserEmail, final String organizationId, final String deliveryId) {
    return this.markDeliveryAsDeliveredOrUndelivered(requestingUserEmail, organizationId,
        deliveryId,
        false);
  }

  private StatusDataContainer<DeliveryDto> markDeliveryAsDeliveredOrUndelivered(
      final String requestingUserEmail, final String organizationId, final String deliveryId,
      final boolean isDelivered) {
    if (!this.organizationService.isUserAdministratorOfOrganization(requestingUserEmail,
        organizationId) &&
        !this.organizationService.isUserMemberOfOrganization(requestingUserEmail, organizationId)) {
      return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
    }

    final Optional<Delivery> deliveryOptional = this.deliveryRepository.findById(deliveryId);
    if (deliveryOptional.isEmpty()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }

    final Delivery delivery = deliveryOptional.get();
    delivery.setDelivered(isDelivered);
    final Delivery savedDelivery = this.deliveryRepository.save(delivery);
    log.info("Successfully marked delivery with ID: {} as isDelivered: {}", delivery.getId(),
        isDelivered);
    return new StatusDataContainer<>(HttpStatus.OK, new DeliveryDto(
        savedDelivery.getId(),
        savedDelivery.getOrganizationId(),
        savedDelivery.getTitle(),
        savedDelivery.getDetails(),
        savedDelivery.isDelivered()
    ));
  }
}
