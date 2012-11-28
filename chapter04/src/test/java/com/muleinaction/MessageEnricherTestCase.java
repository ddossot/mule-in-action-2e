
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import org.junit.Test;

public class MessageEnricherTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "message-enricher.xml";
    }

    @Test
    public void singleEnrichment() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);
    	MuleMessage result = muleClient.send("vm://invoice-processor", "ignored", null);
    	assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(String.class)));
        assertThat(result.getPayloadAsString(), is("USD"));
    }

    @Test
    public void multipleEnrichment() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);
    	MuleMessage result = muleClient.send("vm://message-enricher-2", "ignored", null);
    	assertThat(result, is(notNullValue()));
        assertThat(result.getPayload(), is(notNullValue()));
        assertThat(result.getPayload(), is(instanceOf(Object[].class)));
        
        Object[] resultValue = (Object[]) result.getPayload();
        assertThat(resultValue[0], is(instanceOf(String.class)));
        assertThat((String)resultValue[0], is("USD"));
        assertThat(resultValue[1], is(instanceOf(String.class)));
        assertThat((String)resultValue[1], is("ABC123"));
    }
}
