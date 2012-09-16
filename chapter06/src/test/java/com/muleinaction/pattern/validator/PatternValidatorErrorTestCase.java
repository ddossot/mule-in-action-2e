
package com.muleinaction.pattern.validator;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.module.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class PatternValidatorErrorTestCase extends FunctionalTestCase
{

    @Override
    protected String getConfigResources()
    {
        return "pattern/validator/pattern-validator-error.xml";
    }

    @Test
    public void testSimpleValidatorWithErrorExpression() throws Exception
    {
        MuleClient muleClient = new MuleClient(muleContext);

        int payload = 0;

        MuleMessage result = muleClient.send("vm://service.in", payload, null);

        assertThat(result.getPayloadAsString(), equalTo("Error delivering."));
    }
    
}
