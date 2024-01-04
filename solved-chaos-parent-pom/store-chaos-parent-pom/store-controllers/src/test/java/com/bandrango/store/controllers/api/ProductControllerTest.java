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

import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.services.StoreService;

@RunWith(MockitoJUnitRunner.class)
public class ProductControllerTest {

	@InjectMocks
    private ProductController productController;

    @Mock
    private StoreService storeService;
    
    @Test
    public void testGetProducts() {
        List<Product> mockProduct = createProductList();
        when(storeService.findAllProducts()).thenReturn(mockProduct);
        List<Product> result = productController.getAllProducts();
        
        verify(storeService, times(1)).findAllProducts();
        assertEquals(mockProduct, result);
    }
    
    @Test
    public void testUpserProductSuccess() {
    	Product product = new Product();
        when(storeService.upsertProduct(product)).thenReturn(1);
        ResponseEntity<String> response = productController.upsertProduct(product);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product upserted successfully.", response.getBody());

        verify(storeService, times(1)).upsertProduct(product);
    }

    @Test
    public void testDeleteProductSuccess() {
    	String product = "A";
        when(storeService.deleteProduct(product)).thenReturn(1);
        ResponseEntity<String> response = productController.deleteProduct(product);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product deleted successfully.", response.getBody());

        verify(storeService, times(1)).deleteProduct(product);
    }

    @Test
    public void testFindProductByName() {
    	String product = "A";
    	Product expectedProduct = createProduct();
        when(storeService.findProductByName(eq(product)))
                .thenReturn(expectedProduct);
        ResponseEntity<?> response =  productController.findProductByName(product);
    	
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedProduct, response.getBody());
    }
    
    private Product createProduct() {
		return new Product("A", new BigDecimal("0.52"), "80%", "none");
	}
    
    private List<Product> createProductList() {
		return Arrays.asList(new Product("A", new BigDecimal("0.52"), "80%", "none"),
				new Product("B", new BigDecimal("0.38"), "120%", "30% off"),
				new Product("C", new BigDecimal("0.41"), "0.9 EUR/unit", "none"),
				new Product("D", new BigDecimal("0.60"), "1 EUR/unit", "20% off")
		);
	}
}
