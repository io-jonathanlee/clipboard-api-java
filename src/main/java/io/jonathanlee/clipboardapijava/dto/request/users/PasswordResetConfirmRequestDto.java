package io.jonathanlee.clipboardapijava.dto.request.users;

import io.jonathanlee.clipboardapijava.dto.Constraints;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetConfirmRequestDto {

  @NotNull
  private String tokenValue;

  @NotNull
  @Size(min = Constraints.MIN_PASSWORD_LENGTH,
      max = Constraints.MAX_PASSWORD_LENGTH)
  private String password;

  @NotNull
  @Size(min = Constraints.MIN_PASSWORD_LENGTH,
      max = Constraints.MAX_PASSWORD_LENGTH)
  private String confirmPassword;

}
