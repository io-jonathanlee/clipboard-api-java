package io.jonathanlee.clipboardapijava.service.users;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {

  String extractUsername(final String jwt);

  String generateJwt(UserDetails userDetails);

  boolean isJwtValid(String jwt, UserDetails userDetails);
}
