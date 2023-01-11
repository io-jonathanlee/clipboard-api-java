package io.jonathanlee.clipboardapijava.dto.response.organization;

import lombok.Data;

@Data
public class OrganizationMembershipRequestDto {

  private String id;

  private String organizationId;

  private String requestingUserEmail;

  private boolean isApproved;

  private String approvingAdministratorEmail;

}
