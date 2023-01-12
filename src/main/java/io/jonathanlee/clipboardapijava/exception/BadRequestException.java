package io.jonathanlee.clipboardapijava.exception;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

  private final String field;

  private final String message;

  public BadRequestException(final String message) {
    super(message);
    this.field = null;
    this.message = message;
  }

  public BadRequestException(final String field, final String message) {
    super(message);
    this.field = field;
    this.message = message;
  }

}
