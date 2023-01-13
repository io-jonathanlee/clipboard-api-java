package io.jonathanlee.clipboardapijava.helper;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

public class AuthenticationHelper {

  private static final String EMAIL_ATTRIBUTE_NAME = "email";

  private AuthenticationHelper() {
    // Private constructor to hide the implicit public one
  }

  public static String getCurrentUsername() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof OAuth2AuthenticationToken) {
      OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) SecurityContextHolder.getContext()
          .getAuthentication();
      return oAuth2AuthenticationToken.getPrincipal().getName();
    }

    JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
    return (String) jwtAuthenticationToken.getTokenAttributes().get(EMAIL_ATTRIBUTE_NAME);
  }

}
