package com.prancingdonkey;

import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class CXFProxyTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/main/app/cxf-service-proxy.xml";
    }

    @Test
    public void testCanProxySOAPRequests() throws Exception {
        MuleClient client = muleContext.getClient();
        String request = FileUtils.readFileToString(new File("src/test/resources/brew.soap.request.xml"));
        MuleMessage response = client.send("http://localhost:8090/soap", request, null);
        assertNotNull(response);
        assertEquals(FileUtils.readFileToString(new File("src/test/resources/brew.response.noprolog.xml")),
                response.getPayloadAsString());
    }



}
