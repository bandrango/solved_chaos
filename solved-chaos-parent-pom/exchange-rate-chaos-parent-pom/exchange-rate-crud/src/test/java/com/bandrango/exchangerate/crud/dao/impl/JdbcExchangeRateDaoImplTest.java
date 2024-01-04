package com.bandrango.exchangerate.crud.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
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
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;

@ExtendWith(MockitoExtension.class)
class JdbcExchangeRateDaoImplTest {

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;

	@InjectMocks
	private JdbcExchangeRateDaoImpl exchangeRateDao;

	@Before(value = "")
	public void setUp() throws Exception {
		exchangeRateDao = mock(JdbcExchangeRateDaoImpl.class);
		jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
		ReflectionTestUtils.setField(exchangeRateDao, "jdbcTemplate", jdbcTemplate);
	}

	@Test
	void testUpsertExchangeRate() {
		// Arrange
		ExchangeRate exchangeRate = createExchangeRate();

		ReflectionTestUtils.setField(exchangeRateDao, "upsertQry", "upsertQry");

		// Act
		mockJdbcTemplateUpdate();

		// Assert
		int result = exchangeRateDao.upsertExchangeRate(exchangeRate);
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testDeleteExchangeRate() {
		// Arrange
		ExchangeRate exchangeRate = createExchangeRate();

		ReflectionTestUtils.setField(exchangeRateDao, "deleteQry", "deleteQry");

		// Act
		mockJdbcTemplateUpdate();

		// Assert
		int result = exchangeRateDao.deleteExchangeRate(exchangeRate.getId());
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testFindAll() {
		// Arrange
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();

		ReflectionTestUtils.setField(exchangeRateDao, "findAllQry", "findAllQry");

		// Act
		mockJdbcTemplateQuery(expectedExchangeRates);

		// Assert
		List<ExchangeRate> actualExchangeRates = exchangeRateDao.findAll();
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	@Test
	void testFindBySourceCurrencyAndTargetCurrencyAndEffectiveDate() {
		// Arrange
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		Date effectiveStartDate = new Date();
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		
		ReflectionTestUtils.setField(exchangeRateDao, "findBySourceCurrencyAndTargetCurrencyAndEffectiveDate",
				"findBySourceCurrencyAndTargetCurrencyAndEffectiveDate");

		// Act
		mockJdbcTemplateQuery(expectedExchangeRates);

		// Assert
		List<ExchangeRate> actualExchangeRates = exchangeRateDao.findBySourceCurrencyAndTargetCurrencyAndEffectiveDate(
				sourceCurrency, targetCurrency, effectiveStartDate);
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	@Test
	void testFindBySourceCurrencyAndTargetCurrency() {
		// Arrange
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		
		ReflectionTestUtils.setField(exchangeRateDao, "findBySourceCurrencyAndTargetCurrency",
				"findBySourceCurrencyAndTargetCurrency");

		// Act
		mockJdbcTemplateQuery(expectedExchangeRates);

		// Assert
		List<ExchangeRate> actualExchangeRates = exchangeRateDao.findBySourceCurrencyAndTargetCurrency(sourceCurrency,
				targetCurrency);
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	private ExchangeRate createExchangeRate() {
		return ExchangeRate.builder().id(1L).sourceCurrency("USD").targetCurrency("EUR")
				.exchangeRate(new BigDecimal(0.56)).effectiveStartDate(new Date()).build();
	}

	private List<ExchangeRate> createExchangeRateList() {
		return Arrays.asList(new ExchangeRate(1L, "USD", "EUR", new BigDecimal("1.2"), new Date()),
				new ExchangeRate(2L, "USD", "EUR", new BigDecimal("1.3"), new Date()));
	}

	private void mockJdbcTemplateUpdate() {
		when(jdbcTemplate.update(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any())).thenReturn(1);
	}

	private void mockJdbcTemplateQuery(List<ExchangeRate> expectedExchangeRates) {
		when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any(),
				ArgumentMatchers.<RowMapper<ExchangeRate>>any())).thenReturn(expectedExchangeRates);
	}
}