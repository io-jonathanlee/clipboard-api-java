package io.jonathanlee.clipboardapijava.dto.request.organization;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RemoveOrganizationAdministratorRequestDto {

  @NotNull
  @Email
  private String administratorToRemoveEmail;

}
