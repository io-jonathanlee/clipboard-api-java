package io.jonathanlee.clipboardapijava.helper;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {

  private AuthenticationHelper() {
    // Private constructor to hide the implicit public one
  }

  public static String getCurrentUsername() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
