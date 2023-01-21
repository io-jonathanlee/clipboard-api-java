package io.jonathanlee.clipboardapijava.service.users.impl;

import io.jonathanlee.clipboardapijava.service.users.MailService;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

  private final JavaMailSender javaMailSender;
  @Value("${clipboard.environment.frontEndHost}")
  private String frontEndHost;

  @Async
  @Override
  public CompletableFuture<SimpleMailMessage> sendRegistrationVerificationEmail(
      final String targetEmail,
      final String tokenValue) {
    final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(targetEmail);
    simpleMailMessage.setSubject("Clipboard E-mail Verification");
    simpleMailMessage.setText(
        String.format("Please click the following link to verify your account: %s",
            String.format("%s/register/confirm/%s", frontEndHost, tokenValue)))
    ;

    try {
      this.javaMailSender.send(simpleMailMessage);
      log.info("Registration verification e-mail sent to: {}", targetEmail);
      return CompletableFuture.completedFuture(simpleMailMessage);
    } catch (MailException mailException) {
      log.error("Registration verification e-mail could not be sent: {}",
          mailException.getMessage());
      return CompletableFuture.completedFuture(null);
    }
  }

  @Async
  @Override
  public CompletableFuture<SimpleMailMessage> sendPasswordResetEmail(final String targetEmail,
      final String tokenValue) {
    final SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
    simpleMailMessage.setTo(targetEmail);
    simpleMailMessage.setSubject("Clipboard Password Reset");
    simpleMailMessage.setText(
        String.format("Please click the following link to reset your password: %s",
            String.format("%s/password/reset/%s", frontEndHost, tokenValue))
    );

    try {
      this.javaMailSender.send(simpleMailMessage);
      log.info("Password reset verification e-mail sent to: {}", targetEmail);
      return CompletableFuture.completedFuture(simpleMailMessage);
    } catch (MailException mailException) {
      log.error("Password reset e-mail could not be sent: {}", mailException.getMessage());
      return CompletableFuture.completedFuture(null);
    }
  }

}
