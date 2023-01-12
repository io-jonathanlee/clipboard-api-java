package io.jonathanlee.clipboardapijava.repository.organization;

import io.jonathanlee.clipboardapijava.model.organization.OrganizationMembershipRequest;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrganizationMembershipRequestRepository extends
    MongoRepository<OrganizationMembershipRequest, ObjectId> {

  Optional<OrganizationMembershipRequest> findById(final String organizationMembershipRequestId);

  Optional<OrganizationMembershipRequest> findByOrganizationIdAndRequestingUserEmail(
      final String organizationId, final String requestingUserEmail);

}
