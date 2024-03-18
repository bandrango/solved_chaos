package com.bandrango.exchangerate.crud.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.crud.dao.ExchangeRateDao;
import com.bandrango.exchangerate.crud.service.ExchangeRateServiceRepository;

@Component
public class ExchangeRateServiceRepositoryImpl implements ExchangeRateServiceRepository {

	@Autowired
	private ExchangeRateDao exchangeRateDao;

	@Override
	public int upsertExchangeRate(ExchangeRate exchangeRate) {
		return exchangeRateDao.upsertExchangeRate(exchangeRate);
	}

	@Override
	public int deleteExchangeRate(Long id) {
		return exchangeRateDao.deleteExchangeRate(id);
	}

	@Override
	public ExchangeRate findExchangeRate(String sourceCurrency, String targetCurrency, Date effectiveStartDate) {
		return exchangeRateDao.findExchangeRate(sourceCurrency, targetCurrency, effectiveStartDate);
	}

	@Override
	public List<ExchangeRate> findExchangeRatesByEffectiveDate(Date effectiveStartDate) {
		return exchangeRateDao.findExchangeRatesByEffectiveDate(effectiveStartDate);
	}
}