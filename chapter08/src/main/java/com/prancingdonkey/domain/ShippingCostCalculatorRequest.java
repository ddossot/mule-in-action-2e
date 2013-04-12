package com.prancingdonkey.domain;

public class ShippingCostCalculatorRequest {

	String zipCode;

	public ShippingCostCalculatorRequest(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
