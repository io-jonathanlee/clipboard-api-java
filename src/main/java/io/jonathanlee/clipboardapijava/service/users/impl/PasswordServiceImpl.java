package io.jonathanlee.clipboardapijava.service.users.impl;

import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetConfirmRequestDto;
import io.jonathanlee.clipboardapijava.dto.request.users.PasswordResetRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.users.PasswordResetStatusDto;
import io.jonathanlee.clipboardapijava.enums.users.PasswordResetStatus;
import io.jonathanlee.clipboardapijava.exception.BadRequestException;
import io.jonathanlee.clipboardapijava.model.users.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.users.Token;
import io.jonathanlee.clipboardapijava.service.users.ApplicationUserService;
import io.jonathanlee.clipboardapijava.service.users.MailService;
import io.jonathanlee.clipboardapijava.service.users.PasswordService;
import io.jonathanlee.clipboardapijava.service.users.TokenService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Password service used to handle password reset requests.
 */
@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

  private final ApplicationUserService applicationUserService;

  private final TokenService tokenService;

  private final MailService mailService;

  private final PasswordEncoder passwordEncoder;

  @Override
  public StatusDataContainer<PasswordResetStatusDto> requestPasswordReset(
      final PasswordResetRequestDto passwordResetRequestDto) {
    final ApplicationUser applicationUser = this.applicationUserService.getApplicationUserByEmail(
        passwordResetRequestDto.getEmail());
    if (applicationUser == null || !applicationUser.isEnabled()) {
      return new StatusDataContainer<>(HttpStatus.OK,
          new PasswordResetStatusDto(PasswordResetStatus.AWAITING_EMAIL_VERIFICATION));
    }
    this.tokenService.deleteToken(applicationUser.getPasswordResetToken());
    final Token passwordResetToken = this.tokenService.generateAndPersistNewValidToken();
    applicationUser.setPasswordResetToken(passwordResetToken);
    this.applicationUserService.persistApplicationUser(applicationUser);
    this.mailService.sendPasswordResetEmail(passwordResetRequestDto.getEmail(),
        passwordResetToken.getValue());
    return new StatusDataContainer<>(HttpStatus.OK,
        new PasswordResetStatusDto(PasswordResetStatus.AWAITING_EMAIL_VERIFICATION));
  }

  @Override
  public StatusDataContainer<PasswordResetStatusDto> requestConfirmPasswordReset(
      final PasswordResetConfirmRequestDto passwordResetConfirmRequestDto) {
    final Token passwordResetToken = this.tokenService.findByTokenValue(
        passwordResetConfirmRequestDto.getTokenValue());
    if (passwordResetToken == null) {
      return new StatusDataContainer<>(HttpStatus.OK,
          new PasswordResetStatusDto(PasswordResetStatus.INVALID_TOKEN));
    }
    if (passwordResetToken.getExpiryDate().isBefore(Instant.now())) {
      return new StatusDataContainer<>(HttpStatus.OK,
          new PasswordResetStatusDto(PasswordResetStatus.EMAIL_VERIFICATION_EXPIRED));
    }
    if (!passwordResetConfirmRequestDto.getPassword()
        .equals(passwordResetConfirmRequestDto.getConfirmPassword())) {
      throw new BadRequestException("password", "Passwords must match");
    }
    final ApplicationUser applicationUser = this.applicationUserService.findByPasswordResetToken(
        passwordResetToken);
    if (applicationUser == null || !applicationUser.isEnabled()) {
      return new StatusDataContainer<>(HttpStatus.OK,
          new PasswordResetStatusDto(PasswordResetStatus.FAILURE));
    }
    applicationUser.setPassword(this.passwordEncoder.encode(
        passwordResetConfirmRequestDto.getPassword()));
    this.applicationUserService.persistApplicationUser(applicationUser);
    return new StatusDataContainer<>(HttpStatus.OK,
        new PasswordResetStatusDto(PasswordResetStatus.SUCCESS));
  }

}
