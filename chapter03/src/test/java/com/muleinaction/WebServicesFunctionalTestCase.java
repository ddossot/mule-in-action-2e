
package com.muleinaction;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.custommonkey.xmlunit.Diff;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class WebServicesFunctionalTestCase extends FunctionalTestCase {
    @Override
    protected String getConfigResources() {
        return "src/main/app/ws-config.xml";
    }

    @Test
    public void testCanConsumeRESTfulService() throws Exception {
        MuleClient client = muleContext.getClient();
        Map parameters = new HashMap();
        parameters.put("Content-Type", "application/json");
        parameters.put("http.method", "GET");

        MuleMessage response = client.send("http://localhost:8091/rest/brews", "", parameters);

        assertNotNull(response);
        assertEquals(FileUtils.readFileToString(new File("src/test/resources/brew.rest.response.js")),
                response.getPayloadAsString());
    }


    @Test
    public void testCanConsumeSOAPService() throws Exception {
        MuleClient client = muleContext.getClient();
        String request = FileUtils.readFileToString(new File("src/test/resources/brew.soap.request.xml"));
        MuleMessage response = client.send("http://localhost:8090/soap", request, null);
        assertNotNull(response);
        Diff diff = new Diff(FileUtils.readFileToString(new File("src/test/resources/brew.soap.response.xml")),
                response.getPayloadAsString());
        assertTrue( diff.toString(),diff.similar());
    }
}
