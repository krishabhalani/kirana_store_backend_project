package com.example.kiranafinal.feature_OrderProduct.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;
import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductRequest;
import com.example.kiranafinal.feature_OrderProduct.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * Controller for managing order-related operations.
 */
@RestController
@RequestMapping("/v1/api/orders")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    /**
     * Adds a product to the order.
     *
     * @param request The request containing product details.
     * @return The API response with order details.
     */
    @PostMapping("/addToOrder")
    public ResponseEntity<ApiResponse> addToOrder(@RequestBody OrderProductRequest request) {
        String productID = request.getProductId();
        int quantity = request.getQuantity();
        String userID = request.getUserId();
        PaymentStatus paymentStatus = request.getPaymentStatus();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.addToOrder(productID, quantity, userID, paymentStatus));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Completes payment and triggers transaction for an order.
     *
     * @param orderID The ID of the order to checkout.
     * @param paymentStatus The payment status of the order.
     * @return The API response with checkout details.
     */
    @PostMapping("checkoutOrder/{orderID}/{paymentStatus}")
    public ResponseEntity<ApiResponse> checkoutOrder(@PathVariable String orderID,
                                                     @PathVariable PaymentStatus paymentStatus) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.checkoutOrder(orderID, paymentStatus));
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Retrieves details of a specific order.
     *
     * @param orderID The ID of the order.
     * @return The API response with order details if found.
     */
    @GetMapping("getOrderDetails/{orderID}")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable String orderID) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<OrderProductResponse> order = orderProductService.getOrderDetails(orderID);
        order.ifPresent(apiResponse::setData);
        return ResponseEntity.ok(apiResponse);
    }

    /**
     * Lists all orders.
     *
     * @return The API response containing all orders.
     */
    @GetMapping("listAllOrders")
    public ResponseEntity<ApiResponse> listAllOrders() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.listAllOrders());
        return ResponseEntity.ok(apiResponse);
    }
}
