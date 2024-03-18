package com.bandrango.store.controllers.api;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.services.StoreService;

@RunWith(MockitoJUnitRunner.class)
public class CustomerControllerTest {
	@InjectMocks
    private CustomerController customerController;

    @Mock
    private StoreService storeService;
    
    @Test
    public void testGetAllExchangeRates() {
        List<Customer> mockCustomer = createCustomerList();
        when(storeService.findAllCustomers()).thenReturn(mockCustomer);
        List<Customer> result = customerController.getAllCustomers();
        
        verify(storeService, times(1)).findAllCustomers();
        assertEquals(mockCustomer, result);
    }
    
    @Test
    public void testUpserCustomerSuccess() {
    	Customer customer = new Customer();
        when(storeService.upsertCustomer(customer)).thenReturn(1);
        ResponseEntity<String> response = customerController.upsertCustomer(customer);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer upserted successfully.", response.getBody());

        verify(storeService, times(1)).upsertCustomer(customer);
    }

    @Test
    public void testDeleteCustomerSuccess() {
        Long idToDelete = 1L;
        when(storeService.deleteCustomer(idToDelete)).thenReturn(1);
        ResponseEntity<String> response = customerController.deleteCustomer(idToDelete);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Customer deleted successfully.", response.getBody());

        verify(storeService, times(1)).deleteCustomer(idToDelete);
    }

    @Test
    public void testFindCustomerById() {
    	Long id = 1L;
    	Customer expectedCustomer = createCustome();
        when(storeService.findCustomerById(eq(id)))
                .thenReturn(expectedCustomer);
        ResponseEntity<?> response =  customerController.findCustomerById(id);
    	
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedCustomer, response.getBody());
    }
    
    private Customer createCustome() {
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
}
