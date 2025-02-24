package com.example.kiranafinal.feature_transaction.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * Service to consume messages from Kafka topics related to reports.
 * This consumer listens to weekly, monthly, and yearly reports.
 */
@Service
public class KafkaConsumerService {

    /**
     * Listens to Kafka topics: "weekly-reports", "monthly-reports", and "yearly-reports".
     * Consumed messages are printed to the console.
     *
     * @param message The message received from Kafka topic.
     */
    @KafkaListener(topics = {"weekly-reports", "monthly-reports", "yearly-reports"}, groupId = "report-consumers")
    public void consumeReportMessage(String message) {
        System.out.println("Consumed Message: " + message);

    }
}
