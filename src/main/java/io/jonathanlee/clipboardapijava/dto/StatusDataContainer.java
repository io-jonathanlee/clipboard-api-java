package io.jonathanlee.clipboardapijava.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@AllArgsConstructor
public class StatusDataContainer<T> {

  HttpStatus httpStatus;

  T data;

}
