package com.example.kiranafinal.feature_transaction.kafka;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendReportMessage(String period, String reportData) {
        String topic = determineTopic(period);
        String message = "Report for " + period + ": " + reportData;

        kafkaTemplate.send(topic, message);
        System.out.println("Produced Message to topic [" + topic + "]: " + message);
    }

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
