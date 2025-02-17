package com.example.kiranafinal.feature_OrderProduct.repo;

import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;
import java.util.UUID;

public interface OrderProductRepository extends MongoRepository<OrderProduct, String> {
    OrderProduct findByorderedProductID(ObjectId orderedProductID);
    List<OrderProduct> findByTransactionID(String transactionID);
}
