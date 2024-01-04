package com.bandrango.store.crud.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.crud.dao.CustomerDao;
import com.bandrango.store.crud.dao.CustomerFieldDefinition;

@Component("customerDao")
public class JdbcCustomerDaoImpl implements CustomerDao {

	private final NamedParameterJdbcTemplate jdbcTemplate;
	private final String upsertQry;
	private final String deleteQry;
	private final String findAllQry;
	private final String findById;

	public JdbcCustomerDaoImpl(NamedParameterJdbcTemplate jdbcTemplate,
			@Value("${store.customer.upsert}") String upsertQry, @Value("${store.customer.delete}") String deleteQry,
			@Value("${store.customer.findAll}") String findAllQry,
			@Value("${store.customer.findById}") String findById) {
		this.jdbcTemplate = jdbcTemplate;
		this.upsertQry = upsertQry;
		this.deleteQry = deleteQry;
		this.findAllQry = findAllQry;
		this.findById = findById;
	}

	private MapSqlParameterSource createParameterSource(Customer customer) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(CustomerFieldDefinition.ID, customer.getId());
		parameterSource.addValue(CustomerFieldDefinition.BASIC_DISCOUNT, customer.getBasicCustomerDiscount());
		parameterSource.addValue(CustomerFieldDefinition.ABOUT_10000, customer.getDiscountAbove10000());
		parameterSource.addValue(CustomerFieldDefinition.ABOUT_30000, customer.getDiscountAbove30000());
		return parameterSource;
	}

	@Override
	public int upsertCustomer(Customer Customer) {
		return jdbcTemplate.update(upsertQry, createParameterSource(Customer));
	}

	@Override
	public int deleteCustomer(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(CustomerFieldDefinition.ID, id);
		return jdbcTemplate.update(deleteQry, parameterSource);
	}

	@Override
	public List<Customer> findAll() {
		return queryCustomers(findAllQry, new MapSqlParameterSource());
	}

	@Override
	public Customer findById(Long id) {
		MapSqlParameterSource parameterSource = new MapSqlParameterSource();
		parameterSource.addValue(CustomerFieldDefinition.ID, id);
		return queryCustomers(findById, parameterSource).get(0);
	}

	private List<Customer> queryCustomers(String query, MapSqlParameterSource parameterSource) {
		return jdbcTemplate.query(query, parameterSource,
				(rs, rowNum) -> new Customer(rs.getLong(CustomerFieldDefinition.ID),
						rs.getBigDecimal(CustomerFieldDefinition.BASIC_DISCOUNT), rs.getBigDecimal(CustomerFieldDefinition.ABOUT_10000),
						rs.getBigDecimal(CustomerFieldDefinition.ABOUT_30000)));
	}
}