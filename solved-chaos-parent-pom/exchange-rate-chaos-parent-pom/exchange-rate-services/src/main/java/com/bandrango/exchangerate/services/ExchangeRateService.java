package com.bandrango.exchangerate.services;

import java.util.Date;
import java.util.List;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;

public interface ExchangeRateService {

	/**
	 * Retrieves a list of all exchange rates.
	 *
	 * @return A list containing all exchange rates.
	 */
	public List<ExchangeRate> getAllExchangeRates();

	/**
	 * Retrieves a list of exchange rates based on the source currency, target
	 * currency, and effective start date.
	 *
	 * @param sourceCurrency     The source currency code.
	 * @param targetCurrency     The target currency code.
	 * @param effectiveStartDate The effective start date for the exchange rates.
	 * @return A list of ExchangeRate objects that match the specified criteria.
	 */
	public Double getExchangeRate(String sourceCurrency, String targetCurrency, Date effectiveStartDate);

	/**
	 * Upserts (inserts or updates) the given exchange rate into the system.
	 *
	 * @param exchangeRate The ExchangeRate object to be upserted.
	 * @return The number of affected rows in the database after the upsert
	 *         operation.
	 * @Transactional(readOnly = false)
	 */
	public int upsertExchangeRate(ExchangeRate exchangeRate);

	/**
	 * Deletes the exchange rate with the specified ID from the system.
	 *
	 * @param id The ID of the exchange rate to be deleted.
	 * @return The number of affected rows in the database after the deletion
	 *         operation.
	 * @Transactional(readOnly = false)
	 */
	public int deleteExchangeRate(Long id);
	
}