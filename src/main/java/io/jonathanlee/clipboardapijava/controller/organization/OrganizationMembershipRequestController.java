package io.jonathanlee.clipboardapijava.controller.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationMembershipStatusDto;
import io.jonathanlee.clipboardapijava.helper.AuthenticationHelper;
import io.jonathanlee.clipboardapijava.service.organization.OrganizationMembershipRequestService;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("organizations/memberships/requests")
public class OrganizationMembershipRequestController {

  private final OrganizationMembershipRequestService organizationMembershipRequestService;

  @GetMapping(
      value = "/{organizationId}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Collection<OrganizationMembershipRequestDto>> getAllOrganizationMembershipRequests(
      @PathVariable final String organizationId
  ) {
    final StatusDataContainer<Collection<OrganizationMembershipRequestDto>> statusDataContainer =
        this.organizationMembershipRequestService.getRequestsToJoinOrganization(
            AuthenticationHelper.getCurrentUsername(),
            organizationId
        );

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PostMapping(
      value = "/{organizationId}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationMembershipStatusDto> requestToJoinOrganization(
      @PathVariable final String organizationId) {
    final StatusDataContainer<OrganizationMembershipStatusDto> statusDataContainer =
        this.organizationMembershipRequestService.requestToJoinOrganization(
            AuthenticationHelper.getCurrentUsername(),
            organizationId
        );

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PutMapping(
      value = "/{organizationMembershipRequestId}/approve",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationMembershipStatusDto> approveRequestToJoinOrganization(
      @PathVariable final String organizationMembershipRequestId
  ) {
    final StatusDataContainer<OrganizationMembershipStatusDto> statusDataContainer =
        this.organizationMembershipRequestService.approveRequestToJoinOrganization(
            AuthenticationHelper.getCurrentUsername(),
            organizationMembershipRequestId
        );

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
