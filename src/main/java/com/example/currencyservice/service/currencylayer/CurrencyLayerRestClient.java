package com.example.currencyservice.service.currencylayer;

import com.example.currencyservice.model.CurrencyRate;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CurrencyLayerRestClient {
    private final RestTemplate restTemplate;
    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyLayerRestClient.class);

    @Value("${currencylayer.service.url}")
    private String serviceUrl;
    @Value("${currencylayer.convert.resource}")
    private String convertResource;
    @Value("${currencylayer.list.resource}")
    private String listResource;
    @Value("${currencylayer.api.key}")
    private String apiKey;

    public CurrencyRate getCurrencyRateFromRestApi(String fromCurrency, String toCurrency) {
        String uri = buildServiceUriConvert(fromCurrency, toCurrency);
        CurrencyLayerRate currencyLayerRates = getCurrencyLayerRateFromRestApi(uri);

        CurrencyRate currencyRates = new CurrencyRate();
        currencyRates.setFromCurrency(fromCurrency);
        currencyRates.setToCurrency(toCurrency);
        currencyRates.setTimestamp(currencyLayerRates.getInfo().getTimestamp());
        currencyRates.setRate(currencyLayerRates.getResult());
        return currencyRates;
    }

    public Map<String, String> getCurrenciesFromRestApi() {
        String uri = buildServiceUriList();
        CurrencyLayerRate currencyLayerRate = getCurrencyLayerRateFromRestApi(uri);

        return new LinkedHashMap<>(currencyLayerRate.getCurrencies());
    }

    private CurrencyLayerRate getCurrencyLayerRateFromRestApi(String uri) {
        CurrencyLayerRate currencyLayerRates;
        try {
            currencyLayerRates = restTemplate.getForObject(uri, CurrencyLayerRate.class);
            if (!currencyLayerRates.isSuccess()) {
                String formattedError = "CurrencyLayer API Error "
                        + currencyLayerRates.getError().getCode() + " "
                        + currencyLayerRates.getError().getInfo();
                LOGGER.error(formattedError);
                throw new RuntimeException(formattedError);
            }
            return currencyLayerRates;
        } catch (RestClientException e) {
            LOGGER.error(e.toString());
            throw e;
        }
    }

    private String buildServiceUriConvert(String fromCurrency, String toCurrency) {
        String template = "%s%s?access_key=%s&from=%s&to=%s&amount=%d";
        return String.format(template,
                serviceUrl, convertResource, apiKey, fromCurrency, toCurrency, 1);
    }

    private String buildServiceUriList() {
        String template = "%s%s?access_key=%s";
        return String.format(template,
                serviceUrl, listResource, apiKey);
    }
}
