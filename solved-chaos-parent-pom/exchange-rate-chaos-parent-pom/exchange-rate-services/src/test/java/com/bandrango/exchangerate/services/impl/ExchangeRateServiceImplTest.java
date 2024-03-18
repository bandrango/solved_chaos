package com.bandrango.exchangerate.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.crud.service.ExchangeRateServiceRepository;
import com.bandrango.exchangerate.services.impl.util.ExchangeRateUtlis;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest extends ExchangeRateUtlis {

	@InjectMocks
	private ExchangeRateServiceImpl exchangeRateService;

	@Mock
	ExchangeRateServiceRepository exchangeRateRepository;

	@Before(value = "")
	public void setUp() throws Exception {
		exchangeRateService = mock(ExchangeRateServiceImpl.class);
		exchangeRateRepository = mock(ExchangeRateServiceRepository.class);
	}
	
	@Test
	public void testGetAllExchangeRates() {
		Date effectiveStartDate = new Date();
		List<ExchangeRate> mockExchangeRates = createExchangeRateList();
		when(exchangeRateRepository.findExchangeRatesByEffectiveDate(effectiveStartDate)).thenReturn(mockExchangeRates);
		List<ExchangeRate> result = exchangeRateService.getAllExchangeRates();
		verify(exchangeRateRepository, times(1)).findExchangeRatesByEffectiveDate(effectiveStartDate);
		assertEquals(mockExchangeRates, result);
	}

	@Test
	public void testUpsertExchangeRate() {
		ExchangeRate exchangeRate = new ExchangeRate();
		when(exchangeRateRepository.upsertExchangeRate(exchangeRate)).thenReturn(1);
		int result = exchangeRateService.upsertExchangeRate(exchangeRate);
		verify(exchangeRateRepository, times(1)).upsertExchangeRate(exchangeRate);
		assertEquals(1, result);
	}

	public void testDeleteExchangeRate() {
		Long exchangeRateId = 1L;
		when(exchangeRateRepository.deleteExchangeRate(exchangeRateId)).thenReturn(1);
		int result = exchangeRateService.deleteExchangeRate(exchangeRateId);
		verify(exchangeRateRepository, times(1)).deleteExchangeRate(exchangeRateId);
		assertEquals(1, result);
	}

	@Test
	public void testGetExchangeRate_DirectRate() {
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		Date effectiveStartDate = new Date();

		when(exchangeRateRepository.findExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate))
				.thenReturn(createExchangeRate());
		Double result = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);

		assertEquals(1.2, result, 0.0001);
	}

	@Test
	public void testGetExchangeRate_WithIntermediateCurrency() {
		String sourceCurrency = "EUR";
		String targetCurrency = "GBP";
		Date effectiveStartDate = new Date();

		when(exchangeRateRepository.findExchangeRatesByEffectiveDate(effectiveStartDate)).thenReturn(createExchangeRateList());
		Double result = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);

		assertEquals(0.8, result, 0.0001);
	}

}