package com.bandrango.exchangerate.crud.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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
import com.bandrango.exchangerate.crud.dao.impl.util.ExchangeRateUtlis;

@ExtendWith(MockitoExtension.class)
class JdbcExchangeRateDaoImplTest extends ExchangeRateUtlis {

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
	void testFindExchangeRate() {
		// Arrange
		String sourceCurrency = "USD";
		String targetCurrency = "EUR";
		Date effectiveStartDate = new Date();
		ExchangeRate expectedExchangeRates = createExchangeRate();
		
		ReflectionTestUtils.setField(exchangeRateDao, "findExchangeRate",
				"findExchangeRate");

		// Act
		mockJdbcTemplateQuery(expectedExchangeRates);

		// Assert
		ExchangeRate actualExchangeRates = exchangeRateDao.findExchangeRate(
				sourceCurrency, targetCurrency, effectiveStartDate);
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	@Test
	void testFindExchangeRatesByEffectiveDate() {
		// Arrange
		Date effectiveStartDate = new Date();
		List<ExchangeRate> expectedExchangeRates = createExchangeRateList();
		
		ReflectionTestUtils.setField(exchangeRateDao, "findExchangeRatesByEffectiveDate",
				"findExchangeRatesByEffectiveDate");

		// Act
		mockJdbcTemplateQuery(expectedExchangeRates);

		// Assert
		List<ExchangeRate> actualExchangeRates = exchangeRateDao.findExchangeRatesByEffectiveDate(effectiveStartDate);
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	private void mockJdbcTemplateUpdate() {
		when(jdbcTemplate.update(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any())).thenReturn(1);
	}

	private void mockJdbcTemplateQuery(List<ExchangeRate> expectedExchangeRates) {
		when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any(),
				ArgumentMatchers.<RowMapper<ExchangeRate>>any())).thenReturn(expectedExchangeRates);
	}
	
	private void mockJdbcTemplateQuery(ExchangeRate expectedExchangeRates) {
		when(jdbcTemplate.queryForObject(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any(),
				ArgumentMatchers.<RowMapper<ExchangeRate>>any())).thenReturn(expectedExchangeRates);
	}
}