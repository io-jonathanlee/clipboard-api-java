package io.jonathanlee.clipboardapijava.service;


import io.jonathanlee.clipboardapijava.dto.RegistrationDto;
import io.jonathanlee.clipboardapijava.dto.RegistrationStatusDto;

public interface RegistrationService {

  RegistrationStatusDto registerNewUser(final RegistrationDto registrationDto);

  RegistrationStatusDto confirmNewUserRegistration(final String tokenValue);

}
