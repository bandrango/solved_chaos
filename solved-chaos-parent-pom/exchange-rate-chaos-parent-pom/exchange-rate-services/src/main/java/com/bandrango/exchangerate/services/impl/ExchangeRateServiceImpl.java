package com.bandrango.exchangerate.services.impl;

import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.crud.service.ExchangeRateServiceRepository;
import com.bandrango.exchangerate.services.ExchangeRateService;

@Component
public class ExchangeRateServiceImpl implements ExchangeRateService {

	@Autowired
	ExchangeRateServiceRepository exchangeRateRepository;

	@Override
	public List<ExchangeRate> getAllExchangeRates() {
		return exchangeRateRepository.findAll();
	}

	@Override
	public Double getExchangeRate(String sourceCurrency, String targetCurrency, Date effectiveStartDate) {
	    Map<String, Double> exchangeRatesMap = buildExchangeRatesMap(sourceCurrency, targetCurrency, effectiveStartDate);

	    String directRateKey = sourceCurrency + ":" + targetCurrency;
	    if (exchangeRatesMap.containsKey(directRateKey)) {
	        return 1.0 * exchangeRatesMap.get(directRateKey);
	    }

//	    String inverseRateKey = targetCurrency + ":" + sourceCurrency;
//	    if (exchangeRatesMap.containsKey(inverseRateKey)) {
//	        return 1.0 / exchangeRatesMap.get(inverseRateKey);
//	    }

	    String intermediateCurrency = findIntermediateCurrency(exchangeRatesMap, sourceCurrency, targetCurrency);
	    if (intermediateCurrency != null) {
	        double sourceToIntermediateRate = exchangeRatesMap.get(sourceCurrency + ":" + intermediateCurrency);
	        double intermediateToTargetRate = exchangeRatesMap.get(intermediateCurrency + ":" + targetCurrency);
	        return sourceToIntermediateRate * intermediateToTargetRate;
	    }

	    throw new IllegalArgumentException("No exchange rate found for the conversion.");
	}

	public Map<String, Double> buildExchangeRatesMap(String sourceCurrency, String targetCurrency, Date effectiveStartDate) {
	    Map<String, Double> exchangeRatesMap = new HashMap<>();

	    List<ExchangeRate> exchangeRates = exchangeRateRepository.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(
	            sourceCurrency, targetCurrency, effectiveStartDate);
	    if (exchangeRates.isEmpty()) {
	        ExchangeRate closestExchangeRate = findClosestExchangeRate(
	                exchangeRateRepository.findBySourceCurrencyAndTargetCurrency(sourceCurrency, targetCurrency),
	                effectiveStartDate);
	        if (closestExchangeRate != null) {
	        	exchangeRates.add(closestExchangeRate);
	        }
	    }

	    if (exchangeRates.isEmpty()) {
	        exchangeRates.addAll(exchangeRateRepository.findAll());
	    }

	    exchangeRates.stream()
	            .filter(rate -> !exchangeRatesMap.containsKey(rate.getSourceCurrency() + ":" +  rate.getTargetCurrency()))
	            .forEach(rate -> exchangeRatesMap.put(rate.getSourceCurrency() + ":" +  rate.getTargetCurrency(), rate.getExchangeRate().doubleValue()));

	    return exchangeRatesMap;
	}

	private ExchangeRate findClosestExchangeRate(List<ExchangeRate> exchangeRates, Date effectiveStartDate) {
	    return exchangeRates.stream()
	            .min(Comparator.comparingLong(rate -> Math.abs(effectiveStartDate.getTime() - rate.getEffectiveStartDate().getTime())))
	            .orElse(null);
	}

	private String findIntermediateCurrency(Map<String, Double> exchangeRatesMap, String sourceCurrency,
	        String targetCurrency) {
	    return exchangeRatesMap.keySet().stream()
	            .map(currency -> currency.split(":"))
	            .filter(currencies -> currencies.length == 2)
	            .filter(currencies -> {
	                String intermediateCurrency = currencies[0];
	                String possibleDirectRate = sourceCurrency + ":" + intermediateCurrency;
	                String possibleInverseRate = intermediateCurrency + ":" + targetCurrency;
	                return exchangeRatesMap.containsKey(possibleDirectRate) && exchangeRatesMap.containsKey(possibleInverseRate);
	            })
	            .map(currencies -> currencies[0])
	            .findFirst()
	            .orElse(null);
	}

	@Override
	public int upsertExchangeRate(ExchangeRate exchangeRate) {
		return exchangeRateRepository.upsertExchangeRate(exchangeRate);
	}

	@Override
	public int deleteExchangeRate(Long id) {
		return exchangeRateRepository.deleteExchangeRate(id);
	}
}