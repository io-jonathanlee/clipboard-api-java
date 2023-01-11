package io.jonathanlee.clipboardapijava.service.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipStatusDto;
import java.util.Collection;

public interface OrganizationMembershipRequestService {

  StatusDataContainer<OrganizationMembershipStatusDto> approveRequestToJoinOrganization(
      final String requestingUserEmail, final String organizationMembershipRequestId
  );

  StatusDataContainer<Collection<OrganizationMembershipRequestDto>> getRequestsToJoinOrganization(
      final String requestingUserEmail, final String organizationId
  );

  StatusDataContainer<OrganizationMembershipStatusDto> requestToJoinOrganization(
      final String requestingUserEmail, final String organizationId
  );

}
