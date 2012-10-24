
package com.muleinaction;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.mule.api.MuleMessage;
import org.mule.api.client.MuleClient;
import org.mule.tck.junit4.FunctionalTestCase;

public class MelCustomizationTestCase extends FunctionalTestCase
{
    @Override
    protected String getConfigResources()
    {
        return "mel-customization.xml";
    }

    @Test
    public void testCustomizations() throws Exception
    {
        final MuleClient client = muleContext.getClient();
        client.dispatch("vm://test.in", "elo", null);

        final MuleMessage result = client.request("vm://test.out", getTestTimeoutSecs() * 1000);
        assertThat(result.getPayloadAsString(), is("ole"));
        assertThat(result.getInboundProperty("random-app-property"), is(notNullValue()));
    }
}
