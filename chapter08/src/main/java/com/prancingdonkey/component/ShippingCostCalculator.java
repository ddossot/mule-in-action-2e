package com.prancingdonkey.component;

import java.math.BigDecimal;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.prancingdonkey.domain.ShippingCostCalculatorResponse;

public class ShippingCostCalculator implements Callable {
	
	public Object onCall(MuleEventContext eventContext)
			throws Exception {
		// Just return a dummy value
		return new ShippingCostCalculatorResponse(new BigDecimal(10));
	}
}

