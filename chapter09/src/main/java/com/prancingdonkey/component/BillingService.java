package com.prancingdonkey.component;

import java.util.HashMap;
import java.util.Map;


/**
 * Mock Component
 */
public class BillingService {
	
	public Map<String, String> process(Object object) throws Exception {
		Map<String, String> statMap = new HashMap<String, String>(1);
		statMap.put("STAT", "OK");
        return statMap;
    }
    
}
