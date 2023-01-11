package io.jonathanlee.clipboardapijava.service.organization.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.OrganizationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import io.jonathanlee.clipboardapijava.model.organization.Organization;
import io.jonathanlee.clipboardapijava.repository.organization.OrganizationRepository;
import io.jonathanlee.clipboardapijava.service.RandomService;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationServiceImpl implements OrganizationService {

  private final OrganizationRepository organizationRepository;

  private final RandomService randomService;

  private static OrganizationDto organizationToOrganizationDto(final Organization organization) {
    return new OrganizationDto(
        organization.getId(),
        organization.getName(),
        organization.getMemberEmails(),
        organization.getAdministratorEmails()
    );
  }

  @Override
  public StatusDataContainer<OrganizationDto> getOrganizationById(final String requestingUserEmail,
      final String organizationId) {
    log.info("Request to get organization with ID: {}", organizationId);
    final Optional<Organization> organizationOptional =
        this.organizationRepository.findById(organizationId);
    if (organizationOptional.isEmpty()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }
    final Organization organization = organizationOptional.get();
    if (organization.getMemberEmails().contains(requestingUserEmail) ||
        organization.getAdministratorEmails().contains(requestingUserEmail)) {
      return new StatusDataContainer<>(HttpStatus.OK, organizationToOrganizationDto(organization));
    }

    return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
  }

  @Override
  public StatusDataContainer<OrganizationDto> createOrganization(final String requestingUserEmail,
      final OrganizationRequestDto organizationRequestDto) {
    final Organization organization = new Organization(
        ObjectId.get(),
        this.randomService.generateNewId(),
        organizationRequestDto.getName(),
        Collections.emptyList(),
        List.of(requestingUserEmail)
    );
    this.organizationRepository.save(organization);
    log.info("New organization created with ID: {}", organization.getId());
    return new StatusDataContainer<>(HttpStatus.CREATED,
        organizationToOrganizationDto(organization));
  }

}