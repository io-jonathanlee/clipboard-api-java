package io.jonathanlee.clipboardapijava.dto.request.users;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequestDto {

  @NotNull
  @Email
  private String email;

}
