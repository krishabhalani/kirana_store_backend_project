package com.example.kiranafinal.feature_OrderProduct.logConstants;

public class LogConstants {
    public static final String RUNTIME_EXCEPTION = "Product not found with ID:";
    public static final String ADD_TO_ORDER_LOG = "[addToOrder] Received: productID=%s, quantity=%d, userID=%s, paymentStatus=%s";
    public static final String CHECKOUT_ORDER_LOG = "[checkoutOrder] Start: OrderID=%s, PaymentStatus=%s";
    public static final String ORDER_NOT_FOUND = "Order not found with ID:";
    public static final String CHECKOUT_ORDER_PAYMENT_SUCCESS = "[checkoutOrder] Payment successful. Creating transaction...";
    public static final String CHECKOUT_ORDER_TRANSACTION_CREATED = "[checkoutOrder] Transaction created successfully. Transaction ID: %s";
    public static final String CHECKOUT_ORDER_TRANSACTION_FAILED = "[checkoutOrder] Transaction creation failed.";
    public static final String CHECKOUT_ORDER_TRANSACTION_ERROR = "[checkoutOrder] Error creating transaction: %s";
    public static final String TRANSACTION_PROCESSING = "Transaction processing failed. " ;
    public static final String PAYMENT_NOT_COMPLETE ="[checkoutOrder] Payment not completed. Skipping transaction.";




}
