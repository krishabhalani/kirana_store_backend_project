package com.example.kiranafinal.feature_OrderProduct.dao;

import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import com.example.kiranafinal.feature_OrderProduct.repo.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Data Access Object (DAO) for handling order product operations.
 */
@Repository
public class OrderProductDAO {

    @Autowired
    private OrderProductRepository orderProductRepository;

    /**
     * Saves an order product to the database.
     *
     * @param orderProduct The order product to save.
     * @return The saved order product.
     */
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    /**
     * Finds an order product by its ID.
     *
     * @param orderedProductID The ID of the order product.
     * @return An Optional containing the order product if found.
     */
    public Optional<OrderProduct> findById(String orderedProductID) {
        return orderProductRepository.findById(orderedProductID);
    }

    /**
     * Retrieves all order products from the database.
     *
     * @return A list of all order products.
     */
    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    /**
     * Finds order products by transaction ID.
     *
     * @param transactionID The transaction ID associated with the orders.
     * @return A list of order products related to the given transaction ID.
     */
    public List<OrderProduct> findByTransactionId(String transactionID) {
        return orderProductRepository.findByTransactionID(transactionID);
    }

    /**
     * Deletes an order product by its ID.
     *
     * @param orderedProductID The ID of the order product to delete.
     */
    public void deleteById(String orderedProductID) {
        orderProductRepository.deleteById(orderedProductID);
    }
}
