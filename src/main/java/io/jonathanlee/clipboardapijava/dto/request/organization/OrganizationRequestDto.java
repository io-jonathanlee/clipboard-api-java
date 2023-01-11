package io.jonathanlee.clipboardapijava.dto.request.organization;

import io.jonathanlee.clipboardapijava.dto.Constraints;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class OrganizationRequestDto {

  @NotNull
  @Size(min = Constraints.MIN_NAME_LENGTH, max = Constraints.MAX_NAME_LENGTH)
  private String name;

}
