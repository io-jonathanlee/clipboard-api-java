package io.jonathanlee.clipboardapijava.service.users;


import io.jonathanlee.clipboardapijava.model.users.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.users.Token;

public interface ApplicationUserService {

  boolean doesApplicationUserExist(final String email);

  ApplicationUser persistApplicationUser(final ApplicationUser applicationUser);

  ApplicationUser findByRegistrationVerificationToken(final Token token);

  ApplicationUser findByPasswordResetToken(final Token token);

  ApplicationUser enableUser(final ApplicationUser applicationUser);

  void deleteDisabledApplicationUserByEmail(final String email);

  ApplicationUser getApplicationUserByEmail(final String email);

}
