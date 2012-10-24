package com.muleinaction.exception;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ExceptionStrategiesCatchTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "exception/exception-strategies-catch.xml";
    }

    @Test
    public void testCatchStrategy() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://calculateShippingCost.in", 0, null);

        assertThat(result.getPayload(), is(instanceOf(String.class)));
  }
    
}
