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


public class ExceptionStrategiesChoiceTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "exception-strategies-choice.xml";
    }

    @Test
    public void testCatchStrategy() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("jms:/queue/calculateShippingCost", 0, null);

        assertThat(result.getPayload(), is(instanceOf(String.class)));
  }
    
}
