package io.jonathanlee.clipboardapijava.service.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.OrganizationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;

public interface OrganizationService {

  StatusDataContainer<OrganizationDto> createOrganization(
      final String requestingUserEmail, final OrganizationRequestDto organizationRequestDto
  );

  StatusDataContainer<OrganizationDto> getOrganizationById(
      final String requestingUserEmail, final String organizationId
  );

}
