package com.bandrango.store.crud.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.crud.dao.ProductDao;
import com.bandrango.store.crud.dao.ProductFieldDefinition;

@Component("productDao")
public class JdbcProductDaoImpl implements ProductDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String upsertQry;
	private final String deleteQry;
	private final String findAllQry;
	private final String findByName;

	public JdbcProductDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
			@Value("${store.product.upsert}") String upsertQry, @Value("${store.product.delete}") String deleteQry,
			@Value("${store.product.findAll}") String findAllQry,
			@Value("${store.product.findByName}") String findByName) {
		this.jdbcTemplate = jdbcTemplate;
		this.upsertQry = upsertQry;
		this.deleteQry = deleteQry;
		this.findAllQry = findAllQry;
		this.findByName = findByName;
	}

	private MapSqlParameterSource createParameterSource(Product product) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ProductFieldDefinition.NAME, product.getName());
		parameterSource.addValue(ProductFieldDefinition.UNIT_COST, product.getUnitCost());
		parameterSource.addValue(ProductFieldDefinition.MARKUP, product.getMarkup());
		parameterSource.addValue(ProductFieldDefinition.PROMOTION, product.getPromotion());
		return parameterSource;
	}

	@Override
	public int upsertProduct(Product product) {
		return jdbcTemplate.update(upsertQry, createParameterSource(product));
	}

	@Override
	public int deleteProduct(String name) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ProductFieldDefinition.NAME, name);
		return jdbcTemplate.update(deleteQry, parameterSource);
	}

	@Override
	public List<Product> findAll() {
		return queryProducts(findAllQry, new MapSqlParameterSource());
	}

	@Override
	public Product findByName(String name) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(ProductFieldDefinition.NAME, name);
		return queryProducts(findByName, parameterSource).get(0);
	}

	private List<Product> queryProducts(String query, MapSqlParameterSource parameterSource) {
		return jdbcTemplate.query(query, parameterSource,
				(rs, rowNum) -> new Product(rs.getString(ProductFieldDefinition.NAME),
						rs.getBigDecimal(ProductFieldDefinition.UNIT_COST), rs.getString(ProductFieldDefinition.MARKUP),
						rs.getString(ProductFieldDefinition.PROMOTION)));
	}
}