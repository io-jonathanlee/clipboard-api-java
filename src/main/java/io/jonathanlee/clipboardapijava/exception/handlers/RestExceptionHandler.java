package io.jonathanlee.clipboardapijava.exception.handlers;

import io.jonathanlee.clipboardapijava.dto.response.error.ErrorDto;
import io.jonathanlee.clipboardapijava.exception.BadRequestException;
import io.jonathanlee.clipboardapijava.validation.ValidationErrorDto;
import io.jonathanlee.clipboardapijava.validation.ValidationErrorsContainerDto;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException exception,
      HttpHeaders headers, HttpStatusCode httpStatus, WebRequest request) {
    List<ValidationErrorDto> errors = exception.getBindingResult()
        .getFieldErrors()
        .stream()
        .map(fieldError -> new ValidationErrorDto(
            fieldError.getField(),
            fieldError.getDefaultMessage()
        ))
        .toList();

    final ValidationErrorsContainerDto validationErrorsContainerDto
        = new ValidationErrorsContainerDto(errors);

    return ResponseEntity.status(httpStatus).body(validationErrorsContainerDto);
  }

  @ExceptionHandler(BadRequestException.class)
  protected ResponseEntity<Object> handleBadRequest(
      RuntimeException exception, WebRequest request) {
    final BadRequestException badRequestException = (BadRequestException) exception;
    ValidationErrorsContainerDto validationErrorsContainerDto = null;
    if (badRequestException.getField() != null) {
      validationErrorsContainerDto =
          new ValidationErrorsContainerDto(
              List.of(new ValidationErrorDto(badRequestException.getField(),
                  badRequestException.getMessage()))
          );
    }
    return handleExceptionInternal(exception, (validationErrorsContainerDto == null) ?
            new ErrorDto(badRequestException.getMessage()) :
            validationErrorsContainerDto,
        new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
  }

}
