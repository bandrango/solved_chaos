package com.bandrango.store.services.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OrderRequest {

	private int customerId;
    private int orderQuantityProductA;
    private int orderQuantityProductB;
    private int orderQuantityProductC;
    private int orderQuantityProductD;

}