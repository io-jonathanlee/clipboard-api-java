package io.jonathanlee.clipboardapijava.controller.users;

import io.jonathanlee.clipboardapijava.dto.RegistrationStatusDto;
import io.jonathanlee.clipboardapijava.dto.StatusDataContainer;
import io.jonathanlee.clipboardapijava.dto.request.users.RegistrationConfirmationDto;
import io.jonathanlee.clipboardapijava.dto.request.users.RegistrationDto;
import io.jonathanlee.clipboardapijava.service.users.RegistrationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

  private final RegistrationService registrationService;

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<Object> registerNewUser(
      @Valid @RequestBody final RegistrationDto registrationDto) {
    final StatusDataContainer<RegistrationStatusDto> statusDataContainer =
        this.registrationService.registerNewUser(registrationDto);

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

  @PostMapping(
      value = "/confirm",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  ResponseEntity<RegistrationStatusDto> confirmNewUserRegistration(
      @Valid @RequestBody final RegistrationConfirmationDto registrationConfirmationDto) {
    log.info("register/confirm with token value: {}", registrationConfirmationDto.getTokenValue());
    final StatusDataContainer<RegistrationStatusDto> statusDataContainer =
        this.registrationService.confirmNewUserRegistration(
            registrationConfirmationDto.getTokenValue());

    return ResponseEntity
        .status(statusDataContainer.getHttpStatus())
        .body(statusDataContainer.getData());
  }

}
