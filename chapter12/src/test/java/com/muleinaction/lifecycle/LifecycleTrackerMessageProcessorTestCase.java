
package com.muleinaction.lifecycle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class LifecycleTrackerMessageProcessorTestCase
{
    @Test
    public void trackLifecycle() throws Exception
    {
        final MuleClient muleClient = new MuleClient("lifecycle-config.xml");

        final MuleContext muleContext = muleClient.getMuleContext();
        muleContext.start();

        final MuleMessage result = muleClient.send("vm://MessageProcessorFlow.In", "foo", null);

        final LifecycleTrackerMessageProcessor ltmp = (LifecycleTrackerMessageProcessor) result.getPayload();

        muleContext.dispose();
        muleClient.dispose();

        assertEquals("[setProperty, setMuleContext, initialise, setFlowConstruct, setMuleContext, initialise, start, stop, dispose]",
            ltmp.getTracker().toString());
    }
}
