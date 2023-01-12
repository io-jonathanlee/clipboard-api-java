package io.jonathanlee.clipboardapijava.model.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class OrganizationMembershipRequest {

  @Id
  private ObjectId objectId;

  private String id;

  private String organizationId;

  private String requestingUserEmail;

  private boolean isApproved;

  private String approvingAdministratorEmail;

}
