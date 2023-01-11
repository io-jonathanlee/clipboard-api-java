package io.jonathanlee.clipboardapijava.repository.users;

import io.jonathanlee.clipboardapijava.model.users.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.users.Token;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;

public interface ApplicationUserRepository extends MongoRepository<ApplicationUser, ObjectId> {

  @Nullable
  ApplicationUser findByEmail(final String email);

  @Nullable
  ApplicationUser findByRegistrationVerificationToken(final Token token);

  @Nullable
  ApplicationUser findByPasswordResetToken(final Token token);

}
