package io.jonathanlee.clipboardapijava.model.organization;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
@AllArgsConstructor
public class Organization {

  @Id
  private ObjectId objectId;

  private String id;

  private String name;

  private Collection<String> memberEmails;

  private Collection<String> administratorEmails;

}
