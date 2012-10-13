
package com.muleinaction.listener;

import static org.junit.Assert.assertEquals;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.mule.DefaultMuleMessage;
import org.mule.api.MuleEventContext;
import org.mule.module.client.MuleClient;
import org.mule.tck.functional.EventCallback;
import org.mule.tck.functional.FunctionalTestComponent;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

public class NotificationsTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");

    @Override
    protected String getConfigResources()
    {
        return "notifications-config.xml";
    }

    @Test
    public void testOriginalMessageSavedInDlq() throws Exception
    {
        final CountDownLatch countDownLatch = new CountDownLatch(1);

        final List<Object> receivedPayloads = new ArrayList<Object>();

        final EventCallback callback = new EventCallback()
        {
            public void eventReceived(final MuleEventContext context, final Object component)
                throws Exception
            {

                receivedPayloads.add(context.getMessage().getPayload());
                countDownLatch.countDown();
            }
        };

        final FunctionalTestComponent testComponent = getFunctionalTestComponent("DlqProcessor");

        testComponent.setLogMessageDetails(true);
        testComponent.setEventCallback(callback);

        final Serializable payload = BigInteger.TEN;
        final MuleClient client = new MuleClient(muleContext);
        client.dispatch("vm://MessageReceiver.In", new DefaultMuleMessage(payload,
            (Map<String, Object>) null, muleContext));

        countDownLatch.await(15, TimeUnit.SECONDS);

        assertEquals(1, receivedPayloads.size());
        assertEquals(payload, receivedPayloads.toArray()[0]);
    }
}
