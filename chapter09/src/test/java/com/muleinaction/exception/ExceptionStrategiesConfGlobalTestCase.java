package com.muleinaction.exception;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;


public class ExceptionStrategiesConfGlobalTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "exception/exception-strategies-conf-global.xml";
    }

    @Test
    public void testCatchStrategy() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);

        Map<String, BigDecimal> headers = new HashMap<String, BigDecimal>();
        headers.put("state", BigDecimal.ZERO);
         
        MuleMessage result = muleClient.send("vm://tax-calculator-service.in", BigDecimal.ZERO, headers);

        assertThat(result.getPayload(), is(instanceOf(String.class)));
  }
    
}
