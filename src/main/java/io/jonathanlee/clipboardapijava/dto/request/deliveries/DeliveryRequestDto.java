package io.jonathanlee.clipboardapijava.dto.request.deliveries;

import io.jonathanlee.clipboardapijava.dto.Constraints;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryRequestDto {

  @NotNull
  @Size(min = Constraints.MIN_NAME_LENGTH,
      max = Constraints.MAX_NAME_LENGTH)
  private String title;
  @NotNull
  @Size(min = Constraints.MIN_DETAILS_LENGTH,
      max = Constraints.MAX_DETAILS_LENGTH)
  private String details;
  @Email
  private String assignedDriverEmail;
  @NotNull
  private boolean delivered;

}
