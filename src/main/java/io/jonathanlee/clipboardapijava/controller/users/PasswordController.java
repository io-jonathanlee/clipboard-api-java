package io.jonathanlee.clipboardapijava.controller.users;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetConfirmRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.users.PasswordResetStatusDto;
import io.jonathanlee.clipboardapijava.service.users.PasswordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/password/reset")
public class PasswordController {

  private final PasswordService passwordService;

  @PostMapping(
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<PasswordResetStatusDto> resetPassword(
      @Valid @RequestBody final PasswordResetRequestDto passwordResetRequestDto) {
    final StatusDataContainer<PasswordResetStatusDto> statusDataContainer =
        this.passwordService.requestPasswordReset(passwordResetRequestDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PostMapping(
      value = "/confirm",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<PasswordResetStatusDto> confirmPasswordReset(
      @Valid @RequestBody final PasswordResetConfirmRequestDto passwordResetConfirmRequestDto) {
    final StatusDataContainer<PasswordResetStatusDto> statusDataContainer =
        this.passwordService.requestConfirmPasswordReset(passwordResetConfirmRequestDto);
    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
