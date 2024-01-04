package com.bandrango.store.crud.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.crud.dao.CustomerDao;
import com.bandrango.store.crud.service.CustomerServiceRepository;

@Component
public class CustomerServiceRepositoryImpl implements CustomerServiceRepository {

	@Autowired
	private CustomerDao customerDao;

	@Override
	public int upsertCustomer(Customer customer) {
		return customerDao.upsertCustomer(customer);
	}

	@Override
	public int deleteCustomer(Long id) {
		return customerDao.deleteCustomer(id);
	}

	@Override
	public List<Customer> findAll() {
		return customerDao.findAll();
	}

	@Override
	public Customer findById(Long id) {
		return customerDao.findById(id);
	}

}