package com.microservices.currencyconversionservice.resource;

import com.microservices.currencyconversionservice.util.containerservice.ContainerMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class CurrencyConversionService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyConversionController.class);
    private static final String CURRENCY_EXCHANGE_URL = "/api/currency-exchange-microservice/currency-exchange/from/{from}/to/{to}";

    @Autowired
    private ContainerMetaDataService containerMetaDataService;

    @Value("${CURRENCY_EXCHANGE_URI:http://localhost:8000}")
    private String currencyExchangeHost;

    @Autowired
    private RestTemplate restTemplate;

    public CurrencyConversionModel convertCurrency(String from, String to, BigDecimal quantity) {
        log.info("Received Request to convert from {} {} to {} ", quantity, from, to);

        ResponseEntity<CurrencyConversionModel> responseEntity = restTemplate.getForEntity(
                currencyExchangeHost + CURRENCY_EXCHANGE_URL,
                CurrencyConversionModel.class, createUriVariables(from, to));

        CurrencyConversionModel response = responseEntity.getBody();

        BigDecimal convertedValue = quantity.multiply(response.getConversionMultiple());

        String conversionEnvironmentInfo = containerMetaDataService.retrieveContainerMetadataInfo();

        return new CurrencyConversionModel(response.getId(), from, to, response.getConversionMultiple(), quantity,
                convertedValue, response.getExchangeEnvironmentInfo(), conversionEnvironmentInfo);
    }

    private Map<String, String> createUriVariables(String from, String to) {
        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("from", from);
        uriVariables.put("to", to);
        return uriVariables;
    }
}
