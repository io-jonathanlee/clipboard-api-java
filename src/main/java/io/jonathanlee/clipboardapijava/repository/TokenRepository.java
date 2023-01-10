package io.jonathanlee.clipboardapijava.repository;

import io.jonathanlee.clipboardapijava.model.Token;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.lang.Nullable;

public interface TokenRepository extends MongoRepository<Token, ObjectId> {

    @Nullable
    Token findByValue(final String value);

}
