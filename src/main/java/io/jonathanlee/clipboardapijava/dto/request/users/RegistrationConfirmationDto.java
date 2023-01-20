package io.jonathanlee.clipboardapijava.dto.request.users;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationConfirmationDto {
  
  private String tokenValue;

}
