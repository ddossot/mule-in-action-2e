package com.prancingdonkey.domain;

import java.math.BigDecimal;

public class ShippingCostCalculatorResponse {

	BigDecimal cost;
	
	public ShippingCostCalculatorResponse() {
	}
	
	public ShippingCostCalculatorResponse(BigDecimal cost) {
		this.cost = cost;
	}

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}
	
}
