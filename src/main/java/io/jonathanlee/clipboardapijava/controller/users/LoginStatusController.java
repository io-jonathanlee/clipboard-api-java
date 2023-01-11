package io.jonathanlee.clipboardapijava.controller.users;

import io.jonathanlee.clipboardapijava.dto.response.users.LoginStatusDto;
import io.jonathanlee.clipboardapijava.enums.users.LoginStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login-status")
public class LoginStatusController {

  @PostMapping(value = "/success",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginStatusDto> loginSuccess() {
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new LoginStatusDto(LoginStatus.SUCCESS));
  }

  @PostMapping(value = "/failure",
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<LoginStatusDto> loginFailure() {
    return ResponseEntity
        .status(HttpStatus.UNAUTHORIZED)
        .body(new LoginStatusDto(LoginStatus.FAILURE));
  }

}
