package io.jonathanlee.clipboardapijava.service.organizations;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationAdministratorRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationMemberRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;

public interface OrganizationMembershipService {

  StatusDataContainer<OrganizationDto> removeOrganizationAdministrator(
      final String requestingUserEmail, final String organizationId,
      final RemoveOrganizationAdministratorRequestDto removeOrganizationAdministratorRequestDto
  );

  StatusDataContainer<OrganizationDto> removeOrganizationMember(
      String requestingUserEmail, String organizationId,
      RemoveOrganizationMemberRequestDto removeOrganizationMemberRequestDto);
}
