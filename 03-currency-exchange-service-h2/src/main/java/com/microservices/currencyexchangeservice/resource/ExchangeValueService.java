package com.microservices.currencyexchangeservice.resource;

import com.microservices.currencyexchangeservice.util.containerservice.ContainerMetaDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExchangeValueService {

    private static final Logger log = LoggerFactory.getLogger(CurrencyExchangeController.class);

    @Autowired
    private ExchangeValueRepository repository;

    @Autowired
    private ContainerMetaDataService containerMetaDataService;

    public ExchangeValue exchangeValue(String from, String to, Map<String, String> headers) {
        printAllHeaders(headers);

        ExchangeValue exchangeValue = repository.findByFromAndTo(from, to);

        log.info("{} {} {}", from, to, exchangeValue);

        if (exchangeValue == null) {
            throw new RuntimeException("No such rate for conversion from " + from + " to " + to);
        }

        exchangeValue.setExchangeEnvironmentInfo(containerMetaDataService.retrieveContainerMetadataInfo());
        return exchangeValue;
    }

    private void printAllHeaders(Map<String, String> headers) {
        headers.forEach((key, value) -> {
            log.info(String.format("Header '%s' = %s", key, value));
        });
    }
}
