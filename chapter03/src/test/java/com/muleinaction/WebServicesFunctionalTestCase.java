
package com.muleinaction;

import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class WebServicesFunctionalTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "src/test/resources/ws-config.xml";
    }

    public void testCanConsumeJaxRSWebService() throws Exception
    {
        final MuleClient client = muleContext.getClient();
        final MuleMessage response = client.send("http://localhost:8091/rest/brews/list", null, null);
        assertNotNull(response);
    }

    public void testCanAddBrew() throws Exception
    {
        final MuleClient client = muleContext.getClient();
        final String brewXml = "<brew>\n" + "<name>Hobbit IPA 2</name>\n"
                               + "<description>Hobbit IPA 2</description>\n" + "</brew>";

        final Map<String, Object> properties = new HashMap<String, Object>();
        properties.put("guid", UUID.randomUUID().toString());
        properties.put("content-type", "application/xml");
        properties.put("accept", "application/json");

        final MuleMessage response = client.send("http://localhost:8091/rest/brews/add", brewXml, properties);
        assertNotNull(response);
    }
}
