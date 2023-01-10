package io.jonathanlee.clipboardapijava.service;

import io.jonathanlee.clipboardapijava.model.Token;

public interface TokenService {

  Token generateAndPersistNewValidToken();

  Token generateAndPersistNewExpiredToken();

  Token findByTokenValue(final String tokenValue);

  void expireToken(final Token token);

}
