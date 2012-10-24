
package com.muleinaction.debugging;

import static org.junit.Assert.assertEquals;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.junit.Rule;
import org.junit.Test;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

public class DebuggingTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");

    @Override
    protected String getConfigResources()
    {
        return "debugging.xml";
    }

    @Test
    public void testBeersService() throws Exception
    {
        GetMethod getMethod = new GetMethod("http://localhost:" + port.getNumber() + "/api/beers");
        assertEquals(200, new HttpClient().executeMethod(getMethod));
    }

    @Test
    public void testOrdersService() throws Exception
    {
        PostMethod postMethod = new PostMethod("http://localhost:" + port.getNumber() + "/api/orders");
        postMethod.setRequestEntity(new StringRequestEntity("fake", "text/plain", "utf-8"));
        assertEquals(200, new HttpClient().executeMethod(postMethod));
    }
}
