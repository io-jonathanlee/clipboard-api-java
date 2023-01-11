package io.jonathanlee.clipboardapijava.service.users;


import io.jonathanlee.clipboardapijava.dto.RegistrationStatusDto;
import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.users.RegistrationDto;

public interface RegistrationService {

  StatusDataContainer<RegistrationStatusDto> registerNewUser(final RegistrationDto registrationDto);

  StatusDataContainer<RegistrationStatusDto> confirmNewUserRegistration(final String tokenValue);

}
