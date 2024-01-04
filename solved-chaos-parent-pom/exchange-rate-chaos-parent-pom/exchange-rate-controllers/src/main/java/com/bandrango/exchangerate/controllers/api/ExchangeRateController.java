package com.bandrango.exchangerate.controllers.api;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bandrango.exchangerate.crud.beans.ExchangeRate;
import com.bandrango.exchangerate.services.ExchangeRateService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/exchangerate")
public class ExchangeRateController {

	@Autowired
	private ExchangeRateService exchangeRateService;

	@GetMapping("/all")
	public List<ExchangeRate> getAllExchangeRates() {
		return exchangeRateService.getAllExchangeRates();
	}

	@GetMapping("/rate")
	public ResponseEntity<Object> getExchangeRate(@RequestParam("sourceCurrency") String sourceCurrency,
			@RequestParam("targetCurrency") String targetCurrency,
			@RequestParam("effectiveStartDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime effectiveStartDate) {
		try {
			
			if (sourceCurrency.equalsIgnoreCase(targetCurrency)) {
	            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                    .body("Source and target currencies cannot be the same.");
	        }
			
			Date date = Date.from(effectiveStartDate.atZone(ZoneId.systemDefault()).toInstant());
			Double exchangeRate = exchangeRateService.getExchangeRate(sourceCurrency, targetCurrency,
					date);

			if (exchangeRate != null) {
				return ResponseEntity.ok(exchangeRate);
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exchange rate not found.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	@PostMapping("/upsert")
	public ResponseEntity<String> upsertExchangeRate(@RequestBody ExchangeRate exchangeRate) {
		try {
			if (exchangeRateService.upsertExchangeRate(exchangeRate) > 0) {
				return ResponseEntity.ok("Exchange rate upserted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Unable to upsert exchange rate.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteExchangeRate(@PathVariable Long id) {
		try {
			if (exchangeRateService.deleteExchangeRate(id) > 0) {
				return ResponseEntity.ok("Exchange rate deleted successfully.");
			} else {
				return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Exchange rate not found.");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An error occurred while processing the request.");
		}
	}

}