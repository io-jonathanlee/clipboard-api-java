package io.jonathanlee.clipboardapijava.service.organization.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipStatusDto;
import io.jonathanlee.clipboardapijava.enums.organization.OrganizationMembershipStatus;
import io.jonathanlee.clipboardapijava.exception.BadRequestException;
import io.jonathanlee.clipboardapijava.model.organization.OrganizationMembershipRequest;
import io.jonathanlee.clipboardapijava.repository.organization.OrganizationMembershipRequestRepository;
import io.jonathanlee.clipboardapijava.service.RandomService;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationMembershipRequestService;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationService;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/**
 * Implementation of organization membership request service. Used to manage requests to join
 * organizations etc.
 *
 * @author jonathanlee <jonathan.lee.devel@gmail.com>
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationMembershipRequestServiceImpl implements
    OrganizationMembershipRequestService {

  private final OrganizationMembershipRequestRepository organizationMembershipRequestRepository;

  private final OrganizationService organizationService;

  private final RandomService randomService;

  @Override
  public StatusDataContainer<OrganizationMembershipStatusDto> approveRequestToJoinOrganization(
      final String requestingUserEmail, final String organizationMembershipRequestId) {
    final Optional<OrganizationMembershipRequest> organizationMembershipRequestOptional =
        this.organizationMembershipRequestRepository.findById(organizationMembershipRequestId);
    if (organizationMembershipRequestOptional.isEmpty()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }
    final OrganizationMembershipRequest organizationMembershipRequest = organizationMembershipRequestOptional.get();
    final StatusDataContainer<OrganizationDto> organizationDtoStatusDataContainer =
        this.organizationService.addOrganizationMember(
            requestingUserEmail,
            organizationMembershipRequest.getOrganizationId(),
            organizationMembershipRequest.getRequestingUserEmail());
    if (!organizationDtoStatusDataContainer.getHttpStatus().is2xxSuccessful()) {
      return new StatusDataContainer<>(organizationDtoStatusDataContainer.getHttpStatus(),
          new OrganizationMembershipStatusDto(
              OrganizationMembershipStatus.FAILURE
          ));
    }
    organizationMembershipRequest.setApproved(true);
    organizationMembershipRequest.setApprovingAdministratorEmail(requestingUserEmail);
    this.organizationMembershipRequestRepository.save(organizationMembershipRequest);
    log.info("Organization membership request approved with ID: {}",
        organizationMembershipRequest.getId());
    return new StatusDataContainer<>(HttpStatus.OK, new OrganizationMembershipStatusDto(
        OrganizationMembershipStatus.SUCCESS
    ));
  }

  @Override
  public StatusDataContainer<Collection<OrganizationMembershipRequestDto>> getRequestsToJoinOrganization(
      final String requestingUserEmail, final String organizationId) {
    final Collection<OrganizationMembershipRequest> organizationMembershipRequests =
        this.organizationMembershipRequestRepository.findAll();
    final StatusDataContainer<OrganizationDto> organizationDtoStatusDataContainer =
        this.organizationService.getOrganizationById(requestingUserEmail, organizationId);
    if (!organizationDtoStatusDataContainer.getHttpStatus().is2xxSuccessful()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }
    final Collection<OrganizationMembershipRequest> organizationMembershipRequestsWhereAdmin =
        organizationMembershipRequests.stream()
            .filter(organizationMembershipRequest -> organizationDtoStatusDataContainer
                .getData().getAdministratorEmails()
                .contains(organizationMembershipRequest.getRequestingUserEmail()))
            .toList();
    final Collection<OrganizationMembershipRequestDto> organizationMembershipRequestDtoCollection =
        organizationMembershipRequestsWhereAdmin.stream()
            .map(organizationMembershipRequest -> new OrganizationMembershipRequestDto(
                organizationMembershipRequest.getId(),
                organizationMembershipRequest.getOrganizationId(),
                organizationMembershipRequest.getRequestingUserEmail(),
                organizationMembershipRequest.isApproved(),
                organizationMembershipRequest.getApprovingAdministratorEmail()
            )).toList();
    log.info(
        "Successfully retrieved all organization membership requests for organization with ID: {}",
        organizationId);
    return new StatusDataContainer<>(HttpStatus.OK, organizationMembershipRequestDtoCollection);
  }

  @Override
  public StatusDataContainer<OrganizationMembershipStatusDto> requestToJoinOrganization(
      final String requestingUserEmail, final String organizationId) {
    final Optional<OrganizationMembershipRequest> organizationMembershipRequestOptional =
        this.organizationMembershipRequestRepository.findByOrganizationIdAndRequestingUserEmail(
            organizationId, requestingUserEmail);
    if (organizationMembershipRequestOptional.isPresent()) {
      return new StatusDataContainer<>(HttpStatus.CONFLICT, null);
    }

    final StatusDataContainer<OrganizationDto> organizationDtoStatusDataContainer =
        this.organizationService.getOrganizationById(requestingUserEmail, organizationId);
    if (organizationDtoStatusDataContainer.getHttpStatus().equals(HttpStatus.NOT_FOUND)) {
      throw new BadRequestException("Organization does not exist");
    }
    final OrganizationMembershipRequest organizationMembershipRequest = new OrganizationMembershipRequest(
        ObjectId.get(),
        this.randomService.generateNewId(),
        organizationId,
        requestingUserEmail,
        false,
        null
    );
    this.organizationMembershipRequestRepository.save(organizationMembershipRequest);
    log.info("Organization membership request successfully created for organization with ID: {}",
        organizationId);
    return new StatusDataContainer<>(HttpStatus.OK, new OrganizationMembershipStatusDto(
        OrganizationMembershipStatus.AWAITING_APPROVAL
    ));
  }
}
