package com.prancingdonkey;

import junit.framework.Assert;
import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.tck.junit4.FunctionalTestCase;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.*;


public class MediationFunctionalTestCase extends FunctionalTestCase  {

    @Override
    protected String getConfigResources() {
        return "src/main/app/mediation-config.xml";
    }


    @Test
    public void testCanProxyMessages() throws Exception {
        String order = FileUtils.readFileToString(new File("src/test/resources/order.xml"));

        Map properties = new HashMap();
        properties.put("Authorization","Basic am9objpqb2hu");

        MuleMessage response = muleContext.getClient().send("http://localhost:8080", order, properties);
        assertNotNull(response);
        assertEquals("SUCCESS", response.getPayloadAsString());
    }
}
