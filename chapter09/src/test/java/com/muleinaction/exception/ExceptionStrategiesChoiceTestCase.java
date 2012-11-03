package com.muleinaction.exception;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.text.StringStartsWith.startsWith;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;

public class ExceptionStrategiesChoiceTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "exception/exception-strategies-choice.xml";
    }

    @Test
    public void testCatchStrategy() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);

    	for (int i = 0; i < 4; i++) {
    		MuleMessage result = muleClient.send("vm://calculateShippingCost.in", 0, null);
    		assertThat(result.getExceptionPayload(), is(not(instanceOf(NullPayload.class))));
    	}
    	
    	MuleMessage result = muleClient.send("vm://calculateShippingCost.in", 0, null);
		assertThat(result.getExceptionPayload(), is(not(instanceOf(NullPayload.class))));
		assertThat(result.getPayloadAsString(), startsWith("Error:"));
  }
    
}
