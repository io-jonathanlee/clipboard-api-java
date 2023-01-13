package io.jonathanlee.clipboardapijava.controller.users;

import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @GetMapping
  public ResponseEntity<Object> redirect(HttpServletResponse response) throws IOException {
    response.sendRedirect("http://localhost:4200");
    return ResponseEntity.status(HttpStatus.PERMANENT_REDIRECT).build();
  }

}
