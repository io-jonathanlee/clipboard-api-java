package io.jonathanlee.clipboardapijava.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Collection;

@Data
@AllArgsConstructor
public class ValidationErrorsContainerDto {

  private Collection<ValidationErrorDto> errors;

}
