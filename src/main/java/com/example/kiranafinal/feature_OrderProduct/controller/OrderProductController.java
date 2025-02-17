package com.example.kiranafinal.feature_OrderProduct.controller;

import com.example.kiranafinal.common.ApiResponse;
import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;
import com.example.kiranafinal.feature_OrderProduct.model.SomeRequest;
import com.example.kiranafinal.feature_OrderProduct.service.OrderProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/orders")
public class OrderProductController {

    @Autowired
    private OrderProductService orderProductService;

    @PostMapping("/addToOrder")
    public ResponseEntity<ApiResponse> addToOrder(@RequestBody SomeRequest request) {
        String productID = request.getProductId();
        int quantity = request.getQuantity();
        String userID = request.getUserId();
        PaymentStatus paymentStatus = request.getPaymentStatus(); // ✅ Extract PaymentStatus from Request

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.addToOrder(productID, quantity, userID, paymentStatus));
        return ResponseEntity.ok(apiResponse);
    }


    /**
     *  Checkout API - Completes payment and triggers transaction on success.
     */
    @PostMapping("checkoutOrder/{orderID}/{paymentStatus}")
    public ResponseEntity<ApiResponse> checkoutOrder(@PathVariable String orderID,
                                                     @PathVariable PaymentStatus paymentStatus) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.checkoutOrder(orderID, paymentStatus));
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getOrderDetails/{orderID}")
    public ResponseEntity<ApiResponse> getOrderDetails(@PathVariable String orderID) {
        ApiResponse apiResponse = new ApiResponse();
        Optional<OrderProductResponse> order = orderProductService.getOrderDetails(orderID);
        order.ifPresent(apiResponse::setData);
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("listAllOrders")
    public ResponseEntity<ApiResponse> listAllOrders() {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setData(orderProductService.listAllOrders());
        return ResponseEntity.ok(apiResponse);
    }
}
