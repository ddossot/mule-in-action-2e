
package com.muleinaction.lifecycle;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.api.MuleContext;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;

public class LifecycleTrackerFilterTestCase
{
    @Test
    public void trackLifecycle() throws Exception
    {
        final MuleClient muleClient = new MuleClient("lifecycle-config.xml");

        final MuleContext muleContext = muleClient.getMuleContext();
        muleContext.start();

        final MuleMessage result = muleClient.send("vm://FilteredFlow.In", "foo", null);

        assertEquals("foo", result.getPayload());

        final LifecycleTrackerFilter ltf = (LifecycleTrackerFilter) muleContext.getRegistry().lookupObject(
            "LifecycleTrackerFilter");

        muleContext.dispose();
        muleClient.dispose();

        assertEquals("[setProperty, setMuleContext, initialise]", ltf.getTracker().toString());
    }
}
