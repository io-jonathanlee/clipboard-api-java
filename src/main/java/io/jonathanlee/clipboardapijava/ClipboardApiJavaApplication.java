package io.jonathanlee.clipboardapijava;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ClipboardApiJavaApplication {

  public static void main(String[] args) {
    SpringApplication.run(ClipboardApiJavaApplication.class, args);
  }

}
