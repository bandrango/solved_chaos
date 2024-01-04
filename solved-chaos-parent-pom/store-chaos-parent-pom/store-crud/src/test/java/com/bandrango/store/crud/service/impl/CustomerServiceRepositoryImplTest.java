package com.bandrango.store.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.crud.dao.impl.JdbcCustomerDaoImpl;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceRepositoryImplTest {
	
	@InjectMocks
	private JdbcCustomerDaoImpl customerDao;

	@Mock
	private CustomerServiceRepositoryImpl customerService;
	
	@Before(value = "")
	public void setUp() throws Exception {
		customerDao = mock(JdbcCustomerDaoImpl.class);
		customerService = mock(CustomerServiceRepositoryImpl.class);
	}

	@Test
	public void testUpsertCustomer() {
		Customer customer = new Customer();
        when(customerService.upsertCustomer(customer)).thenReturn(1);
        int result = customerService.upsertCustomer(customer);
        verify(customerService, times(1)).upsertCustomer(customer);
        assertEquals(1, result);
	}

	@Test
	public void testDeleteCustomer() {
        when(customerService.deleteCustomer(anyLong())).thenReturn(1);
        int result = customerService.deleteCustomer(anyLong());
        verify(customerService, times(1)).deleteCustomer(anyLong());
        assertEquals(1, result);
    }
	
	@Test
    public void testFindAllCustomers() {
        List<Customer> customer = createCustomerList();
        when(customerService.findAll()).thenReturn(customer);
        List<Customer> result = customerService.findAll();
        verify(customerService, times(1)).findAll();
        assertEquals(customer, result);
    }
	
	@Test
    public void testFindCustomerById() {
		Customer customer = createCustomerList().get(0);
        when(customerService.findById(anyLong())).thenReturn(customer);
        Customer result = customerService.findById(anyLong());
        verify(customerService, times(1)).findById(anyLong());
        assertEquals(customer, result);
    }
	
	private List<Customer> createCustomerList() {
		return Arrays.asList(new Customer(1L, new BigDecimal("5"), new BigDecimal("0"), new BigDecimal("2")),
				new Customer(2L, new BigDecimal("4"), new BigDecimal("1"), new BigDecimal("2")),
				new Customer(3L, new BigDecimal("3"), new BigDecimal("1"), new BigDecimal("3")),
				new Customer(4L, new BigDecimal("2"), new BigDecimal("3"), new BigDecimal("5")),
				new Customer(5L, new BigDecimal("0"), new BigDecimal("5"), new BigDecimal("7"))
		);
	}

}