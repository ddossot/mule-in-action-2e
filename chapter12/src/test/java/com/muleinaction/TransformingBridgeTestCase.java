
package com.muleinaction;

import static com.muleinaction.Constants.MESSAGE_XML_V1;
import static com.muleinaction.Constants.MESSAGE_XML_V2;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;
//<start id="lis_12_fun_testing-2"/>
public class TransformingBridgeTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()//<co id="lis_12_fun_testing-2-1"/>
    {
       return
         "functional-test-connectors.xml,jms-transforming-bridge.xml";
    }

    @Test
    public void testJmsXmlV1V2Bridge() throws Exception
    {
        MuleClient muleClient = muleContext.getClient();//<co id="lis_12_fun_testing-2-2"/>

        muleClient.dispatch("jms://messages.v1", //<co id="lis_12_fun_testing-2-3"/>
                            MESSAGE_XML_V1,
                            null);

        MuleMessage response =
                    muleClient.request("jms://messages.v2",
                                       1000 * getTestTimeoutSecs());//<co id="lis_12_fun_testing-2-4"/>

        assertThat(response.getPayloadAsString(),//<co id="lis_12_fun_testing-2-5"/>
                   is(MESSAGE_XML_V2));
    }
}
//<end id="lis_12_fun_testing-2"/>