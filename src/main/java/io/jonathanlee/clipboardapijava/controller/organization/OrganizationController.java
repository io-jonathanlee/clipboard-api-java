package io.jonathanlee.clipboardapijava.controller.organization;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.OrganizationRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import io.jonathanlee.clipboardapijava.helper.AuthenticationHelper;
import io.jonathanlee.clipboardapijava.service.organizations.OrganizationService;
import jakarta.validation.Valid;
import java.util.Collection;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/organizations")
public class OrganizationController {

  private final OrganizationService organizationService;

  @GetMapping(
      value = "/{organizationId}",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationDto> getOrganizationById(
      @PathVariable final String organizationId) {
    final StatusDataContainer<OrganizationDto> statusDataContainer = this.organizationService
        .getOrganizationById(AuthenticationHelper.getCurrentUsername(), organizationId);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<OrganizationDto> createOrganization(
      @Valid @RequestBody final OrganizationRequestDto organizationRequestDto
  ) {
    final StatusDataContainer<OrganizationDto> statusDataContainer = this.organizationService
        .createOrganization(AuthenticationHelper.getCurrentUsername(), organizationRequestDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @GetMapping(
      value = "/where-member",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Collection<OrganizationDto>> getWhereMember() {
    final StatusDataContainer<Collection<OrganizationDto>> statusDataContainer =
        this.organizationService.getOrganizationsWhereMember(
            AuthenticationHelper.getCurrentUsername());

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @GetMapping(
      value = "/where-involved",
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<Collection<OrganizationDto>> getWhereInvolved() {
    final StatusDataContainer<Collection<OrganizationDto>> statusDataContainer =
        this.organizationService.getOrganizationsWhereInvolved(
            AuthenticationHelper.getCurrentUsername()
        );

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
