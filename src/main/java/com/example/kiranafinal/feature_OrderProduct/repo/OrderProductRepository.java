package com.example.kiranafinal.feature_OrderProduct.repo;

import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

/**
 * Repository interface for managing OrderProduct entities in MongoDB.
 */
public interface OrderProductRepository extends MongoRepository<OrderProduct, String> {

    /**
     * Finds an OrderProduct by its ordered product ID.
     *
     * @param orderedProductID The unique ID of the ordered product.
     * @return The OrderProduct object if found.
     */
    OrderProduct findByorderedProductID(ObjectId orderedProductID);

    /**
     * Finds a list of OrderProducts by transaction ID.
     *
     * @param transactionID The transaction ID associated with the order.
     * @return A list of OrderProduct objects linked to the given transaction ID.
     */
    List<OrderProduct> findByTransactionID(String transactionID);
}
