package com.muleinaction;

import org.junit.Ignore;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.context.notification.EndpointMessageNotificationListener;
import org.mule.api.context.notification.ServerNotification;
import org.mule.tck.junit4.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import static junit.framework.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;

/**
 * ToDo These tests are currently disabled until there is a way for Cloud Connectors to participate in
 * Mule's notification
 * system.  Need the notifications to count down the latch for the test cases.
 */
@Ignore
public class MongoDBFunctionalTestCase extends FunctionalTestCase {

    CountDownLatch insertLatch;

    @Override
    protected String getConfigResources() {
        return "src/main/app/mongodb-config.xml";
    }


    @Test
    public void testCanSaveCoolingAlerts() throws Exception {

        Map<String,Object> coolingAlert = new HashMap<String, Object>();
        coolingAlert.put("location","NYC");
        coolingAlert.put("temperature",65.00);
        muleContext.getClient().dispatch("jms://topic:cooling.alerts", "{'location': 'NYC', 'temperature': 65.00}", null);
    }

    @Test
    public void testCanQueryForAlerts() throws Exception {
        Map  properties = new HashMap();
        MuleMessage response = muleContext.getClient().send("http://localhost:8080/alerts/cooling","{}",properties);
        assertNotNull(response);
        assertEquals("Foo", response.getPayloadAsString());
    }
}
