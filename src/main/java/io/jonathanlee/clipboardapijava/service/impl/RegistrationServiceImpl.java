package io.jonathanlee.clipboardapijava.service.impl;

import io.jonathanlee.clipboardapijava.dto.RegistrationDto;
import io.jonathanlee.clipboardapijava.dto.RegistrationStatusDto;
import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.enums.RegistrationStatus;
import io.jonathanlee.clipboardapijava.model.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.Token;
import io.jonathanlee.clipboardapijava.service.ApplicationUserService;
import io.jonathanlee.clipboardapijava.service.MailService;
import io.jonathanlee.clipboardapijava.service.RandomService;
import io.jonathanlee.clipboardapijava.service.RegistrationService;
import io.jonathanlee.clipboardapijava.service.TokenService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

  private final RandomService randomService;

  private final PasswordEncoder passwordEncoder;

  private final TokenService tokenService;

  private final ApplicationUserService applicationUserService;

  private final MailService mailService;

  @Override
  public StatusDataContainer<RegistrationStatusDto> registerNewUser(
      final RegistrationDto registrationDto) {
    if (!registrationDto.getPassword().equals(registrationDto.getConfirmPassword())) {
      return new StatusDataContainer<>(
          HttpStatus.BAD_REQUEST,
          new RegistrationStatusDto(RegistrationStatus.PASSWORDS_DO_NOT_MATCH)
      );
    }

    final ApplicationUser newApplicationUser = new ApplicationUser(
        ObjectId.get(),
        this.randomService.generateNewId(),
        registrationDto.getEmail(),
        this.passwordEncoder.encode(registrationDto.getPassword()),
        registrationDto.getFirstName(),
        registrationDto.getLastName(),
        false,
        this.tokenService.generateAndPersistNewValidToken(),
        this.tokenService.generateAndPersistNewExpiredToken()
    );

    this.applicationUserService.deleteDisabledApplicationUserByEmail(registrationDto.getEmail());

    final ApplicationUser savedApplicationUser = this.applicationUserService.persistApplicationUser(
        newApplicationUser);
    this.mailService.sendRegistrationVerificationEmail(
        savedApplicationUser.getEmail(),
        newApplicationUser.getRegistrationVerificationToken().getValue());
    log.info("Awaiting e-mail verification for user with e-mail: {}",
        savedApplicationUser.getEmail());
    return new StatusDataContainer<>(
        HttpStatus.OK,
        new RegistrationStatusDto(RegistrationStatus.AWAITING_EMAIL_VERIFICATION)
    );
  }

  @Override
  public StatusDataContainer<RegistrationStatusDto> confirmNewUserRegistration(
      final String tokenValue) {
    final Token registrationVerificationToken = this.tokenService.findByTokenValue(tokenValue);
    if (registrationVerificationToken == null) {
      return new StatusDataContainer<>(HttpStatus.BAD_REQUEST,
          new RegistrationStatusDto(RegistrationStatus.INVALID_TOKEN));
    }

    if (!registrationVerificationToken.getExpiryDate().isBefore(Instant.now())) {
      final ApplicationUser applicationUserToEnable = this.applicationUserService
          .findByRegistrationVerificationToken(registrationVerificationToken);
      final ApplicationUser updatedUser = this.applicationUserService.enableUser(
          applicationUserToEnable);
      this.tokenService.expireToken(registrationVerificationToken);
      if (updatedUser.isEnabled()) {
        log.info("Successfully confirmed registration for user with e-mail: {}",
            updatedUser.getEmail());
        return new StatusDataContainer<>(
            HttpStatus.OK,
            new RegistrationStatusDto(RegistrationStatus.SUCCESS)
        );
      }
    } else {
      return new StatusDataContainer<>(
          HttpStatus.BAD_REQUEST,
          new RegistrationStatusDto(RegistrationStatus.EMAIL_VERIFICATION_EXPIRED)
      );
    }

    log.error("Internal server error when confirm registration using token value: {}",
        tokenValue);
    return new StatusDataContainer<>(
        HttpStatus.INTERNAL_SERVER_ERROR,
        new RegistrationStatusDto(RegistrationStatus.FAILURE)
    );
  }

}
