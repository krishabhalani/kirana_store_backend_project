package com.example.kiranafinal.feature_OrderProduct.service;

import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderProductService {
    OrderProductResponse addToOrder( String productID, Integer quantity, String userID,PaymentStatus paymentStatus);

    OrderProductResponse checkoutOrder(String orderID, PaymentStatus paymentStatus);

    Optional<OrderProductResponse> getOrderDetails(String orderID);

    List<OrderProductResponse> listAllOrders();

    void removeFromOrder(String orderID);
}
