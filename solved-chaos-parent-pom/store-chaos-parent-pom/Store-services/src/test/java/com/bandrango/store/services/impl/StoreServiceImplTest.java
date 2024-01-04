package com.bandrango.store.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
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
import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.crud.service.CustomerServiceRepository;
import com.bandrango.store.crud.service.ProductServiceRepository;
import com.bandrango.store.services.beans.OrderRequest;
import com.bandrango.store.services.beans.OrderResponse;

@ExtendWith(MockitoExtension.class)
public class StoreServiceImplTest {

	@InjectMocks
	private StoreServiceImpl storeService;
	
	@Mock
	ProductServiceRepository productRepository;

	@Mock
	CustomerServiceRepository customerRepository;
	
	@Before(value = "")
	public void setUp() throws Exception {
		storeService = mock(StoreServiceImpl.class);
		productRepository = mock(ProductServiceRepository.class);
		customerRepository = mock(CustomerServiceRepository.class);
	}
	
	/** Product */
	
	@Test
	public void testUpsertProduct() {
		Product product = new Product();
        when(productRepository.upsertProduct(product)).thenReturn(1);
        int result = storeService.upsertProduct(product);
        verify(productRepository, times(1)).upsertProduct(product);
        assertEquals(1, result);
	}

	@Test
	public void testDeleteProduct() {
        when(productRepository.deleteProduct(anyString())).thenReturn(1);
        int result = storeService.deleteProduct(anyString());
        verify(productRepository, times(1)).deleteProduct(anyString());
        assertEquals(1, result);
    }
	
	@Test
    public void testFindAllProducts() {
        List<Product> products = createProductList();
        when(productRepository.findAll()).thenReturn(products);
        List<Product> result = storeService.findAllProducts();
        verify(productRepository, times(1)).findAll();
        assertEquals(products, result);
    }
	
	@Test
    public void testFindProductByName() {
        Product product = createProductList().get(0);
        when(productRepository.findByName(anyString())).thenReturn(product);
        Product result = storeService.findProductByName(anyString());
        verify(productRepository, times(1)).findByName(anyString());
        assertEquals(product, result);
    }
	
	private List<Product> createProductList() {
		return Arrays.asList(new Product("A", new BigDecimal("0.52"), "80%", "none"),
				new Product("B", new BigDecimal("0.38"), "120%", "30% off"),
				new Product("C", new BigDecimal("0.41"), "0.9 EUR/unit", "none"),
				new Product("D", new BigDecimal("0.60"), "1 EUR/unit", "20% off")
		);
	}
	
	/** Customer */
	@Test
	public void testUpsertCustomer() {
		Customer customer = new Customer();
        when(customerRepository.upsertCustomer(customer)).thenReturn(1);
        int result = storeService.upsertCustomer(customer);
        verify(customerRepository, times(1)).upsertCustomer(customer);
        assertEquals(1, result);
	}

	@Test
	public void testDeleteCustomer() {
        when(customerRepository.deleteCustomer(anyLong())).thenReturn(1);
        int result = storeService.deleteCustomer(anyLong());
        verify(customerRepository, times(1)).deleteCustomer(anyLong());
        assertEquals(1, result);
    }
	
	@Test
    public void testFindAllCustomers() {
        List<Customer> customer = createCustomerList();
        when(customerRepository.findAll()).thenReturn(customer);
        List<Customer> result = storeService.findAllCustomers();
        verify(customerRepository, times(1)).findAll();
        assertEquals(customer, result);
    }
	
	@Test
    public void testFindCustomerById() {
		Customer customer = createCustomerList().get(0);
        when(customerRepository.findById(anyLong())).thenReturn(customer);
        Customer result = storeService.findCustomerById(anyLong());
        verify(customerRepository, times(1)).findById(anyLong());
        assertEquals(customer, result);
    }
	
	@Test
	public void testOrder() {
		OrderRequest orderRequest = createOrderRequest();
		Customer customer = createCustomer();
		List<Product> products = createProductList();
		when(productRepository.findAll()).thenReturn(products);
		when(customerRepository.findById(anyLong())).thenReturn(customer);
		OrderResponse orderResponse = storeService.order(orderRequest);
		verify(productRepository, times(1)).findAll();
		verify(customerRepository, times(1)).findById(anyLong());
		assertNotNull(orderResponse);
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
	
	private OrderRequest createOrderRequest() {
		return OrderRequest.builder().customerId(1).orderQuantityProductA(2).orderQuantityProductB(3)
				.orderQuantityProductC(4).orderQuantityProductD(5).build();
	}

}