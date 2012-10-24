package com.prancingdonkey.component;

import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.sql.Timestamp;

/**
 * Mock URLMetricComponent
 */
public class URLMetricsComponent {
	
	public Object process(Object object) throws Exception {
        return getData((String) object);
    }

    Map<String, String> getData(String data) {
        Map<String, String> result = new HashMap<String, String>(5);
        result.put("CLIENT_ID","1");
        result.put("AVG_RESPONSE_TIME", "0.95");
        result.put("MED_RESPONSE_TIME", "0.75");
        result.put("MAX_RESPONSE_TIME", "0.195");
        Timestamp ts = new Timestamp(new Date().getTime());
        result.put("TIMESTAMP", ts.toString());
        return result;
    }
    
}
