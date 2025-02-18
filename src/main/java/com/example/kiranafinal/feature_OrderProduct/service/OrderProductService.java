package com.example.kiranafinal.feature_OrderProduct.service;

import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for handling order-related operations.
 */
public interface OrderProductService {

    /**
     * Adds a product to an order.
     *
     * @param productID     The ID of the product to be added.
     * @param quantity      The quantity of the product.
     * @param userID        The ID of the user placing the order.
     * @param paymentStatus The payment status of the order.
     * @return The details of the added order.
     */
    OrderProductResponse addToOrder(String productID, Integer quantity, String userID, PaymentStatus paymentStatus);

    /**
     * Processes the checkout for an order.
     *
     * @param orderID       The ID of the order to be checked out.
     * @param paymentStatus The updated payment status.
     * @return The updated order details after checkout.
     */
    OrderProductResponse checkoutOrder(String orderID, PaymentStatus paymentStatus);

    /**
     * Retrieves details of a specific order.
     *
     * @param orderID The ID of the order to retrieve.
     * @return An Optional containing the order details if found.
     */
    Optional<OrderProductResponse> getOrderDetails(String orderID);

    /**
     * Lists all orders.
     *
     * @return A list of all orders.
     */
    List<OrderProductResponse> listAllOrders();

    /**
     * Removes an order.
     *
     * @param orderID The ID of the order to be removed.
     */
    void removeFromOrder(String orderID);
}
