
package com.muleinaction;

import static com.muleinaction.Constants.MESSAGE_XML_V1;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Rule;
import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
import org.mule.tck.junit4.rule.DynamicPort;

public class TransformingBridgeDelayTestCase extends FunctionalTestCase
{
    @Rule
    public DynamicPort port = new DynamicPort("port");

    @Override
    protected String getConfigResources()
    {
        return "functional-test-connectors.xml,jms-delayed-transforming-bridge.xml";
    }

    @Test
    public void testJmsXmlV1V2Bridge() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();

        // <start id="lis_12_ftc_delay-2"/>
        muleClient.dispatch("jms://messages.v1", MESSAGE_XML_V1, null);

        MuleMessage response = muleClient.request("jms://messages.v2", 2000);

        assertThat(response, is(nullValue()));
        // <end id="lis_12_ftc_delay-2"/>
    }
}
