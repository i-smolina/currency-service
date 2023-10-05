package com.example.currencyservice.service;

import com.example.currencyservice.model.CurrencyRate;
import com.example.currencyservice.service.currencylayer.CurrencyLayerRestClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyService {
    private final CurrencyLayerRestClient currencyLayerRestClient;

    public CurrencyRate getExchangeRates(String fromCurrency, String toCurrency) {
        return currencyLayerRestClient.getCurrencyRateFromRestApi(fromCurrency, toCurrency);
    }

    public Map<String, String> getExchangeCurrencies() {
        return currencyLayerRestClient.getCurrenciesFromRestApi();
    }

}
