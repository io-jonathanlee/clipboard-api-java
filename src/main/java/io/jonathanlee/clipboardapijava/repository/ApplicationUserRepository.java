package io.jonathanlee.clipboardapijava.repository;

import io.jonathanlee.clipboardapijava.model.ApplicationUser;
import io.jonathanlee.clipboardapijava.model.Token;
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
