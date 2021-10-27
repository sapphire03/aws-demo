package com.microservices.currencyconversionservice.resource;

import java.math.BigDecimal;

import com.amazonaws.xray.spring.aop.XRayEnabled;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@XRayEnabled
public class CurrencyConversionController {

	@Autowired private CurrencyConversionService currencyConversionService;

	@GetMapping("/currency-converter/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionModel convertCurrency(@PathVariable String from, @PathVariable String to,
												   @PathVariable BigDecimal quantity) {
		return currencyConversionService.convertCurrency(from, to, quantity);
	}
}
