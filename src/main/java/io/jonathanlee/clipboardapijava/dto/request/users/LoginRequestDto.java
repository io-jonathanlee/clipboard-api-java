package io.jonathanlee.clipboardapijava.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginRequestDto {

  private String username;

  private String password;

}
