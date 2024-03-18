package com.bandrango.store.services.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bandrango.store.crud.beans.Customer;
import com.bandrango.store.crud.beans.Product;
import com.bandrango.store.crud.service.CustomerServiceRepository;
import com.bandrango.store.crud.service.ProductServiceRepository;
import com.bandrango.store.services.StoreService;
import com.bandrango.store.services.beans.OrderLine;
import com.bandrango.store.services.beans.OrderRequest;
import com.bandrango.store.services.beans.OrderResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class StoreServiceImpl implements StoreService {

	@Autowired
	ProductServiceRepository productRepository;

	@Autowired
	CustomerServiceRepository customerRepository;

	@Override
	public int upsertProduct(Product product) {
		return productRepository.upsertProduct(product);
	}

	@Override
	public int deleteProduct(String name) {
		return productRepository.deleteProduct(name);
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public Product findProductByName(String name) {
		return productRepository.findByName(name);
	}

	@Override
	public int upsertCustomer(Customer Customer) {
		return customerRepository.upsertCustomer(Customer);
	}

	@Override
	public int deleteCustomer(Long id) {
		return customerRepository.deleteCustomer(id);
	}

	@Override
	public List<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}

	@Override
	public Customer findCustomerById(Long id) {
		return customerRepository.findById(id);
	}

	@Override
	public OrderResponse order(OrderRequest orderRequest) {
		List<Product> products = productRepository.findAll();
		products.sort(Comparator.comparing(Product::getName));
		Customer customer = customerRepository.findById(Long.valueOf(orderRequest.getCustomerId()));

		BigDecimal totalBeforeDiscounts = BigDecimal.ZERO;
		List<OrderLine> orderLines = new ArrayList<>();

		for (Product product : products) {
		    int quantity = getOrderQuantity(product.getName(), orderRequest);
		    BigDecimal lineTotal = quantity > 0 ? calculateTotalBeforeDiscounts(product, quantity): BigDecimal.ZERO;

		    OrderLine orderLine = OrderLine.builder()
		            .quantityOrdered(quantity)
		            .baseUnitPrice(product.getUnitCost())
		            .lineTotal(lineTotal)
		            .build();

		    orderLines.add(orderLine);
		    totalBeforeDiscounts = totalBeforeDiscounts.add(lineTotal);
		}

		BigDecimal basicDiscount = totalBeforeDiscounts
				.multiply(customer.getBasicCustomerDiscount().divide(BigDecimal.valueOf(100)));
		BigDecimal discountAbove10000 = (totalBeforeDiscounts.compareTo(new BigDecimal("10000")) > 0)
				? totalBeforeDiscounts.multiply(customer.getDiscountAbove10000().divide(BigDecimal.valueOf(100)))
				: BigDecimal.ZERO;
		BigDecimal discountAbove30000 = (totalBeforeDiscounts.compareTo(new BigDecimal("30000")) > 0)
				? totalBeforeDiscounts.multiply(customer.getDiscountAbove30000().divide(BigDecimal.valueOf(100)))
				: BigDecimal.ZERO;

		BigDecimal totalAfterDiscounts = totalBeforeDiscounts.subtract(basicDiscount).subtract(discountAbove10000)
				.subtract(discountAbove30000);

		return OrderResponse.builder().orderLines(orderLines).totalBeforeDiscounts(totalBeforeDiscounts)
				.totalAfterDiscounts(totalAfterDiscounts).build();

	}

	private static int getOrderQuantity(String productName, OrderRequest orderRequest) {
		switch (productName) {
		case "A":
			return orderRequest.getOrderQuantityProductA();
		case "B":
			return orderRequest.getOrderQuantityProductB();
		case "C":
			return orderRequest.getOrderQuantityProductC();
		case "D":
			return orderRequest.getOrderQuantityProductD();
		default:
			throw new IllegalArgumentException("Invalid product name: " + productName);
		}
	}

	private static BigDecimal calculateTotalBeforeDiscounts(Product product, int quantity) {
		BigDecimal markup = getMarkup(product);
		BigDecimal unitPrice = product.getUnitCost().add(product.getUnitCost().multiply(markup));
		BigDecimal lineTotal  =  unitPrice.multiply(BigDecimal.valueOf(quantity));

		return lineTotal.subtract(calculatePromotion(product.getPromotion(), unitPrice));
	}

	private static BigDecimal getMarkup(Product product) {
		if (product.getMarkup().endsWith("%")) {
			return calculatePercentage(product.getMarkup());
		} else {
			String[] parts = product.getMarkup().split(" ");
			if (parts.length == 2 && parts[1].equalsIgnoreCase("EUR/unit")) {
				return calculateMarkup(product.getUnitCost(), new BigDecimal(parts[0]));
			} else {
				throw new IllegalArgumentException("Unrecognized format for the markup: " + product.getMarkup());
			}
		}
	}

	private static BigDecimal calculatePercentage(String pattern) {
		BigDecimal percentage = new BigDecimal(pattern.substring(0, pattern.length() - 1));
		return percentage.divide(BigDecimal.valueOf(100));
	}

	private static BigDecimal calculateMarkup(BigDecimal unitCost, BigDecimal unitSalePrice) {
		if (unitCost.compareTo(BigDecimal.ZERO) <= 0 || unitSalePrice.compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Both unitCost and unitSalePrice must be positive numbers.");
		}

		BigDecimal numerator = unitSalePrice.subtract(unitCost);
		BigDecimal denominator = unitCost;

		return numerator.divide(denominator, 4, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100));
	}

	private static BigDecimal calculatePromotion(String promotionType, BigDecimal unitCost) {
		try {
			if ("none".equalsIgnoreCase(promotionType)) {
				return BigDecimal.ZERO;
			} else {
				String[] parts = promotionType.split(" ");
				BigDecimal discountPercentage = calculatePercentage(parts[0]);
				return unitCost.multiply(discountPercentage);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return BigDecimal.ZERO;
		}
	}
}