package com.bandrango.store.crud.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.crud.dao.impl.JdbcProductDaoImpl;

@ExtendWith(MockitoExtension.class)
public class ProductServiceRepositoryImplTest {
	
	@InjectMocks
	private JdbcProductDaoImpl productDao;

	@Mock
	private ProductServiceRepositoryImpl productService;
	
	@Before(value = "")
	public void setUp() throws Exception {
		productDao = mock(JdbcProductDaoImpl.class);
		productService = mock(ProductServiceRepositoryImpl.class);
	}

	@Test
	public void testUpsertProduct() {
		Product product = new Product();
        when(productService.upsertProduct(product)).thenReturn(1);
        int result = productService.upsertProduct(product);
        verify(productService, times(1)).upsertProduct(product);
        assertEquals(1, result);
        
	}

	@Test
	public void testDeleteProduct() {
        when(productService.deleteProduct(anyString())).thenReturn(1);
        int result = productService.deleteProduct(anyString());
        verify(productService, times(1)).deleteProduct(anyString());
        assertEquals(1, result);
    }
	
	@Test
    public void testFindAllProducts() {
        List<Product> products = createProductList();
        when(productService.findAll()).thenReturn(products);
        List<Product> result = productService.findAll();
        verify(productService, times(1)).findAll();
        assertEquals(products, result);
    }
	
	@Test
    public void testFindProductByName() {
        Product product = createProductList().get(0);
        when(productService.findByName(anyString())).thenReturn(product);
        Product result = productService.findByName(anyString());
        verify(productService, times(1)).findByName(anyString());
        assertEquals(product, result);
    }
	
	private List<Product> createProductList() {
		return Arrays.asList(new Product("A", new BigDecimal("0.52"), "80%", "none"),
				new Product("B", new BigDecimal("0.38"), "120%", "30% off"),
				new Product("C", new BigDecimal("0.41"), "0.9 EUR/unit", "none"),
				new Product("D", new BigDecimal("0.60"), "1 EUR/unit", "20% off")
		);
	}
}
