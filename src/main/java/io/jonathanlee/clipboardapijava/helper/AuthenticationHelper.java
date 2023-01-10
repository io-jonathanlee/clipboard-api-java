package io.jonathanlee.clipboardapijava.helper;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationHelper {

  public static String getCurrentUsername() {
    return (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
  }

}
