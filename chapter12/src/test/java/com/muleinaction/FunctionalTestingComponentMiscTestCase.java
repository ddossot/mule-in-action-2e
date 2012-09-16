
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;
import org.mule.util.StringUtils;

public class FunctionalTestingComponentMiscTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");

    @Override
    protected String getConfigResources()
    {
        return "ftc-misc.xml";
    }

    @Test
    public void testFtcReturnData() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();

        MuleMessage response = muleClient.send("vm://ftc.returndata", "ignored", null);

        assertThat(StringUtils.trimToEmpty(response.getPayloadAsString()), is("TEST PAYLOAD"));
    }

    @Test
    public void testFtcReturnFileData() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();

        MuleMessage response = muleClient.send("vm://ftc.returnfiledata", "ignored", null);

        assertThat(StringUtils.trimToEmpty(response.getPayloadAsString()), is("FILE TEST PAYLOAD"));
    }
}
