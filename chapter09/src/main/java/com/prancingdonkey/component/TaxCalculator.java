package com.prancingdonkey.component;

import java.math.BigDecimal;

import org.mule.api.annotations.param.InboundHeaders;
import org.mule.api.annotations.param.Payload;

public class TaxCalculator {

	BigDecimal calculateTaxForState(String state, BigDecimal value) {
		// dummy
		return BigDecimal.ZERO;
	}

	public BigDecimal calculateTax(
			@Payload BigDecimal cartValue,
			@InboundHeaders("state") String state) throws Exception
	{
		//Force error
		throw new Exception();
	}
	
}

