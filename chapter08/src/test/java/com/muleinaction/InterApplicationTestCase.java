
package com.muleinaction;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.domain.ShippingCostCalculatorRequest;
import com.prancingdonkey.domain.ShippingCostCalculatorResponse;

public class InterApplicationTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "inter-application-service.xml,inter-application-client.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        ShippingCostCalculatorRequest payload = new ShippingCostCalculatorRequest("12345");

        MuleMessage result = muleClient.send("vm://shippingCostClient.in", payload, null);

        assertThat(result.getPayload(), is(instanceOf(ShippingCostCalculatorResponse.class)));
    }
    
}
