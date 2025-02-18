package com.example.kiranafinal.feature_OrderProduct.service;

import com.example.kiranafinal.feature_OrderProduct.dao.OrderProductDAO;
import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.logConstants.LogConstants;
import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import com.example.kiranafinal.feature_OrderProduct.enums.PaymentStatus;
import com.example.kiranafinal.feature_product.model.Product;
import com.example.kiranafinal.feature_product.repo.ProductRepository;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;
import com.example.kiranafinal.feature_transaction.enums.TransactionType;
import com.example.kiranafinal.feature_transaction.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.example.kiranafinal.feature_OrderProduct.logConstants.LogConstants.*;

/**
 * Service implementation for managing product orders.
 */
@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionService transactionService;

    /**
     * Adds a product to an order.
     *
     * @param productID     The ID of the product to be added.
     * @param quantity      The quantity of the product.
     * @param userID        The ID of the user placing the order.
     * @param paymentStatus The payment status for the order.
     * @return The response containing order details.
     */
    @Override
    public OrderProductResponse addToOrder(String productID, Integer quantity, String userID, PaymentStatus paymentStatus) {
        System.out.println(String.format(LogConstants.ADD_TO_ORDER_LOG, productID, quantity, userID, paymentStatus));

        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException(RUNTIME_EXCEPTION + productID));

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductID(productID);
        orderProduct.setUserID(userID);
        orderProduct.setQuantity(quantity);
        orderProduct.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
        orderProduct.setTotalPrice(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        orderProduct.setOrderDate(Instant.now());
        orderProduct.setPaymentStatus(paymentStatus);

        OrderProduct savedOrder = orderProductDAO.save(orderProduct);
        return mapToResponse(savedOrder);
    }

    /**
     * Processes checkout for an order.
     *
     * @param orderID       The ID of the order to be checked out.
     * @param paymentStatus The payment status of the order.
     * @return The response containing updated order details.
     * @throws RuntimeException if the transaction fails.
     */
    @Override
    public OrderProductResponse checkoutOrder(String orderID, PaymentStatus paymentStatus) {
        System.out.println(String.format(LogConstants.CHECKOUT_ORDER_LOG, orderID, paymentStatus));
        OrderProduct order = orderProductDAO.findById(orderID)
                .orElseThrow(() -> new RuntimeException(ORDER_NOT_FOUND + orderID));

        order.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {
            System.out.println(LogConstants.CHECKOUT_ORDER_PAYMENT_SUCCESS);
            try {
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setOrderId(order.getOrderedProductID());
                transactionRequest.setUserId(order.getUserID());
                transactionRequest.setTransactionType(TransactionType.DEBIT);

                TransactionResponse transactionResponse = transactionService.addTransaction(transactionRequest);

                if (transactionResponse != null) {
                    System.out.println(String.format(LogConstants.CHECKOUT_ORDER_TRANSACTION_CREATED, transactionResponse.getTransactionId()));
                } else {
                    System.out.println(LogConstants.CHECKOUT_ORDER_TRANSACTION_FAILED);
                }
            } catch (Exception e) {
                System.out.printf((LogConstants.CHECKOUT_ORDER_TRANSACTION_ERROR) + "%n", e.getMessage());
                throw new RuntimeException(TRANSACTION_PROCESSING + e.getMessage());
            }
        } else {
            System.out.println(PAYMENT_NOT_COMPLETE);
        }

        OrderProduct savedOrder = orderProductDAO.save(order);
        return mapToResponse(savedOrder);
    }

    /**
     * Retrieves order details by order ID.
     *
     * @param orderID The ID of the order.
     * @return An Optional containing the order details if found.
     */
    @Override
    public Optional<OrderProductResponse> getOrderDetails(String orderID) {
        return orderProductDAO.findById(orderID).map(this::mapToResponse);
    }

    /**
     * Lists all orders.
     *
     * @return A list of all orders.
     */
    @Override
    public List<OrderProductResponse> listAllOrders() {
        return orderProductDAO.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    /**
     * Removes an order by order ID.
     *
     * @param orderID The ID of the order to be removed.
     */
    @Override
    public void removeFromOrder(String orderID) {
        orderProductDAO.deleteById(orderID);
    }

    /**
     * Maps an OrderProduct entity to an OrderProductResponse DTO.
     *
     * @param order The order entity.
     * @return The response containing order details.
     */
    private OrderProductResponse mapToResponse(OrderProduct order) {
        OrderProductResponse orderProductResponse = new OrderProductResponse();
        orderProductResponse.setProductID(order.getProductID());
        orderProductResponse.setQuantity(order.getQuantity());
        orderProductResponse.setUnitPrice(order.getUnitPrice());
        orderProductResponse.setTotalPrice(order.getTotalPrice());
        orderProductResponse.setOrderDate(order.getOrderDate());
        orderProductResponse.setOrderedProductID(order.getOrderedProductID());
        return orderProductResponse;
    }
}
