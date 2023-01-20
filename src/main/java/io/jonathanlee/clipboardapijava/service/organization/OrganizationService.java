package io.jonathanlee.clipboardapijava.service.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.OrganizationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import java.util.Collection;

public interface OrganizationService {

  StatusDataContainer<OrganizationDto> createOrganization(
      final String requestingUserEmail, final OrganizationRequestDto organizationRequestDto
  );

  StatusDataContainer<OrganizationDto> getOrganizationById(
      final String requestingUserEmail, final String organizationId
  );

  StatusDataContainer<OrganizationDto> addOrganizationMember(
      final String requestingUserEmail, final String organizationId, final String memberEmailToAdd);

  StatusDataContainer<Collection<OrganizationDto>> getOrganizationsWhereMember(
      final String requestingUserEmail);

}
