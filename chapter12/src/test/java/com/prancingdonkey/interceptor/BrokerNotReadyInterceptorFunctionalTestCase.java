package com.prancingdonkey.interceptor;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
public class BrokerNotReadyInterceptorFunctionalTestCase extends
        FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "broker-ready-config.xml";
    }

    @Test
    public void testBrokerReadinessSensitiveComponent() throws Exception {
        final MuleClient muleClient = new MuleClient(muleContext);

        assertEquals("ACK", muleClient.send("vm://BrokerReadinessService.In",
                "foo", null).getPayload());

        muleClient.dispose();
    }
}
