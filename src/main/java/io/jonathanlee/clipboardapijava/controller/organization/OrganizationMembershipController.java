package io.jonathanlee.clipboardapijava.controller.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationAdministratorRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationMemberRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import io.jonathanlee.clipboardapijava.helper.AuthenticationHelper;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationMembershipService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations/memberships")
public class OrganizationMembershipController {

  private final OrganizationMembershipService organizationMembershipService;

  @PutMapping(value = "/{organizationId}/remove-member",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationDto> removeMember(@PathVariable final String organizationId,
      @Valid @RequestBody final
      RemoveOrganizationMemberRequestDto removeOrganizationMemberRequestDto) {
    final StatusDataContainer<OrganizationDto> statusDataContainer =
        this.organizationMembershipService.removeOrganizationMember(
            AuthenticationHelper.getCurrentUsername(), organizationId,
            removeOrganizationMemberRequestDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PutMapping(value = "/{organizationId}/remove-administrator",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationDto> removeAdministrator(
      @PathVariable final String organizationId,
      @Valid @RequestBody final RemoveOrganizationAdministratorRequestDto removeOrganizationAdministratorRequestDto
  ) {
    final StatusDataContainer<OrganizationDto> statusDataContainer =
        this.organizationMembershipService.removeOrganizationAdministrator(
            AuthenticationHelper.getCurrentUsername(),
            organizationId,
            removeOrganizationAdministratorRequestDto
        );
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
