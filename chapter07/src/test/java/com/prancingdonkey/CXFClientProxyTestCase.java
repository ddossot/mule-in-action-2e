package com.prancingdonkey;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CXFClientProxyTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/cxf-client-proxy.xml";
    }

    @Test
    public void testCanProxyClient() throws Exception {
        MuleClient client = muleContext.getClient();
        String request = FileUtils.readFileToString(new File("src/test/resources/brew.request.xml"));
        MuleMessage response = client.send("vm://brews", request, null);
        assertNotNull(response);
        assertEquals(FileUtils.readFileToString(new File("src/test/resources/brew.response.xml")),
                response.getPayloadAsString());
    }
}
