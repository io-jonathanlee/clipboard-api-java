package io.jonathanlee.clipboardapijava.dto;

import io.jonathanlee.clipboardapijava.enums.RegistrationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegistrationStatusDto {

    private RegistrationStatus registrationStatus;

}
