package io.jonathanlee.clipboardapijava.service.users;

import java.util.concurrent.CompletableFuture;
import org.springframework.mail.SimpleMailMessage;

public interface MailService {

  CompletableFuture<SimpleMailMessage> sendRegistrationVerificationEmail(final String targetEmail,
      final String tokenValue);

  CompletableFuture<SimpleMailMessage> sendPasswordResetEmail(final String targetEmail,
      final String tokenValue);

}
