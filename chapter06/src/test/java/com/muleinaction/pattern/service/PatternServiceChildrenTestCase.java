
package com.muleinaction.pattern.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.reciclingplant.RecicledCountRequest;

public class PatternServiceChildrenTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/service/pattern-service-children.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        RecicledCountRequest payload = new RecicledCountRequest();

        MuleMessage result = muleClient.send("vm://recicledCount.in", payload, null);

        assertThat(result.getPayload(), is(instanceOf(String.class)));
    }
    
}
