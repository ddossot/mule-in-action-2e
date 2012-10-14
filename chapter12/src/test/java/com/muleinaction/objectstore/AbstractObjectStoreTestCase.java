
package com.muleinaction.objectstore;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public abstract class AbstractObjectStoreTestCase extends FunctionalTestCase
{
    @Test
    public void testIdempotentBridge() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();

        muleClient.dispatch("vm://ib.in", "test_payload", null);
        MuleMessage response = muleClient.request("vm://ib.out", 1000 * getTestTimeoutSecs());
        assertEquals("test_payload", response.getPayloadAsString());
    }
}
