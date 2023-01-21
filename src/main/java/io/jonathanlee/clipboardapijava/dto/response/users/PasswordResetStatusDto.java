package io.jonathanlee.clipboardapijava.dto.response.users;

import io.jonathanlee.clipboardapijava.enums.users.PasswordResetStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PasswordResetStatusDto {

  private PasswordResetStatus status;

}
