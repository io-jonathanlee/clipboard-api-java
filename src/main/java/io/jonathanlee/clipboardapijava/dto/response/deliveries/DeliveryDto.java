package io.jonathanlee.clipboardapijava.dto.response.deliveries;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryDto {

  private String id;

  private String organizationId;

  private String title;

  private String details;

  private boolean delivered;

}
