package com.example.currencyservice.service.currencylayer;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class CurrencyLayerRate {
    private boolean success;
    private CurrencyLayerError error;
    private String terms;
    private String privacy;
    private BigDecimal result;
    private Info info;
    private Map<String, String> currencies;

    @Data
    class Info {
        private long timestamp;
        private BigDecimal quote;
    }
}
