package io.jonathanlee.clipboardapijava.helper;

import io.jonathanlee.clipboardapijava.model.users.ApplicationUser;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {

  private AuthenticationHelper() {
    // Private constructor to hide the implicit public one
  }

  public static String getCurrentUsername() {
    return ((ApplicationUser) SecurityContextHolder.getContext().getAuthentication()
        .getPrincipal()).getUsername();
  }

}
