package com.example.currencyservice.config;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.web.client.RestTemplate;

@Configuration
@PropertySources(@PropertySource("classpath:currencylayearapi.properties")
)
public class WebMvcConfiguration {
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Bean
//    static PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() {
//        return new PropertySourcesPlaceholderConfigurer();
//    }
}
