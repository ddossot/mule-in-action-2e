package com.muleinaction;

import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.FunctionalTestCase;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public class WebServicesFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/ws-config.xml";
    }

    public void testCanConsumeJaxRSWebService() throws Exception {
        MuleClient client = muleContext.getClient();
        MuleMessage response = client.send("http://localhost:8091/rest/brews/list", null, null);
        assertNotNull(response);
//        assertEquals("foo", response.getPayloadAsString());
    }

    public void testCanAddBrew() throws Exception {
        MuleClient client = muleContext.getClient();
        String brewXml =
                "<brew>\n" +
                "<name>Hobbit IPA 2</name>\n" +
                "<description>Hobbit IPA 2</description>\n" +
                "</brew>";

        Map properties = new HashMap();
        properties.put("guid", UUID.randomUUID().toString());
        properties.put("content-type", "application/xml");
        properties.put("accept", "application/json");

        MuleMessage response = client.send("http://localhost:8091/rest/brews/add", brewXml, properties);
        assertNotNull(response);
//        assertEquals("foo", response.getPayloadAsString());

    }
}
