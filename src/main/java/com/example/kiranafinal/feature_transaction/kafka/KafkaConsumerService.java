package com.example.kiranafinal.feature_transaction.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumerService {

    @KafkaListener(topics = {"weekly-reports", "monthly-reports", "yearly-reports"}, groupId = "report-consumers")
    public void consumeReportMessage(String message) {
        System.out.println("Consumed Message: " + message);
        // You can add further processing logic here
    }
}
