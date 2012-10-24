package com.muleinaction.transaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.transport.NullPayload;


public class TransactionJmsForwardTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "transaction/transaction-jms-forward.xml";
    }

    @Test
    public void testCatchStrategy() throws Exception
    {
    	MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("jms://messages", "STATUS: OK", null);

        assertThat(result.getPayload(), is(not(instanceOf(NullPayload.class))));
    }
    
}
