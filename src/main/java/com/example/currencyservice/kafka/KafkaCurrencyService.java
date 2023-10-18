package com.example.currencyservice.kafka;

import com.example.currencyservice.model.CurrencyRate;
import com.example.currencyservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaCurrencyService {
    private final CurrencyRateProducer producer;
    private final CurrencyService currencyService;

    public void send(String fromCurrency, String toCurrency) {
        CurrencyRate rate = currencyService.getExchangeRates(fromCurrency, toCurrency);
        producer.send(rate);
        System.out.println(rate);
    }

    @Scheduled(cron = "* */30 * * * *")
    public void doScheduledSend() {
        send("USD", "RUB");
    }
}
