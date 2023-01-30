package io.jonathanlee.clipboardapijava.model.deliveries;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Delivery {

  @Id
  private ObjectId objectId;

  private String id;

  private String creatorEmail;

  private String assignedDriverEmail;

  private String organizationId;

  private String title;

  private String details;

  private boolean delivered;

}
