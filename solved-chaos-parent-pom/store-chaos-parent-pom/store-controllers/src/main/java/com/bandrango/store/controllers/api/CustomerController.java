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

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.services.StoreService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store/customer")
public class CustomerController {

	@Autowired
	private StoreService storeRateService;

	@GetMapping("/all")
	public List<Customer> getAllCustomers() {
		return storeRateService.findAllCustomers();
	}

	@PostMapping("/upsert")
	public ResponseEntity<String> upsertCustomer(@RequestBody Customer customer) {
		try {
			if (storeRateService.upsertCustomer(customer) > 0) {
				return ResponseEntity.ok("Customer upserted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to upsert customer.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
		try {
			if (storeRateService.deleteCustomer(id) > 0) {
				return ResponseEntity.ok("Customer deleted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Customer not found.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}
	
	@GetMapping("/{id}")
    public ResponseEntity<?> findCustomerById(@PathVariable Long id) {
        try {
        	
            Customer customer = storeRateService.findCustomerById(id);

            if (customer != null) {
                return ResponseEntity.ok(customer);
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