
package com.muleinaction.component;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class ComponentLifecycleTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "component/component-lifecycle.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        String payload = "testPayload";

        MuleMessage result = muleClient.send("vm://calculateShippingCost.in", payload, null);

        assertThat(result.getPayload(), is(instanceOf(String.class)));
    }
    
}
