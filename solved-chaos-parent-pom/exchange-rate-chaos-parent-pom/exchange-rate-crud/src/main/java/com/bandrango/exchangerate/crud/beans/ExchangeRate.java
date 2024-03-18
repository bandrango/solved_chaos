package com.bandrango.exchangerate.crud.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder

public class ExchangeRate implements Serializable {
	
	private static final long serialVersionUID = -8747739845614540941L;
    private Long id;
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal exchangeRate;
	private Date effectiveStartDate;
}