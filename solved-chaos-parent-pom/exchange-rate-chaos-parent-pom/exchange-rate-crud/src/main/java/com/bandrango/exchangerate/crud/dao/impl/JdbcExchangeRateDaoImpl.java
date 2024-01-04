package com.bandrango.exchangerate.crud.dao.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.crud.dao.ExchangeRateDao;
import com.bandrango.exchangerate.crud.dao.ExchangeRateFieldDefinition;

@Component("exchangeRateDao")
public class JdbcExchangeRateDaoImpl implements ExchangeRateDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String upsertQry;
	private final String deleteQry;
	private final String findAllQry;
	private final String findBySourceCurrencyAndTargetCurrencyAndEffectiveDate;
	private final String findBySourceCurrencyAndTargetCurrency;

	public JdbcExchangeRateDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
			@Value("${exchangeRate.upsert}") String upsertQry, @Value("${exchangeRate.delete}") String deleteQry,
			@Value("${exchangeRate.findAll}") String findAllQry,
			@Value("${exchangeRate.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate}") String findBySourceCurrencyAndTargetCurrencyAndEffectiveDate,
			@Value("${exchangeRate.findBySourceCurrencyAndTargetCurrency}") String findBySourceCurrencyAndTargetCurrency) {
		this.jdbcTemplate = jdbcTemplate;
		this.upsertQry = upsertQry;
		this.deleteQry = deleteQry;
		this.findAllQry = findAllQry;
		this.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate = findBySourceCurrencyAndTargetCurrencyAndEffectiveDate;
		this.findBySourceCurrencyAndTargetCurrency = findBySourceCurrencyAndTargetCurrency;
	}

	@Override
	public int upsertExchangeRate(ExchangeRate exchangeRate) {
		return jdbcTemplate.update(upsertQry, createParameterSource(exchangeRate));
	}

	@Override
	public int deleteExchangeRate(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ExchangeRateFieldDefinition.ID, id);
		return jdbcTemplate.update(deleteQry, parameterSource);
	}

	private List<ExchangeRate> queryExchangeRates(String query, MapSqlParameterSource parameterSource) {
		return jdbcTemplate.query(query, parameterSource,
				(rs, rowNum) -> new ExchangeRate(rs.getLong(ExchangeRateFieldDefinition.ID),
						rs.getString(ExchangeRateFieldDefinition.SOURCE_CURRENCY),
						rs.getString(ExchangeRateFieldDefinition.TARGET_CURRENCY),
						rs.getBigDecimal(ExchangeRateFieldDefinition.EXCHANGE_RATE),
						rs.getDate(ExchangeRateFieldDefinition.EFFECTIVE_START_DATE)));
	}

	@Override
	public List<ExchangeRate> findAll() {
		return queryExchangeRates(findAllQry, new MapSqlParameterSource());
	}

	@Override
	public List<ExchangeRate> findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(String sourceCurrency,
			String targetCurrency, Date effectiveStartDate) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ExchangeRateFieldDefinition.SOURCE_CURRENCY, sourceCurrency);
		parameterSource.addValue(ExchangeRateFieldDefinition.TARGET_CURRENCY, targetCurrency);
		parameterSource.addValue(ExchangeRateFieldDefinition.EFFECTIVE_START_DATE, effectiveStartDate);
		return queryExchangeRates(findBySourceCurrencyAndTargetCurrencyAndEffectiveDate, parameterSource);
	}

	@Override
	public List<ExchangeRate> findBySourceCurrencyAndTargetCurrency(String sourceCurrency, String targetCurrency) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ExchangeRateFieldDefinition.SOURCE_CURRENCY, sourceCurrency);
		parameterSource.addValue(ExchangeRateFieldDefinition.TARGET_CURRENCY, targetCurrency);
		return queryExchangeRates(findBySourceCurrencyAndTargetCurrency, parameterSource);
	}

	private MapSqlParameterSource createParameterSource(ExchangeRate exchangeRate) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ExchangeRateFieldDefinition.ID, exchangeRate.getId());
		parameterSource.addValue(ExchangeRateFieldDefinition.SOURCE_CURRENCY, exchangeRate.getSourceCurrency());
		parameterSource.addValue(ExchangeRateFieldDefinition.TARGET_CURRENCY, exchangeRate.getTargetCurrency());
		parameterSource.addValue(ExchangeRateFieldDefinition.EXCHANGE_RATE, exchangeRate.getExchangeRate());
		parameterSource.addValue(ExchangeRateFieldDefinition.EFFECTIVE_START_DATE,
				exchangeRate.getEffectiveStartDate());
		return parameterSource;
	}
}