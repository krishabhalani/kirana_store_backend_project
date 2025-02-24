package com.example.kiranafinal.feature_transaction.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Service for producing Kafka messages related to reports.
 * This service determines the appropriate Kafka topic based on the report period
 * and sends the generated report message to the corresponding topic.
 */
@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Constructor to initialize KafkaTemplate.
     *
     * @param kafkaTemplate The Kafka template used to send messages.
     */
    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    /**
     * Sends a report message to the appropriate Kafka topic based on the given period.
     *
     * @param period     The period of the report (e.g., weekly, monthly, yearly).
     * @param reportData The content of the report.
     */
    public void sendReportMessage(String period, String reportData) {
        String topic = determineTopic(period);
        String message = "Report for " + period + ": " + reportData;

        kafkaTemplate.send(topic, message);
        System.out.println("Produced Message to topic [" + topic + "]: " + message);
    }

    /**
     * Determines the Kafka topic based on the report period.
     *
     * @param period The report period (case-insensitive: weekly, monthly, yearly).
     * @return The corresponding Kafka topic.
     * @throws IllegalArgumentException if the period is invalid.
     */
    private String determineTopic(String period) {
        switch (period.toLowerCase()) {
            case "weekly":
                return "weekly-reports";
            case "monthly":
                return "monthly-reports";
            case "yearly":
                return "yearly-reports";
            default:
                throw new IllegalArgumentException("Invalid report period: " + period);
        }
    }
}