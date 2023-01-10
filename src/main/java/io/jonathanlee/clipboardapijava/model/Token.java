package io.jonathanlee.clipboardapijava.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;

@Data
@Document
@AllArgsConstructor
public class Token implements Serializable {

  @Serial
  private static final long serialVersionUID = 2405172041950251807L;

  @Id
  @Field("_id")
  private ObjectId objectId;

  private String id;

  private String value;

  private Instant expiryDate;

}
