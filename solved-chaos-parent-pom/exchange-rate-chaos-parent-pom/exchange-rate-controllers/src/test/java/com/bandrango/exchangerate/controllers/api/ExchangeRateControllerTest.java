package com.bandrango.exchangerate.controllers.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.services.ExchangeRateService;

@RunWith(MockitoJUnitRunner.class)
public class ExchangeRateControllerTest {

    @InjectMocks
    private ExchangeRateController exchangeRateController;

    @Mock
    private ExchangeRateService exchangeRateService;
    
    @Test
    public void testGetAllExchangeRates() {
        List<ExchangeRate> mockExchangeRates = createExchangeRateList();
        when(exchangeRateService.getAllExchangeRates()).thenReturn(mockExchangeRates);
        List<ExchangeRate> result = exchangeRateController.getAllExchangeRates();
        
        verify(exchangeRateService, times(1)).getAllExchangeRates();
        assertEquals(mockExchangeRates, result);
    }
    
    @Test
    public void testUpsertExchangeRateSuccess() {
        ExchangeRate exchangeRate = new ExchangeRate();
        when(exchangeRateService.upsertExchangeRate(exchangeRate)).thenReturn(1);
        ResponseEntity<String> response = exchangeRateController.upsertExchangeRate(exchangeRate);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Exchange rate upserted successfully.", response.getBody());

        verify(exchangeRateService, times(1)).upsertExchangeRate(exchangeRate);
    }
    
    @Test
    public void testUpsertExchangeRateBadRequest() {
        ExchangeRate exchangeRate = new ExchangeRate();
        when(exchangeRateService.upsertExchangeRate(exchangeRate)).thenReturn(0);
        ResponseEntity<String> response = exchangeRateController.upsertExchangeRate(exchangeRate);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Unable to upsert exchange rate.", response.getBody());

        verify(exchangeRateService, times(1)).upsertExchangeRate(exchangeRate);
    }

    @Test
    public void testUpsertExchangeRateInternalServerError() {
        ExchangeRate exchangeRate = new ExchangeRate();
        when(exchangeRateService.upsertExchangeRate(exchangeRate)).thenThrow(new RuntimeException("Simulated error"));
        ResponseEntity<String> response = exchangeRateController.upsertExchangeRate(exchangeRate);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while processing the request.", response.getBody());

        verify(exchangeRateService, times(1)).upsertExchangeRate(exchangeRate);
    }
    
    @Test
    public void testDeleteExchangeRateSuccess() {
        Long idToDelete = 1L;
        when(exchangeRateService.deleteExchangeRate(idToDelete)).thenReturn(1);
        ResponseEntity<String> response = exchangeRateController.deleteExchangeRate(idToDelete);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Exchange rate deleted successfully.", response.getBody());

        verify(exchangeRateService, times(1)).deleteExchangeRate(idToDelete);
    }
    
    @Test
    public void testDeleteExchangeRateNotFound() {
        Long idToDelete = 2L;
        when(exchangeRateService.deleteExchangeRate(idToDelete)).thenReturn(0);
        ResponseEntity<String> response = exchangeRateController.deleteExchangeRate(idToDelete);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Exchange rate not found.", response.getBody());

        verify(exchangeRateService, times(1)).deleteExchangeRate(idToDelete);
    }

    @Test
    public void testDeleteExchangeRateInternalServerError() {
        Long idToDelete = 3L;
        when(exchangeRateService.deleteExchangeRate(idToDelete)).thenThrow(new RuntimeException("Simulated error"));
        ResponseEntity<String> response = exchangeRateController.deleteExchangeRate(idToDelete);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("An error occurred while processing the request.", response.getBody());

        verify(exchangeRateService, times(1)).deleteExchangeRate(idToDelete);
    }
    
    @Test
    public void testGetExchangeRate() {
    	String sourceCurrency = "USD";
    	String targetCurrency = "EUR";
    	LocalDateTime effectiveStartDate = LocalDateTime.now();
        Double expectedExchangeRate = 1.5;
        
        when(exchangeRateService.getExchangeRate(eq(sourceCurrency), eq(targetCurrency), any(Date.class)))
                .thenReturn(expectedExchangeRate);
        ResponseEntity<Object> response =  exchangeRateController.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);
    	
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedExchangeRate, (Double) response.getBody());
    }
    
    @Test
    public void testGetExchangeRate_BadRequest() {
    	String sourceCurrency = "USD";
    	String targetCurrency = "USD";
    	LocalDateTime effectiveStartDate = LocalDateTime.now();
        ResponseEntity<Object> response =  exchangeRateController.getExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);
    	
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Source and target currencies cannot be the same.", response.getBody());
    }
    
    private List<ExchangeRate> createExchangeRateList() {
		return Arrays.asList(new ExchangeRate(1L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(2L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(1L, "EUR", "USD", new BigDecimal("1.4"), new Date()),
				new ExchangeRate(2L, "USD", "GBP", new BigDecimal("0.5"), new Date())
		);
	}
    
}