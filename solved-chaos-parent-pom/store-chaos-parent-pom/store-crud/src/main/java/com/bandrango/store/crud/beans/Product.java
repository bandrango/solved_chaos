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
public class Product implements Serializable {
	
	private static final long serialVersionUID = -3976352183809708304L;
	private String name;
    private BigDecimal unitCost;
    private String markup;
    private String promotion;
}