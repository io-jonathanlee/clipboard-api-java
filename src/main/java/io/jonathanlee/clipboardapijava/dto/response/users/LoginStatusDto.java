package io.jonathanlee.clipboardapijava.dto.response.users;

import io.jonathanlee.clipboardapijava.enums.users.LoginStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginStatusDto {

  private LoginStatus loginStatus;

  private String accessToken;

}
