
package com.muleinaction.pattern.validator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PatternValidatorTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/validator/pattern-validator.xml";
    }

    @Test
    public void testSimpleValidator() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        int payload = 0;

        MuleMessage result = muleClient.send("vm://service.in", payload, null);
        MuleMessage outboundResult = muleClient.request("vm://real-service.in", 2000);

        assertThat(result.getPayloadAsString(), equalTo("Message accepted."));
        assertThat((Integer) outboundResult.getPayload(), equalTo(payload));
    }
    
    @Test
    public void testSimpleValidatorInvalid() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        String payload = "Hello World";

        MuleMessage result = muleClient.send("vm://service.in", payload, null);

        assertThat(result.getPayloadAsString(), equalTo("Message rejected."));
    }

}
