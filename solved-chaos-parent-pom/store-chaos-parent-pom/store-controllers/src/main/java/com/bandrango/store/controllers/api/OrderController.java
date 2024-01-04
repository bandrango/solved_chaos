package com.bandrango.store.controllers.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bandrango.store.services.StoreService;
import com.bandrango.store.services.beans.OrderRequest;
import com.bandrango.store.services.beans.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/store")
public class OrderController {

	@Autowired
	private StoreService storeRateService;

	@PostMapping("/order")
	public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest) {
		try {
			OrderResponse orderResponse = storeRateService.order(orderRequest);
			return ResponseEntity.ok(orderResponse);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}
}