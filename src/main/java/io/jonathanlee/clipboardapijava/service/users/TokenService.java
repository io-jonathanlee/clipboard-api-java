package io.jonathanlee.clipboardapijava.service.users;

import io.jonathanlee.clipboardapijava.model.users.Token;

public interface TokenService {

  Token generateAndPersistNewValidToken();

  Token generateAndPersistNewExpiredToken();

  Token findByTokenValue(final String tokenValue);

  void expireToken(final Token token);

  void deleteToken(final Token token);

}
