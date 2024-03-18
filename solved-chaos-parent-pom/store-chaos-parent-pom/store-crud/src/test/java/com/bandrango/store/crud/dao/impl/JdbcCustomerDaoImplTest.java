package com.bandrango.store.crud.dao.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
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

import com.bandrango.store.crud.beans.Customer;

@ExtendWith(MockitoExtension.class)
public class JdbcCustomerDaoImplTest {
	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;

	@InjectMocks
	private JdbcCustomerDaoImpl customerDao;

	@Before(value = "")
	public void setUp() throws Exception {
		customerDao = mock(JdbcCustomerDaoImpl.class);
		jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
		ReflectionTestUtils.setField(customerDao, "jdbcTemplate", jdbcTemplate);
	}

	@Test
	void testUpsertProduct() {
		Customer customer = createCustomer();
		ReflectionTestUtils.setField(customerDao, "upsertQry", "upsertQry");
		mockJdbcTemplateUpdate();
		int result = customerDao.upsertCustomer(customer);
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testDeleteProduct() {
		Customer customer = createCustomer();
		ReflectionTestUtils.setField(customerDao, "deleteQry", "deleteQry");
		mockJdbcTemplateUpdate();
		int result = customerDao.deleteCustomer(customer.getId());
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testFindAll() {
		List<Customer> expectedCustomers = createCustomerList();
		ReflectionTestUtils.setField(customerDao, "findAllQry", "findAllQry");
		mockJdbcTemplateQuery(expectedCustomers);
		List<Customer> actualCustomer = customerDao.findAll();
		assertEquals(expectedCustomers, actualCustomer);
	}

	@Test
	void testFindById() {
		Long id = 1L;
		List<Customer> expectedCustomers = createCustomerList();
		ReflectionTestUtils.setField(customerDao, "findById", "findById");
		mockJdbcTemplateQuery(expectedCustomers);
		Customer actualCustomer = customerDao.findById(id);
		assertEquals(expectedCustomers.get(0), actualCustomer);
	}
	
	
	private Customer createCustomer() {
		return new Customer(1L, new BigDecimal("5"), new BigDecimal("0"), new BigDecimal("2"));
	}

	private List<Customer> createCustomerList() {
		return Arrays.asList(new Customer(1L, new BigDecimal("5"), new BigDecimal("0"), new BigDecimal("2")),
				new Customer(2L, new BigDecimal("4"), new BigDecimal("1"), new BigDecimal("2")),
				new Customer(3L, new BigDecimal("3"), new BigDecimal("1"), new BigDecimal("3")),
				new Customer(4L, new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5")),
				new Customer(5L, new BigDecimal("0"), new BigDecimal("5"), new BigDecimal("7"))
		);
	}
	
	private void mockJdbcTemplateUpdate() {
		when(jdbcTemplate.update(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any())).thenReturn(1);
	}

	private void mockJdbcTemplateQuery(List<Customer> expectedExchangeRates) {
		when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any(),
				ArgumentMatchers.<RowMapper<Customer>>any())).thenReturn(expectedExchangeRates);
	}

}