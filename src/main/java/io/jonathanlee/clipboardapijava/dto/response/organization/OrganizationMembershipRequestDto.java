package io.jonathanlee.clipboardapijava.dto.response.organization;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationMembershipRequestDto {

  private String id;

  private String organizationId;

  private String requestingUserEmail;

  private boolean isApproved;

  private String approvingAdministratorEmail;

}
