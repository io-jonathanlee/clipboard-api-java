package io.jonathanlee.clipboardapijava.service;


import io.jonathanlee.clipboardapijava.dto.RegistrationDto;
import io.jonathanlee.clipboardapijava.dto.RegistrationStatusDto;
import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;

public interface RegistrationService {

  StatusDataContainer<RegistrationStatusDto> registerNewUser(final RegistrationDto registrationDto);

  StatusDataContainer<RegistrationStatusDto> confirmNewUserRegistration(final String tokenValue);

}
