package com.prancingdonkey;

import com.prancingdonkey.model.Order;
import junit.framework.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import static junit.framework.Assert.*;

public class BPMFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/order-backend-bpm-config.xml";
    }

    @Test
    public void testCanSubmitOrder() throws Exception {

        MuleClient client = muleContext.getClient();
        Order order = new Order();
        client.dispatch("jms://order.submit", order, null);

        MuleMessage result = client.request("jms://topic:events.orders.completed", 15000);
        assertNotNull(result);
        assertTrue(result.getPayload() instanceof Order);
    }
}
