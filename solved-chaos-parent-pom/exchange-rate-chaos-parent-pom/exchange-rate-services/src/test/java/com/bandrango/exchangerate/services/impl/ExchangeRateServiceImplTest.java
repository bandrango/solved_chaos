package com.bandrango.exchangerate.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
import com.bandrango.exchangerate.crud.service.ExchangeRateServiceRepository;

@ExtendWith(MockitoExtension.class)
class ExchangeRateServiceImplTest {

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
        List<ExchangeRate> mockExchangeRates = createExchangeRateList();
        when(exchangeRateRepository.findAll()).thenReturn(mockExchangeRates);
        List<ExchangeRate> result = exchangeRateService.getAllExchangeRates();
        verify(exchangeRateRepository, times(1)).findAll();
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
        
		when(exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(sourceCurrency,
				targetCurrency, effectiveStartDate)).thenReturn(createExchangeRateList());
        Double result = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);

        assertEquals(1.2, result, 0.0001);
    }
	
	@Test
    public void testGetExchangeRate_ClosedRate() {
        String sourceCurrency = "USD";
        String targetCurrency = "EUR";
        Date effectiveStartDate = new Date();
        
		when(exchangeRateRepository.findBySourceCurrencyAndTargetCurrency(sourceCurrency,
				targetCurrency)).thenReturn(createExchangeRateList());
        Double result = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);

        assertEquals(1.2, result, 0.0001);
    }
	
	@Test
    public void testGetExchangeRate_WithIntermediateCurrency() {
        String sourceCurrency = "EUR";
        String targetCurrency = "GBP";
        Date effectiveStartDate = new Date();
        
		when(exchangeRateRepository.findAll()).thenReturn(createExchangeRateList());
        Double result = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);

        assertEquals(0.7, result, 0.0001);
    }

	private List<ExchangeRate> createExchangeRateList() {
		return Arrays.asList(new ExchangeRate(1L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(2L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(1L, "EUR", "USD", new BigDecimal("1.4"), new Date()),
				new ExchangeRate(2L, "USD", "GBP", new BigDecimal("0.5"), new Date())
		);
	}

}