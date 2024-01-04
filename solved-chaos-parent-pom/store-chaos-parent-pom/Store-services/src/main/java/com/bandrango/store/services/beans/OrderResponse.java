package com.bandrango.store.services.beans;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderResponse {
	private List<OrderLine> orderLines;
	private BigDecimal totalBeforeDiscounts;
	private BigDecimal totalAfterDiscounts;

}