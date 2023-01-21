package io.jonathanlee.clipboardapijava.service.users;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetConfirmRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.users.PasswordResetStatusDto;

public interface PasswordService {

  StatusDataContainer<PasswordResetStatusDto> requestPasswordReset(
      PasswordResetRequestDto passwordResetRequestDto);

  StatusDataContainer<PasswordResetStatusDto> requestConfirmPasswordReset(
      PasswordResetConfirmRequestDto passwordResetConfirmRequestDto);

}
