package com.example.kiranafinal.feature_OrderProduct.dao;

import com.example.kiranafinal.feature_OrderProduct.model.OrderProduct;
import com.example.kiranafinal.feature_OrderProduct.repo.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class OrderProductDAO {

    @Autowired
    private OrderProductRepository orderProductRepository;

//    private static final String ORDER_CACHE = "orderProducts";

    // Save OrderProduct in MongoDB & update cache
//    @CachePut(value = ORDER_CACHE, key = "#orderProduct.orderedProductID")
    public OrderProduct save(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }

    // Fetch OrderProduct by ID (from cache if available)
//    @Cacheable(value = ORDER_CACHE, key = "#orderedProductID")
    public Optional<OrderProduct> findById(String orderedProductID) {  // ✅ Expect String, not UUID
        return orderProductRepository.findById(orderedProductID);
    }


    // Get all orders (No caching for list retrieval)
    public List<OrderProduct> findAll() {
        return orderProductRepository.findAll();
    }

    public List<OrderProduct> findByTransactionId(String transactionID) {  // ✅ Use String
        return orderProductRepository.findByTransactionID(transactionID);
    }

//    // Invalidate cache when deleting an order
//    @CacheEvict(value = ORDER_CACHE, key = "#orderedProductID")
    public void deleteById(String orderedProductID) {
        orderProductRepository.deleteById(orderedProductID);
    }
}
