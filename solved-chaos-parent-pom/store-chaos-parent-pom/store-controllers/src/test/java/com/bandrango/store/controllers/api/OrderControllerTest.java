package com.bandrango.store.controllers.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.bandrango.store.services.StoreService;
import com.bandrango.store.services.beans.OrderLine;
import com.bandrango.store.services.beans.OrderRequest;
import com.bandrango.store.services.beans.OrderResponse;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@InjectMocks
	private OrderController orderController;

	@Mock
	private StoreService storeService;

	@Test
	public void testOrder() {
		OrderRequest orderRequest = createOrderRequest();
		when(storeService.order(orderRequest)).thenReturn(createOrderResponse());
		ResponseEntity<?> result = orderController.order(orderRequest);

		verify(storeService, times(1)).order(orderRequest);
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertTrue(result.getBody() instanceof OrderResponse);
		assertEquals(createOrderResponse(), result.getBody());
	}

	private OrderRequest createOrderRequest() {
		return OrderRequest.builder().customerId(1).orderQuantityProductA(2).orderQuantityProductB(3)
				.orderQuantityProductC(4).orderQuantityProductD(5).build();
	}

	private OrderResponse createOrderResponse() {
		List<OrderLine> orderLines = new ArrayList<>();
		OrderLine orderLine1 = OrderLine.builder().quantityOrdered(10000).baseUnitPrice(new BigDecimal("0.52"))
				.lineTotal(new BigDecimal("9360.00")).build();
		OrderLine orderLine2 = OrderLine.builder().quantityOrdered(0).baseUnitPrice(new BigDecimal("0.38"))
				.lineTotal(BigDecimal.ZERO).build();
		OrderLine orderLine3 = OrderLine.builder().quantityOrdered(20000).baseUnitPrice(new BigDecimal("0.41"))
				.lineTotal(new BigDecimal("988182.00")).build();
		OrderLine orderLine4 = OrderLine.builder().quantityOrdered(0).baseUnitPrice(new BigDecimal("0.60"))
				.lineTotal(BigDecimal.ZERO).build();

		orderLines.add(orderLine1);
		orderLines.add(orderLine2);
		orderLines.add(orderLine3);
		orderLines.add(orderLine4);

		return OrderResponse.builder().orderLines(orderLines).totalAfterDiscounts(new BigDecimal("877836.96"))
				.totalBeforeDiscounts(new BigDecimal("997542.00")).build();
	}

}
