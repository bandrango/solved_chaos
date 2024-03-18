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

import com.bandrango.store.crud.beans.Product;

@ExtendWith(MockitoExtension.class)
public class JdbcProductDaoImplTest {

	@Mock
	private NamedParameterJdbcTemplate jdbcTemplate;

	@InjectMocks
	private JdbcProductDaoImpl productDao;

	@Before(value = "")
	public void setUp() throws Exception {
		productDao = mock(JdbcProductDaoImpl.class);
		jdbcTemplate = mock(NamedParameterJdbcTemplate.class);
		ReflectionTestUtils.setField(productDao, "jdbcTemplate", jdbcTemplate);
	}

	@Test
	void testUpsertProduct() {
		Product product = createProduct();
		ReflectionTestUtils.setField(productDao, "upsertQry", "upsertQry");
		mockJdbcTemplateUpdate();
		int result = productDao.upsertProduct(product);
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testDeleteProduct() {
		Product product = createProduct();
		ReflectionTestUtils.setField(productDao, "deleteQry", "deleteQry");
		mockJdbcTemplateUpdate();
		int result = productDao.deleteProduct(product.getName());
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testFindAll() {
		List<Product> expectedExchangeRates = createProductList();
		ReflectionTestUtils.setField(productDao, "findAllQry", "findAllQry");
		mockJdbcTemplateQuery(expectedExchangeRates);
		List<Product> actualExchangeRates = productDao.findAll();
		assertEquals(expectedExchangeRates, actualExchangeRates);
	}

	@Test
	void testFindByName() {
		String name = "A";
		List<Product> expectedProduct = createProductList();
		ReflectionTestUtils.setField(productDao, "findByName", "findByName");
		mockJdbcTemplateQuery(expectedProduct);
		Product actualExchangeRates = productDao.findByName(name);
		assertEquals(expectedProduct.get(0), actualExchangeRates);
	}

	private Product createProduct() {
		return new Product("A", new BigDecimal("0.52"), "80%", "none");
	}

	private List<Product> createProductList() {
		return Arrays.asList(new Product("A", new BigDecimal("0.52"), "80%", "none"),
				new Product("B", new BigDecimal("0.38"), "120%", "30% off"),
				new Product("C", new BigDecimal("0.41"), "0.9 EUR/unit", "none"),
				new Product("D", new BigDecimal("0.60"), "1 EUR/unit", "20% off"));
	}

	private void mockJdbcTemplateUpdate() {
		when(jdbcTemplate.update(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any())).thenReturn(1);
	}

	private void mockJdbcTemplateQuery(List<Product> expectedExchangeRates) {
		when(jdbcTemplate.query(Mockito.anyString(), ArgumentMatchers.<MapSqlParameterSource>any(),
				ArgumentMatchers.<RowMapper<Product>>any())).thenReturn(expectedExchangeRates);
	}

}