
package com.muleinaction.component;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class SpringFixedSeedRandomIntegerServiceTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "component/spring-fixed-seed-random-integer-service.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        MuleMessage result = muleClient.send("vm://spring-fixed-seed-random-integer-service.in", null, null);

        assertThat(result.getPayload(), is(instanceOf(Integer.class)));
    }
    
}
