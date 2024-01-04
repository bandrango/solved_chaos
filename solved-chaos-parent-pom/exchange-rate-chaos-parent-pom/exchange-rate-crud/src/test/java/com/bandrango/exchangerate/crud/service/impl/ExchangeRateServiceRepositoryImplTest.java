package com.bandrango.exchangerate.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.crud.dao.impl.JdbcExchangeRateDaoImpl;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceRepositoryImplTest {

	@InjectMocks
	private JdbcExchangeRateDaoImpl exchangeRateDao;

	@Mock
	private ExchangeRateServiceRepositoryImpl exchangeRateService;

	@Before(value = "")
	public void setUp() throws Exception {
		exchangeRateDao = mock(JdbcExchangeRateDaoImpl.class);
		exchangeRateService = mock(ExchangeRateServiceRepositoryImpl.class);
	}

	@Test
	public void testUpsertExchangeRate() {
		ExchangeRate exchangeRate = new ExchangeRate();
		when(exchangeRateService.upsertExchangeRate(exchangeRate)).thenReturn(1);
		int result = exchangeRateService.upsertExchangeRate(exchangeRate);
		assertEquals(1, result);
	}

	@Test
	public void testDeleteExchangeRate() {
		when(exchangeRateService.deleteExchangeRate(1L)).thenReturn(1);
		int result = exchangeRateService.deleteExchangeRate(1L);
		assertEquals(1, result);
	}

	@Test
	public void testFindAll() {
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		when(exchangeRateService.findAll()).thenReturn(createExchangeRateList());
		List<ExchangeRate> actualExchangeRates = exchangeRateService.findAll();
		assertEquals(expectedExchangeRates, actualExchangeRates);

	}
	
	@Test
	public void testFindBySourceCurrencyAndTargetCurrencyAndEffectiveDate() {
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		Date effectiveStartDate = new Date();
		
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		when(exchangeRateService.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, effectiveStartDate)).thenReturn(createExchangeRateList());
		List<ExchangeRate> actualExchangeRates = exchangeRateService.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(sourceCurrency, targetCurrency, effectiveStartDate);
		assertEquals(expectedExchangeRates.get(0).getId(), actualExchangeRates.get(0).getId());

	}
	
	@Test
	public void testFindBySourceCurrencyAndTargetCurrency() {
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		when(exchangeRateService.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency)).thenReturn(createExchangeRateList());
		List<ExchangeRate> actualExchangeRates = exchangeRateService.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency);
		assertEquals(expectedExchangeRates, actualExchangeRates);

	}

	private List<ExchangeRate> createExchangeRateList() {
		return Arrays.asList(new ExchangeRate(1L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(2L, "USD", "EUR", new BigDecimal("1.3"), new Date()));
	}

}