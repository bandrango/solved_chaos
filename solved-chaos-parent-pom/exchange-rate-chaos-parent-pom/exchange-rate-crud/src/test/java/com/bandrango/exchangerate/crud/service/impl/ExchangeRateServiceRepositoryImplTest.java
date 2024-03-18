package com.bandrango.exchangerate.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
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
import com.bandrango.exchangerate.crud.dao.impl.JdbcExchangeRateDaoImpl;
import com.bandrango.exchangerate.crud.dao.impl.util.ExchangeRateUtlis;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceRepositoryImplTest extends ExchangeRateUtlis {

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
	public void testFindExchangeRate() {
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		Date effectiveStartDate = new Date();
		
		ExchangeRate expectedExchangeRates = createExchangeRate();
		when(exchangeRateService.findExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate)).thenReturn(createExchangeRate());
		ExchangeRate actualExchangeRates = exchangeRateService.findExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);
		assertEquals(expectedExchangeRates.getId(), actualExchangeRates.getId());

	}
	
	@Test
	public void testFindBySourceCurrencyAndTargetCurrency() {
		Date effectiveStartDate = new Date();
		
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		when(exchangeRateService.findExchangeRatesByEffectiveDate(effectiveStartDate)).thenReturn(createExchangeRateList());
		List<ExchangeRate> actualExchangeRates = exchangeRateService.findExchangeRatesByEffectiveDate(effectiveStartDate);
		assertEquals(expectedExchangeRates, actualExchangeRates);

	}

}