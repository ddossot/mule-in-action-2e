package com.prancingdonkey.component;

import java.math.BigDecimal;

import org.mule.api.annotations.param.InboundHeaders;
import org.mule.api.annotations.param.Payload;

public class TaxCalculator {

	BigDecimal calculateTaxForState(String state, BigDecimal value) {
		// dummy
		return BigDecimal.ZERO;
	}

	//<start id="lis_06_annotated-component"/>
	public BigDecimal calculateTax(
			@Payload BigDecimal cartValue,//<co id="lis_06_annotated-component_1"/>  
			@InboundHeaders("state") String state)//<co id="lis_06_annotated-component_2"/> 
	{
		return calculateTaxForState(state, cartValue);
	}
	// <end id="lis_06_annotated-component"/>
	
}

