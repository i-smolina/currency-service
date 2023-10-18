package com.example.currencyservice.kafka;

import com.example.currencyservice.model.CurrencyRate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrencyRateProducer {
    @Value(value = "${currency.topic.name}")
    private String topicName;

    private final KafkaTemplate<String, CurrencyRate> kafkaTemplate;

    public void send(CurrencyRate rate) {
        kafkaTemplate.send(topicName, rate);
        System.out.println("Sent rate " + rate.getRate());
    }
}
