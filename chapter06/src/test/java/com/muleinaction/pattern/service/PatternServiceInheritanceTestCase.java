
package com.muleinaction.pattern.service;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

import com.prancingdonkey.reciclingplant.RecicledCountRequest;

public class PatternServiceInheritanceTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/service/pattern-service-inheritance.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        RecicledCountRequest payload = new RecicledCountRequest();

        MuleMessage recicledResult = muleClient.send("vm://recicledCount.in", payload, null);
        assertThat(recicledResult.getPayload(), is(instanceOf(String.class)));
        
        MuleMessage wasteResult = muleClient.send("vm://wastedCount.in", payload, null);
        assertThat(wasteResult.getPayload(), is(instanceOf(String.class)));
    }
    
}
