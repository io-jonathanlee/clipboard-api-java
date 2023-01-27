package io.jonathanlee.clipboardapijava.controller.users;

import io.jonathanlee.clipboardapijava.dto.request.users.LoginRequestDto;
import io.jonathanlee.clipboardapijava.dto.response.users.LoginStatusDto;
import io.jonathanlee.clipboardapijava.enums.users.LoginStatus;
import io.jonathanlee.clipboardapijava.service.users.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;

  private final UserDetailsService userDetailsService;

  private final JwtService jwtService;

  @PostMapping(value = "/login",
      produces = MediaType.APPLICATION_JSON_VALUE,
      consumes = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<LoginStatusDto> login(@Valid @RequestBody LoginRequestDto loginRequestDto) {
    this.authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequestDto.getUsername(),
            loginRequestDto.getPassword()
        )
    );
    final UserDetails userDetails = this.userDetailsService.loadUserByUsername(
        loginRequestDto.getUsername());
    return ResponseEntity
        .status(HttpStatus.OK)
        .body(new LoginStatusDto(LoginStatus.SUCCESS, loginRequestDto.getUsername(),
            this.jwtService.generateJwt(userDetails)));
  }

}
