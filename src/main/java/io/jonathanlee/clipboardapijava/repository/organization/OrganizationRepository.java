package io.jonathanlee.clipboardapijava.repository.organization;

import io.jonathanlee.clipboardapijava.model.organization.Organization;
import java.util.Collection;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationRepository extends MongoRepository<Organization, ObjectId> {

  Optional<Organization> findById(final String organizationId);

  Collection<Organization> findOrganizationByMemberEmailsContaining(final String memberEmail);

  Collection<Organization> findOrganizationByAdministratorEmailsContainingOrMemberEmailsContaining(
      Collection<String> administratorEmails, Collection<String> memberEmails);

}
