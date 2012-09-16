package com.prancingdonkey.reciclingplant;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;


public class WastedCountComponent implements Callable {

	public Object onCall(MuleEventContext eventContext) throws Exception {
		return "<com.prancingdonkey.reciclingplant.RecicledCountResponse><count>123</count></com.prancingdonkey.reciclingplant.RecicledCountResponse>";
	}
	 
}
