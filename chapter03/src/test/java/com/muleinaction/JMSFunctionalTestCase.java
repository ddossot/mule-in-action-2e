package com.muleinaction;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;


public class JMSFunctionalTestCase extends FunctionalTestCase {

    @Override
    protected String getConfigResources() {
        return "src/test/resources/jms-config.xml";
    }

    @Test
    public void testCanGetListOfBrews() throws Exception {
        MuleClient client = new MuleClient(muleContext);
        MuleMessage result = client.send("jms://brews.list",null,null,15000);
        System.out.println(result.getPayloadAsString());
    }
}
