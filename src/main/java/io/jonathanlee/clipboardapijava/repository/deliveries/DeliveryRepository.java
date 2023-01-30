package io.jonathanlee.clipboardapijava.repository.deliveries;

import io.jonathanlee.clipboardapijava.model.deliveries.Delivery;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DeliveryRepository extends MongoRepository<Delivery, ObjectId> {

  List<Delivery> findDeliveriesByOrganizationIdIsAndAssignedDriverEmailIs(
      final String organizationId, final String assignedDriverEmail);

  List<Delivery> findDeliveriesByCreatorEmailIs(final String creatorEmail);

  Optional<Delivery> findById(final String deliveryId);
}
