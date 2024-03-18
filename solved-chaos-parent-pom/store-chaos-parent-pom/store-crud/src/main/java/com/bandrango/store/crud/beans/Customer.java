package com.bandrango.store.crud.beans;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Customer implements Serializable {
	
	private static final long serialVersionUID = 6222145700967191967L;
	private Long id;
	private BigDecimal basicCustomerDiscount;
    private BigDecimal discountAbove10000;
    private BigDecimal discountAbove30000;
}