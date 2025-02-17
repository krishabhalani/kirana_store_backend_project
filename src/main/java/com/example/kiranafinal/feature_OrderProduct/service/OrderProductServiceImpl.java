package com.example.kiranafinal.feature_OrderProduct.service;

import com.example.kiranafinal.feature_OrderProduct.dao.OrderProductDAO;
import com.example.kiranafinal.feature_OrderProduct.dto.OrderProductResponse;
import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import com.example.kiranafinal.feature_OrderProduct.model.PaymentStatus;
import com.example.kiranafinal.feature_product.model.Product;
import com.example.kiranafinal.feature_product.repo.ProductRepository;
import com.example.kiranafinal.feature_transaction.dto.TransactionRequest;
import com.example.kiranafinal.feature_transaction.dto.TransactionResponse;
import com.example.kiranafinal.feature_transaction.model.TransactionType;
import com.example.kiranafinal.feature_transaction.service.TransactionService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderProductServiceImpl implements OrderProductService {

    @Autowired
    private OrderProductDAO orderProductDAO;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TransactionService transactionService;

    @Override
    public OrderProductResponse addToOrder(String productID, Integer quantity, String userID, PaymentStatus paymentStatus) {
        System.out.println(" [addToOrder] Received: productID=" + productID + ", quantity=" + quantity + ", userID=" + userID + ", paymentStatus=" + paymentStatus);

        // ✅ Convert String to ObjectId before fetching the product
        Product product = productRepository.findById(productID)
                .orElseThrow(() -> new RuntimeException(" Product not found with ID: " + productID));

        System.out.println("✅ [addToOrder] Found Product: " + product);

        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProductID(productID);
        orderProduct.setUserID(userID);
        orderProduct.setQuantity(quantity);
        orderProduct.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
        orderProduct.setTotalPrice(BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(quantity)));
        orderProduct.setOrderDate(Instant.now());

        //  Set Payment Status from Request
        orderProduct.setPaymentStatus(paymentStatus);

        System.out.println(" [addToOrder] Created OrderProduct: " + orderProduct);

        OrderProduct savedOrder = orderProductDAO.save(orderProduct);

        System.out.println(" [addToOrder] Saved OrderProduct: " + savedOrder);

        return mapToResponse(savedOrder);
    }

    @Override
    public OrderProductResponse checkoutOrder(String orderID, PaymentStatus paymentStatus) {
        System.out.println(" [checkoutOrder] Start: OrderID=" + orderID + ", PaymentStatus=" + paymentStatus);

        OrderProduct order = orderProductDAO.findById(orderID)
                .orElseThrow(() -> new RuntimeException(" Order not found with ID: " + orderID));

        System.out.println(" [checkoutOrder] Found Order: " + order);

        order.setPaymentStatus(paymentStatus);

        if (paymentStatus == PaymentStatus.SUCCESS) {
            System.out.println(" [checkoutOrder] Payment Successful. Creating Transaction...");

            try {
                // 📝 Create Transaction Request
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest.setOrderId(order.getOrderedProductID());
                transactionRequest.setUserId(order.getUserID());
                transactionRequest.setTransactionType(TransactionType.DEBIT);

                System.out.println(" [checkoutOrder] Sending Transaction Request: " + transactionRequest);

                //  Call Transaction Service
                TransactionResponse transactionResponse = transactionService.addTransaction(transactionRequest);

                if (transactionResponse != null) {
                    System.out.println(" [checkoutOrder] Transaction Created Successfully! TransactionID: " + transactionResponse.getTransactionId());
                } else {
                    System.out.println(" [checkoutOrder] Transaction Creation Failed!");
                }
            } catch (Exception e) {
                System.out.println(" [checkoutOrder] Error Creating Transaction: " + e.getMessage());
//                e.printStackTrace();
                throw new RuntimeException("Transaction processing failed."+e.getMessage());
            }
        } else {
            System.out.println(" [checkoutOrder] Payment Not Completed. Skipping Transaction.");
        }

        OrderProduct savedOrder = orderProductDAO.save(order);
        System.out.println(" [checkoutOrder] Updated Order: " + savedOrder);

        return mapToResponse(savedOrder);
    }



    @Override
    public Optional<OrderProductResponse> getOrderDetails(String orderID) {
        return orderProductDAO.findById(orderID).map(this::mapToResponse);
    }

    @Override
    public List<OrderProductResponse> listAllOrders() {
        return orderProductDAO.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    @Override
    public void removeFromOrder(String orderID) {

        orderProductDAO.deleteById(orderID);
    }


    private OrderProductResponse mapToResponse(OrderProduct order) {
        OrderProductResponse orderProductResponse =  new OrderProductResponse();
//                order.getOrderedProductID(),
//                order.getProductID(),  // ✅ Keep as String, DO NOT Convert to UUID
//                order.getQuantity(),
//                order.getUnitPrice(),
//                order.getTotalPrice(),
//                order.getOrderDate()
        orderProductResponse.setProductID(order.getProductID());
        orderProductResponse.setQuantity(order.getQuantity());
        orderProductResponse.setUnitPrice(order.getUnitPrice());
        orderProductResponse.setTotalPrice(order.getTotalPrice());
        orderProductResponse.setOrderDate(order.getOrderDate());
        orderProductResponse.setOrderedProductID(order.getOrderedProductID());
        return orderProductResponse;
    }

}
