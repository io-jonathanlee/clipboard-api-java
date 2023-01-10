package io.jonathanlee.clipboardapijava.service.impl;

import io.jonathanlee.clipboardapijava.model.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.Token;
import io.jonathanlee.clipboardapijava.repository.ApplicationUserRepository;
import io.jonathanlee.clipboardapijava.service.ApplicationUserService;
import io.jonathanlee.clipboardapijava.service.TokenService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationUserServiceImpl implements ApplicationUserService {

  private final ApplicationUserRepository applicationUserRepository;

  private final TokenService tokenService;

  @Override
  public ApplicationUser persistApplicationUser(final ApplicationUser applicationUser) {
    return this.applicationUserRepository.save(applicationUser);
  }

  @Override
  public ApplicationUser findByRegistrationVerificationToken(final Token token) {
    return this.applicationUserRepository.findByRegistrationVerificationToken(token);
  }

  @Override
  public ApplicationUser findByPasswordResetToken(final Token token) {
    return this.applicationUserRepository.findByPasswordResetToken(token);
  }

  @Override
  public ApplicationUser enableUser(final ApplicationUser applicationUser) {
    applicationUser.setEnabled(true);
    return this.applicationUserRepository.save(applicationUser);
  }

  @Override
  public void deleteDisabledApplicationUserByEmail(final String email) {
    final ApplicationUser applicationUserToDelete =
        this.applicationUserRepository.findByEmail(email);
    if (applicationUserToDelete != null && !applicationUserToDelete.isEnabled()) {
      log.info("Deleting associated tokens and disabled user with email: {}", email);
      this.tokenService.deleteToken(applicationUserToDelete.getRegistrationVerificationToken());
      this.tokenService.deleteToken(applicationUserToDelete.getPasswordResetToken());
      this.applicationUserRepository.delete(applicationUserToDelete);
    }

  }

  @Override
  public void deleteApplicationUser(final ApplicationUser applicationUser) {
    if (applicationUser != null) {
      this.applicationUserRepository.delete(applicationUser);
    }
  }

}
