package com.bandrango.exchangerate.crud.dao.impl.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;

public class ExchangeRateUtlis {

	public List<ExchangeRate> createExchangeRateList() {
		return Arrays.asList(
					ExchangeRate.builder()
					.id(1L)
					.sourceCurrency("USD")
					.targetCurrency("EUR")
					.exchangeRate(new BigDecimal(1.2))
					.effectiveStartDate(new Date())
					.build(),
					
					ExchangeRate.builder()
					.id(1L)
					.sourceCurrency("USD")
					.targetCurrency("EUR")
					.exchangeRate(new BigDecimal(1.3))
					.effectiveStartDate(new Date())
					.build(),
					
					ExchangeRate.builder()
					.id(1L)
					.sourceCurrency("EUR")
					.targetCurrency("USD")
					.exchangeRate(new BigDecimal(1.6))
					.effectiveStartDate(new Date())
					.build(),
					
					ExchangeRate.builder()
					.id(1L)
					.sourceCurrency("USD")
					.targetCurrency("GBP")
					.exchangeRate(new BigDecimal(0.5))
					.effectiveStartDate(new Date())
					.build()
		);
	}
	
	public ExchangeRate createExchangeRate() {
		return ExchangeRate.builder()
				.id(1L)
				.sourceCurrency("USD")
				.targetCurrency("EUR")
				.exchangeRate(new BigDecimal(1.2))
				.effectiveStartDate(new Date())
				.build();
	}
}