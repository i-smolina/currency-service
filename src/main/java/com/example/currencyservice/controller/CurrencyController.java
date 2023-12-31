package com.example.currencyservice.controller;

import com.example.currencyservice.kafka.KafkaCurrencyService;
import com.example.currencyservice.model.CurrencyRate;
import com.example.currencyservice.service.CurrencyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService currencyRatesService;
    private final KafkaCurrencyService kafkaCurrencyService;

    @GetMapping("currency/rate/{from}/{to}")
    public CurrencyRate getCurrency(@PathVariable String from, @PathVariable String to) {
        return currencyRatesService.getExchangeRates(from, to);
    }

    @GetMapping("currencies")
    public Map<String, String> getCurrencies() {
        return currencyRatesService.getExchangeCurrencies();
    }

    @GetMapping("send/kafka/{from}/{to}")
    public void sendToKafka(@PathVariable String from, @PathVariable String to) {
        kafkaCurrencyService.send(from, to);
    }
}
