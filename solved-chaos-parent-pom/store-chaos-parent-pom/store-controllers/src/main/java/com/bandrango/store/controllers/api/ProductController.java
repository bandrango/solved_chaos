package com.bandrango.store.controllers.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.services.StoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store/product")
public class ProductController {

	@Autowired
	private StoreService storeRateService;

	@GetMapping("/all")
	public List<Product> getAllProducts() {
		return storeRateService.findAllProducts();
	}

	@PostMapping("/upsert")
	public ResponseEntity<String> upsertProduct(@RequestBody Product product) {
		try {
			if (storeRateService.upsertProduct(product) > 0) {
				return ResponseEntity.ok("Product upserted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to upsert product.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	@DeleteMapping("/{name}")
	public ResponseEntity<String> deleteProduct(@PathVariable String name) {
		try {
			if (storeRateService.deleteProduct(name) > 0) {
				return ResponseEntity.ok("Product deleted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product not found.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}
	
	@GetMapping("/{name}")
    public ResponseEntity<?> findProductByName(@PathVariable String name) {
        try {
        	
            Product product = storeRateService.findProductByName(name);

            if (product != null) {
                return ResponseEntity.ok(product);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            log.error("Error processing the request.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing the request.");
        }
    }

}