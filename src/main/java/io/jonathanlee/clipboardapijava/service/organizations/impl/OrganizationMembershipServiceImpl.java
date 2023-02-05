package io.jonathanlee.clipboardapijava.service.organizations.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationAdministratorRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.organization.RemoveOrganizationMemberRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.organization.OrganizationDto;
import io.jonathanlee.clipboardapijava.exception.BadRequestException;
import io.jonathanlee.clipboardapijava.model.organization.Organization;
import io.jonathanlee.clipboardapijava.repository.organizations.OrganizationRepository;
import io.jonathanlee.clipboardapijava.service.organizations.OrganizationMembershipService;
import java.util.Collection;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationMembershipServiceImpl implements OrganizationMembershipService {

  private final OrganizationRepository organizationRepository;

  @Override
  public StatusDataContainer<OrganizationDto> removeOrganizationAdministrator(
      final String requestingUserEmail, final String organizationId,
      final RemoveOrganizationAdministratorRequestDto removeOrganizationAdministratorRequestDto) {
    final StatusDataContainer<OrganizationDto> statusDataContainer =
        this.removeUserFromDesignatedCollectionAfterValidatingAction(
            requestingUserEmail,
            organizationId,
            removeOrganizationAdministratorRequestDto.getAdministratorToRemoveEmail(),
            MemberToRemoveType.ADMINISTRATOR
        );
    if (statusDataContainer.getHttpStatus().equals(HttpStatus.OK)) {
      log.info("Administrator with e-mail <{}> removed from organization with ID: {}",
          removeOrganizationAdministratorRequestDto.getAdministratorToRemoveEmail(),
          organizationId);
    }
    return statusDataContainer;
  }

  @Override
  public StatusDataContainer<OrganizationDto> removeOrganizationMember(
      final String requestingUserEmail, final String organizationId,
      final RemoveOrganizationMemberRequestDto removeOrganizationMemberRequestDto) {
    final StatusDataContainer<OrganizationDto> statusDataContainer =
        this.removeUserFromDesignatedCollectionAfterValidatingAction(
            requestingUserEmail,
            organizationId,
            removeOrganizationMemberRequestDto.getMemberToRemoveEmail(),
            MemberToRemoveType.MEMBER
        );
    if (statusDataContainer.getHttpStatus().equals(HttpStatus.OK)) {
      log.info("Member with e-mail <{}> removed from organization with ID: {}",
          removeOrganizationMemberRequestDto.getMemberToRemoveEmail(), organizationId);
    }
    return statusDataContainer;
  }

  private StatusDataContainer<OrganizationDto> removeUserFromDesignatedCollectionAfterValidatingAction(
      final String requestingUserEmail, final String organizationId, final String userEmailToRemove,
      final MemberToRemoveType memberToRemoveType
  ) {
    final Optional<Organization> organizationOptional = this.organizationRepository.findById(
        organizationId);
    if (organizationOptional.isEmpty()) {
      return new StatusDataContainer<>(HttpStatus.NOT_FOUND, null);
    }
    final Organization organization = organizationOptional.get();
    if (!organization.getAdministratorEmails().contains(requestingUserEmail)) {
      return new StatusDataContainer<>(HttpStatus.FORBIDDEN, null);
    }
    final Collection<String> collectionToModify = (memberToRemoveType == MemberToRemoveType.MEMBER)
        ? organization.getMemberEmails()
        : organization.getAdministratorEmails();
    if (!collectionToModify.contains(userEmailToRemove)) {
      throw new BadRequestException(
          "Cannot remove user email from organization that does not contain that user email");
    }
    collectionToModify.remove(userEmailToRemove);
    final Organization savedOrganization = this.organizationRepository.save(organization);
    return new StatusDataContainer<>(HttpStatus.OK, new OrganizationDto(
        savedOrganization.getId(),
        savedOrganization.getName(),
        savedOrganization.getMemberEmails(),
        savedOrganization.getAdministratorEmails()
    ));
  }

  private enum MemberToRemoveType {
    ADMINISTRATOR,
    MEMBER,
  }

}
