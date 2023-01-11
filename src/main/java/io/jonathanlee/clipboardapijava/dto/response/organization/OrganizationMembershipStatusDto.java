package io.jonathanlee.clipboardapijava.dto.response.organization;

import io.jonathanlee.clipboardapijava.enums.organization.OrganizationMembershipStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganizationMembershipStatusDto {

  private OrganizationMembershipStatus status;

}
