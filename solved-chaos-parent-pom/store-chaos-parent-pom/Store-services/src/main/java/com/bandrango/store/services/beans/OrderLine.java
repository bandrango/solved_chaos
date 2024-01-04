package com.bandrango.store.services.beans;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderLine {

	private int quantityOrdered;
    private BigDecimal baseUnitPrice;
    private BigDecimal lineTotal;

}